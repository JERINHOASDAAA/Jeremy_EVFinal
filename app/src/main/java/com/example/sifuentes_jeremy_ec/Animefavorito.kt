package com.example.sifuentes_jeremy_ec

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Animefavorito : Fragment(), AnimeFavoritosAdapter.AnimeItemClickListener {

    private lateinit var rvAnimeFavoritos: RecyclerView
    private lateinit var animeFavoritosAdapter: AnimeFavoritosAdapter
    private val animeFavoritosList: ArrayList<Anime> = ArrayList()
    private val animeList: List<Anime> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_animefavorito, container, false)

        rvAnimeFavoritos = view.findViewById(R.id.rcvFavoritos)
        animeFavoritosAdapter = AnimeFavoritosAdapter(animeFavoritosList, this)
        rvAnimeFavoritos.adapter = animeFavoritosAdapter
        rvAnimeFavoritos.layoutManager = LinearLayoutManager(context)

        return view
    }


    override fun onAddToFavoritesClicked(anime: Anime) {

        if (!animeFavoritosList.contains(anime)) {
            animeFavoritosList.add(anime)
            animeFavoritosAdapter.notifyDataSetChanged()
        }
    }

    override fun onRemoveFromFavoritesClicked(anime: Anime) {

        if (animeFavoritosList.contains(anime)) {
            animeFavoritosList.remove(anime)
            animeFavoritosAdapter.notifyDataSetChanged()
        }
    }
}