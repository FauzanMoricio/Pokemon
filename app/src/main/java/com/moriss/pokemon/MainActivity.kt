package com.moriss.pokemon

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.moriss.pokemon.model.Pokemon
import com.moriss.pokemon.network.PokemonApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvHeight: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvBaseExperience: TextView
    private lateinit var ivPokemon: ImageView
    private lateinit var ivPokemonBack: ImageView
    private lateinit var tvStats: TextView
    private lateinit var tvAbilities: TextView
    private lateinit var tvType: TextView
    private lateinit var etPokemonName: EditText
    private lateinit var btnFetch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi View
        tvName = findViewById(R.id.tvName)
        tvHeight = findViewById(R.id.tvHeight)
        tvWeight = findViewById(R.id.tvWeight)
        tvBaseExperience = findViewById(R.id.tvBaseExperience)
        ivPokemon = findViewById(R.id.ivPokemon)
        ivPokemonBack = findViewById(R.id.ivPokemonBack) // Inisialisasi ivPokemonBack
        tvStats = findViewById(R.id.tvStats)
        tvType = findViewById(R.id.tvType)
        tvAbilities = findViewById(R.id.tvAbilities)
        etPokemonName = findViewById(R.id.etPokemonName)
        btnFetch = findViewById(R.id.btnFetch)

        // Ambil data Pikachu secara default saat aplikasi dimulai
        fetchPokemon()

        // Ambil data  nama Pokémon saat tombol diklik
        btnFetch.setOnClickListener {
            val pokemonName = etPokemonName.text.toString().trim().toLowerCase()
            fetchPokemon(pokemonName)
        }
    }

    private fun fetchPokemon(name: String? = null) {
        // Gunakan Pikachu jika nama tidak diberikan
        val pokemonName = name?.takeIf { it.isNotEmpty() } ?: "pikachu"

        val call = PokemonApi.api.getPokemon(pokemonName)

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    pokemon?.let {
                        tvName.text = "Name: ${it.name}"
                        tvHeight.text = "Height: ${it.height}"
                        tvWeight.text = "Weight: ${it.weight}"
                        tvBaseExperience.text = "Base Experience: ${it.baseExperience}"

                        // Memuat gambar depan Pokémon
                        Glide.with(this@MainActivity)
                            .load(it.sprites.frontDefault)
                            .into(ivPokemon)

                        // Memuat gambar belakang Pokémon
                        Glide.with(this@MainActivity)
                            .load(it.sprites.backDefault)
                            .into(ivPokemonBack)

                        val statsText = it.stats.joinToString(separator = "\n") { stat ->
                            "${stat.stat.name.capitalize()}: ${stat.baseStat}"
                        }
                        tvStats.text = "Stats:\n$statsText"

                        val typesText = it.types.joinToString(separator = "\n") { type ->
                            type.type.name.capitalize()
                        }
                        tvType.text = "Type:\n$typesText"

                        val abilitiesText = it.abilities.joinToString(separator = "\n") { ability ->
                            ability.ability.name.capitalize()
                        }
                        tvAbilities.text = "Abilities:\n$abilitiesText"
                    }
                } else {
                    tvName.text = "Request failed with code: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                tvName.text = "Request failed: ${t.message}"
            }
        })
    }
}
