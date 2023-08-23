package com.example.sifuentes_jeremy_ec

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sifuentes_jeremy_ec.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val animeListaFragment = AnimeLister()
    private val animeFavoritoFragment = Animefavorito()
    private val animeInfoFragment = InfoAnime()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(animeListaFragment)

        binding.btnavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_list-> setCurrentFragment(animeListaFragment)
                R.id.menu_fav -> setCurrentFragment(animeFavoritoFragment)
                R.id.menu_info-> setCurrentFragment(animeInfoFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }
}

