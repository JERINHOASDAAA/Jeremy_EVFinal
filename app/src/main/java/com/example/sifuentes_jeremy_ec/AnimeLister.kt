package com.example.sifuentes_jeremy_ec

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AnimeLister : Fragment() {

    private lateinit var rvAnimeList: RecyclerView
    private lateinit var animeListAdapter: AnimeListAdapter
    private val animeList: ArrayList<Anime> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_anime_lista, container, false)

        rvAnimeList = view.findViewById(R.id.rcvlista)
        animeListAdapter = AnimeListAdapter(animeList)
        rvAnimeList.adapter = animeListAdapter
        rvAnimeList.layoutManager = LinearLayoutManager(context)

        cargarAnimes()

        return view
    }

    private fun cargarAnimes() {
        val url = "https://randomuser.me/api/?results=10"

        val requestQueue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val results = response.getJSONArray("results")
                    Log.d("AnimeLister", "Results length: ${results.length()}")
                    for (i in 0 until results.length()) {
                        val userObj = results.getJSONObject(i)
                        val nameObj = userObj.getJSONObject("name")
                        val animeName = "${nameObj.getString("first")} ${nameObj.getString("last")}"
                        val pictureObj = userObj.getJSONObject("picture")
                        val animeImgUrl = pictureObj.getString("large")

                        val anime = Anime(animeName, animeImgUrl, false) // Crear instancia de Anime
                        animeList.add(anime)
                    }

                    animeListAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

private inner class AnimeListAdapter(private val animeList: List<Anime>) :

        RecyclerView.Adapter<AnimeListAdapter.AnimeViewHolder>() {



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)

            view.setOnClickListener {
                val position = rvAnimeList.getChildAdapterPosition(view)
                if (position != RecyclerView.NO_POSITION) {
                    val anime = animeList[position]

                    val intent = Intent(view.context, DetalleAnimeActivity::class.java)
                    intent.putExtra("anime", anime)
                    view.context.startActivity(intent)
                }
            }

            return AnimeViewHolder(view)
        }

        override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
            holder.bind(animeList[position])
        }

        override fun getItemCount(): Int {
            return animeList.size
        }

        inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val tvAnimeName: TextView = itemView.findViewById(R.id.tvname)
            private val ivAnimeImage: ImageView = itemView.findViewById(R.id.ivfoto)

            fun bind(anime: Anime) {
                tvAnimeName.text = anime.animeName

                Glide.with(itemView)
                    .load(anime.animeImgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivAnimeImage)
            }
        }
    }
}
