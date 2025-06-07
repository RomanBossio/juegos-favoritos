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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaJuegosActivity : AppCompatActivity() {

    private lateinit var rvJuegos: RecyclerView
    private lateinit var btnAgregar: Button
    private lateinit var adapter: JuegoAdapter

    private val juegos = mutableListOf(
        Juego("FIFA 24", "Juego de fútbol", null, "Deportes"),
        Juego("Fortnite", "Battle royale popular", null, "Acción"),
        Juego("Minecraft", "Construcción y aventuras", null, "Aventura"),
        Juego("The Witcher 3", "Rol y mundo abierto", null, "Rol")
    )

    private var posicionEditando: Int? = null
    private var nuevaImagenUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_juegos)

        rvJuegos = findViewById(R.id.rvJuegos)
        btnAgregar = findViewById(R.id.btnAgregar)

        adapter = JuegoAdapter(juegos) { position ->
            mostrarDialogoOpciones(position)
        }

        rvJuegos.layoutManager = LinearLayoutManager(this)
        rvJuegos.adapter = adapter

        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarJuegoActivity::class.java)
            startActivityForResult(intent, 100)
        }
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
        juegos.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun mostrarDialogoModificarJuego(position: Int) {
        val juego = juegos[position]
        val dialogView = layoutInflater.inflate(R.layout.dialog_modificar_juego, null)
        val etNuevoNombre = dialogView.findViewById<EditText>(R.id.etNuevoNombre)
        val etNuevaDescripcion = dialogView.findViewById<EditText>(R.id.etNuevaDescripcion)
        val ivNuevaImagen = dialogView.findViewById<ImageView>(R.id.ivNuevaImagen)
        val etNuevaCategoria = dialogView.findViewById<EditText>(R.id.etNuevaCategoria) // NUEVO

        etNuevoNombre.setText(juego.nombre)
        etNuevaDescripcion.setText(juego.descripcion)
        etNuevaCategoria.setText(juego.categoria) // NUEVO

        if (juego.imagenUri != null) {
            ivNuevaImagen.setImageURI(Uri.parse(juego.imagenUri))
        } else {
            ivNuevaImagen.setImageResource(R.drawable.ic_juego_generico)
        }

        posicionEditando = position
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
            val nuevaCategoria = etNuevaCategoria.text.toString().trim() // NUEVO

            if (nuevoNombre.isNotEmpty() && nuevaDescripcion.isNotEmpty() && nuevaCategoria.isNotEmpty()) {
                val juegoActualizado = Juego(
                    nuevoNombre,
                    nuevaDescripcion,
                    nuevaImagenUri?.toString(),
                    nuevaCategoria // NUEVO
                )
                juegos[position] = juegoActualizado
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
            val categoria = data?.getStringExtra("categoria") // NUEVO
            val imagenUriString = data?.getStringExtra("imagenUri")

            if (!nombre.isNullOrEmpty() && !descripcion.isNullOrEmpty() && !categoria.isNullOrEmpty()) {
                val nuevoJuego = Juego(nombre, descripcion, imagenUriString, categoria)
                juegos.add(nuevoJuego)
                adapter.notifyItemInserted(juegos.size - 1)
            }
        }

        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            nuevaImagenUri = data?.data
        }
    }
}
