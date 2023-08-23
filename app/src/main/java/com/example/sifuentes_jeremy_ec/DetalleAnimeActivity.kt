package com.example.sifuentes_jeremy_ec

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class DetalleAnimeActivity : AppCompatActivity() {
    private lateinit var anime: Anime
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_anime_favorito)

        anime = intent.getSerializableExtra("anime") as Anime
        database = FirebaseDatabase.getInstance()



        val btnFavorito = findViewById<Button>(R.id.btnAddToFavorites)
        btnFavorito.setOnClickListener {
            anime.isFavorite = !anime.isFavorite

            val userId = "mobil-28e66" // Reemplaza con el ID del usuario actual
            val favoritesRef = database.reference.child("users").child(userId).child("favorites")

            if (anime.isFavorite) {
                favoritesRef.child(anime.animeName).setValue(true)
            } else {
                favoritesRef.child(anime.animeName).removeValue()
            }


            updateButtonAppearance(btnFavorito)
        }
    }

    private fun updateButtonAppearance(button: Button) {
        if (anime.isFavorite) {
            button.text = getString(R.string.rev)
        } else {
            button.text = getString(R.string.add)
        }
    }
}
