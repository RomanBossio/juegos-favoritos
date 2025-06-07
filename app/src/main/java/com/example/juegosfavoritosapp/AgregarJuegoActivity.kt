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
import androidx.appcompat.widget.Toolbar

class AgregarJuegoActivity : AppCompatActivity() {

    private lateinit var etNombreJuego: EditText
    private lateinit var etDescripcionJuego: EditText
    private lateinit var etCategoriaJuego: EditText
    private lateinit var btnGuardarJuego: Button
    private lateinit var ivImagenJuego: ImageView

    private var imagenUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_juego)

        // Configurar Toolbar como ActionBar para que aparezca la flecha
        val myToolbar: Toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Agregar Juego"

        // Inicializar vistas
        etNombreJuego = findViewById(R.id.etNombreJuego)
        etDescripcionJuego = findViewById(R.id.etDescripcionJuego)
        etCategoriaJuego = findViewById(R.id.etCategoriaJuego)
        btnGuardarJuego = findViewById(R.id.btnGuardarJuego)
        ivImagenJuego = findViewById(R.id.ivImagenJuego)

        // Click para abrir la galería y seleccionar una imagen
        ivImagenJuego.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 200)
        }

        // Acción del botón para guardar el juego
        btnGuardarJuego.setOnClickListener {
            val nombre = etNombreJuego.text.toString().trim()
            val descripcion = etDescripcionJuego.text.toString().trim()
            val categoria = etCategoriaJuego.text.toString().trim()

            if (nombre.isEmpty() || descripcion.isEmpty() || categoria.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val resultIntent = Intent()
                resultIntent.putExtra("nombre", nombre)
                resultIntent.putExtra("descripcion", descripcion)
                resultIntent.putExtra("categoria", categoria)
                resultIntent.putExtra("imagenUri", imagenUri?.toString())
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
            ivImagenJuego.setImageURI(imagenUri)
        }
    }

    // Manejar el evento de la flecha atrás
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
