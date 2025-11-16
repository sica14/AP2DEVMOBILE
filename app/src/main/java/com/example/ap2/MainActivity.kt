package com.example.ap2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val dadosUsuario = UserData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnStart)
        val etName = findViewById<EditText>(R.id.etName)

        btnStart.setOnClickListener {
            val nome = etName.text.toString().trim()

            if (nome.isEmpty()) {
                Toast.makeText(this, "Por favor, digite seu nome", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            dadosUsuario.name = nome

            val intent = Intent(this, AtividadeFisicaActivity::class.java)
            intent.putExtra("USER_DATA", dadosUsuario)
            startActivity(intent)
        }
    }
}
