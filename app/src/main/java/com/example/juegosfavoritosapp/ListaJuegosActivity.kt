package com.example.juegosfavoritosapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaJuegosActivity : AppCompatActivity() {

    private lateinit var rvJuegos: RecyclerView
    private lateinit var btnAgregar: Button
    private lateinit var adapter: JuegoAdapter

    private val juegos = mutableListOf(
        Juego("FIFA 24", "Juego de fútbol", null),
        Juego("Fortnite", "Battle royale popular", null),
        Juego("Minecraft", "Construcción y aventuras", null),
        Juego("The Witcher 3", "Rol y mundo abierto", null)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_juegos)

        rvJuegos = findViewById(R.id.rvJuegos)
        btnAgregar = findViewById(R.id.btnAgregar)

        // Inicializamos el Adapter con el onItemClick
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
        val opciones = arrayOf("Eliminar juego")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Qué querés hacer?")
        builder.setItems(opciones) { _, which ->
            when (which) {
                0 -> eliminarJuego(position)
            }
        }
        builder.show()
    }

    private fun eliminarJuego(position: Int) {
        juegos.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val nombre = data?.getStringExtra("nombre")
            val descripcion = data?.getStringExtra("descripcion")
            val imagenUriString = data?.getStringExtra("imagenUri")

            if (!nombre.isNullOrEmpty() && !descripcion.isNullOrEmpty()) {
                val nuevoJuego = Juego(nombre, descripcion, imagenUriString)
                juegos.add(nuevoJuego)
                adapter.notifyItemInserted(juegos.size - 1)
            }
        }
    }
}


