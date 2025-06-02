package com.example.juegosfavoritosapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaJuegosActivity : AppCompatActivity() {
    private lateinit var rvJuegos: RecyclerView
    private lateinit var btnAgregar: Button
    private lateinit var adapter: JuegoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_juegos)

        val juegos = listOf(
            Juego("FIFA 24", "Juego de fútbol", R.drawable.fifa24),
            Juego("Fortnite", "Battle royale popular", R.drawable.fortnite),
            Juego("Minecraft", "Construcción y aventuras", R.drawable.minecraft),
            Juego("The Witcher 3", "Rol y mundo abierto", R.drawable.witcher3)
        )


        // Inicializar vistas
        rvJuegos = findViewById(R.id.rvJuegos)
        btnAgregar = findViewById(R.id.btnAgregar)

        // Configurar RecyclerView
        adapter = JuegoAdapter(juegos)
        rvJuegos.layoutManager = LinearLayoutManager(this)
        rvJuegos.adapter = adapter

        // Botón para agregar juego (placeholder)
        btnAgregar.setOnClickListener {
            Toast.makeText(this, "Funcionalidad de agregar juego próximamente!", Toast.LENGTH_SHORT).show()
        }
    }
}
