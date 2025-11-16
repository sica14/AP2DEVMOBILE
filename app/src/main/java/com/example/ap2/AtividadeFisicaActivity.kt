package com.example.ap2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AtividadeFisicaActivity : AppCompatActivity() {

    private lateinit var dadosUsuario: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atividade_fisica)

        dadosUsuario = intent.getParcelableExtra("USER_DATA") ?: UserData()

        val rgFrequency = findViewById<RadioGroup>(R.id.rgFrequency)
        val spinnerExerciseType = findViewById<Spinner>(R.id.spinnerExerciseType)
        val btnNext = findViewById<Button>(R.id.btnNext)

        // Configurar Spinner
        val tiposExercicio = arrayOf(
            getString(R.string.exercise_cardio),
            getString(R.string.exercise_strength),
            getString(R.string.exercise_yoga),
            getString(R.string.exercise_sports),
            getString(R.string.exercise_none)
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposExercicio)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerExerciseType.adapter = adapter

        btnNext.setOnClickListener {
            // Obter frequência de exercício do RadioGroup
            dadosUsuario.exerciseFrequency = when(rgFrequency.checkedRadioButtonId) {
                R.id.rbDaily -> 0
                R.id.rb3to5 -> 1
                R.id.rb1to2 -> 2
                else -> 3
            }

            // Obter tipo de exercício do Spinner
            dadosUsuario.exerciseType = spinnerExerciseType.selectedItem.toString()

            val intent = Intent(this, NutricaoActivity::class.java)
            intent.putExtra("USER_DATA", dadosUsuario)
            startActivity(intent)
        }
    }
}

