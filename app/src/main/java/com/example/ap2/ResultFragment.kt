package com.example.ap2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ResultFragment : Fragment() {

    private var dadosUsuario: UserData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dadosUsuario = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_USER_DATA, UserData::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(ARG_USER_DATA)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dadosUsuario?.let { dados ->
            val textoCategoria = view.findViewById<TextView>(R.id.tvCategory)
            val textoPontuacao = view.findViewById<TextView>(R.id.tvScore)
            val textoRecomendacoes = view.findViewById<TextView>(R.id.tvRecommendations)

            textoCategoria.text = "Categoria: ${dados.getCategory()}"
            textoPontuacao.text = "Pontuação: ${dados.calculateScore()}/100"

            val recomendacoes = dados.getRecommendations()
            textoRecomendacoes.text = recomendacoes.joinToString("\n\n")
        }
    }

    companion object {
        private const val ARG_USER_DATA = "user_data"

        fun newInstance(dadosUsuario: UserData) = ResultFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_USER_DATA, dadosUsuario)
            }
        }
    }
}

