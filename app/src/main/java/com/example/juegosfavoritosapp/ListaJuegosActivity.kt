package com.example.juegosfavoritosapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent


class ListaJuegosActivity : AppCompatActivity() {
    private lateinit var rvJuegos: RecyclerView
    private lateinit var btnAgregar: Button
    private lateinit var adapter: JuegoAdapter

    // Lista mutable de juegos
    private val juegos = mutableListOf(
        Juego("FIFA 24", "Juego de fútbol", R.drawable.fifa24),
        Juego("Fortnite", "Battle royale popular", R.drawable.fortnite),
        Juego("Minecraft", "Construcción y aventuras", R.drawable.minecraft),
        Juego("The Witcher 3", "Rol y mundo abierto", R.drawable.witcher3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_juegos)

        rvJuegos = findViewById(R.id.rvJuegos)
        btnAgregar = findViewById(R.id.btnAgregar)

        adapter = JuegoAdapter(juegos)
        rvJuegos.layoutManager = LinearLayoutManager(this)
        rvJuegos.adapter = adapter

        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarJuegoActivity::class.java)
            startActivityForResult(intent, 100) // Código de request
        }
    }

    // Recibir el juego nuevo cuando vuelva la Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val nombre = data?.getStringExtra("nombre")
            val descripcion = data?.getStringExtra("descripcion")

            if (!nombre.isNullOrEmpty() && !descripcion.isNullOrEmpty()) {
                // Crear el nuevo juego con imagen por defecto
                val nuevoJuego = Juego(nombre, descripcion, R.drawable.repo)
                juegos.add(nuevoJuego)
                adapter.notifyItemInserted(juegos.size - 1)
            }
        }
    }
}
