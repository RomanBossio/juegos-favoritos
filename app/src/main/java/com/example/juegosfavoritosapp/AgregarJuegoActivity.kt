package com.example.juegosfavoritosapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgregarJuegoActivity : AppCompatActivity() {

    private lateinit var etNombreJuego: EditText
    private lateinit var etDescripcionJuego: EditText
    private lateinit var btnGuardarJuego: Button
    private lateinit var ivImagenJuego: ImageView

    private var imagenUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_juego)

        // Inicializar vistas
        etNombreJuego = findViewById(R.id.etNombreJuego)
        etDescripcionJuego = findViewById(R.id.etDescripcionJuego)
        btnGuardarJuego = findViewById(R.id.btnGuardarJuego)
        ivImagenJuego = findViewById(R.id.ivImagenJuego)

        // Click para abrir la galería y seleccionar una imagen
        ivImagenJuego.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 200) // 200 es el requestCode para la galería
        }

        // Acción del botón para guardar el juego
        btnGuardarJuego.setOnClickListener {
            val nombre = etNombreJuego.text.toString().trim()
            val descripcion = etDescripcionJuego.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val resultIntent = Intent()
                resultIntent.putExtra("nombre", nombre)
                resultIntent.putExtra("descripcion", descripcion)
                resultIntent.putExtra("imagenUri", imagenUri?.toString()) // Guardar la URI como String
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    // Recibir la imagen seleccionada de la galería
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            imagenUri = data?.data
            ivImagenJuego.setImageURI(imagenUri) // Mostrar la imagen elegida
        }
    }
}
