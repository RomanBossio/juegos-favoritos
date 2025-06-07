package com.example.juegosfavoritosapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaJuegosActivity : AppCompatActivity() {

    private lateinit var rvJuegos: RecyclerView
    private lateinit var btnAgregar: Button
    private lateinit var svBuscar: SearchView // NUEVO: el SearchView
    private lateinit var adapter: JuegoAdapter

    private val juegos = mutableListOf(
        Juego("FIFA 24", "Juego de fútbol", "android.resource://${packageName}/drawable/fifa24, "Deportes"),
        Juego("Fortnite", "Battle royale popular", "android.resource://${packageName}/drawable/fornite, "Acción"),
        Juego("Minecraft", "Construcción y aventuras", "android.resource://${packageName}/drawable/minecraft, "Aventura"),
        Juego("The Witcher 3", "Rol y mundo abierto", "android.resource://${packageName}/drawable/witcher3, "Rol")
    )

    // Lista para la búsqueda
    private val juegosFiltrados = mutableListOf<Juego>()

    private var posicionEditando: Int? = null
    private var nuevaImagenUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_juegos)

        rvJuegos = findViewById(R.id.rvJuegos)
        btnAgregar = findViewById(R.id.btnAgregar)
        svBuscar = findViewById(R.id.svBuscar)

        // Inicialmente, mostrar todos los juegos
        juegosFiltrados.addAll(juegos)

        adapter = JuegoAdapter(juegosFiltrados) { position ->
            mostrarDialogoOpciones(position)
        }

        rvJuegos.layoutManager = LinearLayoutManager(this)
        rvJuegos.adapter = adapter

        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarJuegoActivity::class.java)
            startActivityForResult(intent, 100)
        }

        // Configurar la búsqueda
        svBuscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // No hacemos nada al darle "Enter"
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarJuegos(newText)
                return true
            }
        })
    }

    private fun filtrarJuegos(query: String?) {
        val texto = query?.lowercase() ?: ""
        juegosFiltrados.clear()
        if (texto.isEmpty()) {
            juegosFiltrados.addAll(juegos)
        } else {
            juegosFiltrados.addAll(
                juegos.filter {
                    it.nombre.lowercase().contains(texto) ||
                            it.descripcion.lowercase().contains(texto) ||
                            it.categoria.lowercase().contains(texto)
                }
            )
        }
        adapter.notifyDataSetChanged()
    }

    private fun mostrarDialogoOpciones(position: Int) {
        val opciones = arrayOf("Modificar juego", "Eliminar juego")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Qué querés hacer?")
        builder.setItems(opciones) { _, which ->
            when (which) {
                0 -> mostrarDialogoModificarJuego(position)
                1 -> eliminarJuego(position)
            }
        }
        builder.show()
    }

    private fun eliminarJuego(position: Int) {
        // Eliminar el juego también de la lista original
        val juegoAEliminar = juegosFiltrados[position]
        juegos.remove(juegoAEliminar)
        juegosFiltrados.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun mostrarDialogoModificarJuego(position: Int) {
        val juego = juegosFiltrados[position]
        val dialogView = layoutInflater.inflate(R.layout.dialog_modificar_juego, null)
        val etNuevoNombre = dialogView.findViewById<EditText>(R.id.etNuevoNombre)
        val etNuevaDescripcion = dialogView.findViewById<EditText>(R.id.etNuevaDescripcion)
        val ivNuevaImagen = dialogView.findViewById<ImageView>(R.id.ivNuevaImagen)
        val etNuevaCategoria = dialogView.findViewById<EditText>(R.id.etNuevaCategoria)

        etNuevoNombre.setText(juego.nombre)
        etNuevaDescripcion.setText(juego.descripcion)
        etNuevaCategoria.setText(juego.categoria)

        if (juego.imagenUri != null) {
            ivNuevaImagen.setImageURI(Uri.parse(juego.imagenUri))
        } else {
            ivNuevaImagen.setImageResource(R.drawable.ic_juego_generico)
        }

        posicionEditando = juegos.indexOf(juego)
        nuevaImagenUri = juego.imagenUri?.let { Uri.parse(it) }

        ivNuevaImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 200)
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Modificar juego")
        builder.setView(dialogView)
        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevoNombre = etNuevoNombre.text.toString().trim()
            val nuevaDescripcion = etNuevaDescripcion.text.toString().trim()
            val nuevaCategoria = etNuevaCategoria.text.toString().trim()

            if (nuevoNombre.isNotEmpty() && nuevaDescripcion.isNotEmpty() && nuevaCategoria.isNotEmpty()) {
                val juegoActualizado = Juego(
                    nuevoNombre,
                    nuevaDescripcion,
                    nuevaImagenUri?.toString(),
                    nuevaCategoria
                )
                // Actualizar en la lista original
                posicionEditando?.let {
                    juegos[it] = juegoActualizado
                }
                // Actualizar en la lista filtrada
                juegosFiltrados[position] = juegoActualizado
                adapter.notifyItemChanged(position)
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            val nombre = data?.getStringExtra("nombre")
            val descripcion = data?.getStringExtra("descripcion")
            val categoria = data?.getStringExtra("categoria")
            val imagenUriString = data?.getStringExtra("imagenUri")

            if (!nombre.isNullOrEmpty() && !descripcion.isNullOrEmpty() && !categoria.isNullOrEmpty()) {
                val nuevoJuego = Juego(nombre, descripcion, imagenUriString, categoria)
                juegos.add(nuevoJuego)
                juegosFiltrados.add(nuevoJuego)
                adapter.notifyItemInserted(juegosFiltrados.size - 1)
            }
        }

        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            nuevaImagenUri = data?.data
        }
    }
}