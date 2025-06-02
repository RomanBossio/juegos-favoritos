package com.example.juegosfavoritosapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class AgregarJuegoActivity : AppCompatActivity() {
    private lateinit var etNombreJuego: EditText
    private lateinit var etDescripcionJuego: EditText
    private lateinit var btnGuardarJuego: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_juego)

        // Inicializar vistas
        etNombreJuego = findViewById(R.id.etNombreJuego)
        etDescripcionJuego = findViewById(R.id.etDescripcionJuego)
        btnGuardarJuego = findViewById(R.id.btnGuardarJuego)

        btnGuardarJuego.setOnClickListener {
            val nombre = etNombreJuego.text.toString().trim()
            val descripcion = etDescripcionJuego.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Devolver los datos a ListaJuegosActivity
                val resultIntent = Intent()
                resultIntent.putExtra("nombre", nombre)
                resultIntent.putExtra("descripcion", descripcion)
                setResult(RESULT_OK, resultIntent)
                finish() // Cierra esta pantalla y vuelve
            }
        }

    }
}
