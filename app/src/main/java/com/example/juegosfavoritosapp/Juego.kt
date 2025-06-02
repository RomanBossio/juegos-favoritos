package com.example.juegosfavoritosapp

data class Juego(
    val nombre: String,
    val descripcion: String,
    val imagenUri: String? // URI como String. Puede ser nulo si se usa la imagen por defecto.
)
