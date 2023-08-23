package com.example.sifuentes_jeremy_ec

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AnimeFavoritosAdapter(
    private val animeFavoritosList: List<Anime>,
    private val animeItemClickListener: AnimeItemClickListener
) : RecyclerView.Adapter<AnimeFavoritosAdapter.AnimeFavoritoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeFavoritoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime_favorito, parent, false)
        return AnimeFavoritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeFavoritoViewHolder, position: Int) {
        holder.bind(animeFavoritosList[position])
    }

    override fun getItemCount(): Int {
        return animeFavoritosList.size
    }

    inner class AnimeFavoritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvAnimeName: TextView = itemView.findViewById(R.id.tvname)
        private val ivAnimeImage: ImageView = itemView.findViewById(R.id.ivfoto)
        private val btnAddToFavorites: Button = itemView.findViewById(R.id.btnAddToFavorites)
        private val btnRemoveFromFavorites: Button = itemView.findViewById(R.id.btnRemoveFromFavorites)

        fun bind(anime: Anime) {
            tvAnimeName.text = anime.animeName

            Glide.with(itemView)
                .load(anime.animeImgUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivAnimeImage)


            btnAddToFavorites.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val animeToAdd = animeFavoritosList[position]
                    animeItemClickListener.onAddToFavoritesClicked(animeToAdd)
                }
            }

            btnRemoveFromFavorites.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val animeToRemove = animeFavoritosList[position]
                    animeItemClickListener.onRemoveFromFavoritesClicked(animeToRemove)
                }
            }
        }
    }


    interface AnimeItemClickListener {
        fun onAddToFavoritesClicked(anime: Anime)
        fun onRemoveFromFavoritesClicked(anime: Anime)
    }
}
