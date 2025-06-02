package com.example.juegosfavoritosapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JuegoAdapter(private val juegos: List<Juego>) : RecyclerView.Adapter<JuegoAdapter.JuegoViewHolder>() {

    // ViewHolder: se encarga de "guardar" las vistas de cada ítem
    inner class JuegoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivJuego: ImageView = itemView.findViewById(R.id.ivJuego)
        val tvNombreJuego: TextView = itemView.findViewById(R.id.tvNombreJuego)
        val tvDescripcionJuego: TextView = itemView.findViewById(R.id.tvDescripcionJuego)
    }

    // Infla la vista de cada ítem (usa item_juego.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuegoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_juego, parent, false)
        return JuegoViewHolder(view)
    }

    // Llena las vistas con los datos del juego actual
    override fun onBindViewHolder(holder: JuegoViewHolder, position: Int) {
        val juego = juegos[position]
        holder.ivJuego.setImageResource(juego.imagenResId)
        holder.tvNombreJuego.text = juego.nombre
        holder.tvDescripcionJuego.text = juego.descripcion
    }

    // Dice cuántos ítems tiene la lista
    override fun getItemCount(): Int = juegos.size
}
