package com.example.juegosfavoritosapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JuegoAdapter(
    private val juegos: List<Juego>,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<JuegoAdapter.JuegoViewHolder>() {

    inner class JuegoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivJuego: ImageView = itemView.findViewById(R.id.ivJuego)
        val tvNombreJuego: TextView = itemView.findViewById(R.id.tvNombreJuego)
        val tvDescripcionJuego: TextView = itemView.findViewById(R.id.tvDescripcionJuego)
        val tvCategoriaJuego: TextView = itemView.findViewById(R.id.tvCategoriaJuego)
        val ivFavorito: ImageView = itemView.findViewById(R.id.ivFavorito) // NUEVO: icono favorito

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuegoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_juego, parent, false)
        return JuegoViewHolder(view)
    }

    override fun onBindViewHolder(holder: JuegoViewHolder, position: Int) {
        val juego = juegos[position]

        if (juego.imagenUri != null) {
            holder.ivJuego.setImageURI(Uri.parse(juego.imagenUri))
        } else {
            holder.ivJuego.setImageResource(R.drawable.ic_juego_generico)
        }

        holder.tvNombreJuego.text = juego.nombre
        holder.tvDescripcionJuego.text = juego.descripcion
        holder.tvCategoriaJuego.text = "Categoría: ${juego.categoria}"

        // Mostrar icono correcto de favorito
        if (juego.esFavorito) {
            holder.ivFavorito.setImageResource(R.drawable.ic_favorite_border)
        } else {
            holder.ivFavorito.setImageResource(R.drawable.ic_favorite_filled)
        }

        // Click en la estrella para alternar favorito
        holder.ivFavorito.setOnClickListener {
            juego.esFavorito = !juego.esFavorito
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = juegos.size
}