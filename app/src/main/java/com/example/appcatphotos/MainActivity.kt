package com.example.appcatphotos

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcatphotos.API.RetrofitCustom
import com.example.appcatphotos.databinding.ActivityMainBinding
import com.jamiltondamasceno.aulatesteempregoandroid.api.model.Resultado
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var galeriaAdapter: GaleriaAdapter

    private val imgurAPI by lazy {
        RetrofitCustom.imgurAPI
    }

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        galeriaAdapter = GaleriaAdapter()
        binding.rvGaleria.adapter = galeriaAdapter
        binding.rvGaleria.layoutManager = GridLayoutManager(
            this,
            3,
            RecyclerView.VERTICAL,
            false
        )
    }

    override fun onStart() {
        super.onStart()
        recuperarImagensAPI()
}

        override fun onDestroy() {
            super.onDestroy()
            job?.cancel()
        }

        fun recuperarImagensAPI() {

            job = CoroutineScope(Dispatchers.IO).launch {
                var response: Response<Resultado>? = null

                try {
                    imgurAPI.pesquisarImagensGaleria("cats")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (response != null && response.isSuccessful) {


                    val resultado = response.body()
                    if (resultado != null) {

                        val lista = resultado.data
                        val listaUrlImagens = mutableListOf<String>()
                        lista.forEach { dados ->
                            val imagem = dados.images[0]
                            val tipo = imagem.type
                            if (tipo == "image/jpeg" || tipo == "image/png") {
                                listaUrlImagens.add(imagem.link)
                            }
                        }

                        WithContext(Dispatchers.Main) {
                            galeriaAdapter.adicionarLista(listaUrlImagens)
                        }
                    }
                } else {
                    Log.i("Teste_galeria", "erro ao recuperar imagens ")
                }
            }
        }
    }
