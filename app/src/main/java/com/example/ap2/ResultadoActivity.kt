package com.example.ap2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultadoActivity : AppCompatActivity() {

    private lateinit var dadosUsuario: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        dadosUsuario = intent.getParcelableExtra("USER_DATA") ?: UserData()

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        val btnShare = findViewById<Button>(R.id.btnShare)
        val btnLearnMore = findViewById<Button>(R.id.btnLearnMore)
        val btnRestart = findViewById<Button>(R.id.btnRestart)

        // Exibir nome do usuário
        tvGreeting.text = "Olá, ${dadosUsuario.name}!"

        // Carregar Fragment de Resultados
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ResultFragment.newInstance(dadosUsuario))
            .commit()

        // Botão Compartilhar - Intent Implícita
        btnShare.setOnClickListener {
            val textoCompartilhar = """
                Meu resultado no Quiz de Saúde! 🏃‍♂️
                
                Categoria: ${dadosUsuario.getCategory()}
                Pontuação: ${dadosUsuario.calculateScore()}/100
                
                Baixe o app e descubra seu perfil fitness!
            """.trimIndent()

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textoCompartilhar)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Compartilhar resultado"))
        }

        // Botão Saiba Mais - Abrir Repositório GitHub
        btnLearnMore.setOnClickListener {
            val url = "https://github.com/sica14/AP2DEVMOBILE"
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(webIntent)
        }

        // Botão Refazer
        btnRestart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}

