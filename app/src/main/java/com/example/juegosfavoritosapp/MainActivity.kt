package com.example.juegosfavoritosapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al botón
        val btnContinuar: Button = findViewById(R.id.btnContinuar)

        // Configurar el click para ir a la siguiente pantalla
        btnContinuar.setOnClickListener {
            // Creamos un Intent para ir a la siguiente pantalla (ListaJuegosActivity)
            val intent = Intent(this, ListaJuegosActivity::class.java)
            startActivity(intent)
        }
    }
}
