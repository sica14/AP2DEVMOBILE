package com.example.ap2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EstiloVidaActivity : AppCompatActivity() {

    private lateinit var dadosUsuario: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estilo_vida)

        dadosUsuario = intent.getParcelableExtra("USER_DATA") ?: UserData()

        val seekBarSleep = findViewById<SeekBar>(R.id.seekBarSleep)
        val tvSleepHours = findViewById<TextView>(R.id.tvSleepHours)
        val switchSmoker = findViewById<Switch>(R.id.switchSmoker)
        val rgStress = findViewById<RadioGroup>(R.id.rgStress)
        val cbWeightLoss = findViewById<CheckBox>(R.id.cbWeightLoss)
        val cbMuscleGain = findViewById<CheckBox>(R.id.cbMuscleGain)
        val cbHealth = findViewById<CheckBox>(R.id.cbHealth)
        val cbEnergy = findViewById<CheckBox>(R.id.cbEnergy)
        val btnNext = findViewById<Button>(R.id.btnNext)

        // Configurar SeekBar para horas de sono (4-12 horas)
        seekBarSleep.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val horas = 4f + (progress / 10f) // 4.0 a 12.0 horas
                tvSleepHours.text = String.format("%.1f horas", horas)
                dadosUsuario.sleepHours = horas
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Configurar Switch para fumante
        switchSmoker.setOnCheckedChangeListener { _, isChecked ->
            switchSmoker.text = if (isChecked) "Sim" else "Não"
            dadosUsuario.smoker = isChecked
        }

        btnNext.setOnClickListener {
            // Obter nível de estresse
            dadosUsuario.stressLevel = when(rgStress.checkedRadioButtonId) {
                R.id.rbStressLow -> 0
                R.id.rbStressMedium -> 1
                else -> 2
            }

            // Obter objetivos
            dadosUsuario.goals.clear()
            if (cbWeightLoss.isChecked) {
                dadosUsuario.goals.add(getString(R.string.goal_weight_loss))
            }
            if (cbMuscleGain.isChecked) {
                dadosUsuario.goals.add(getString(R.string.goal_muscle_gain))
            }
            if (cbHealth.isChecked) {
                dadosUsuario.goals.add(getString(R.string.goal_health))
            }
            if (cbEnergy.isChecked) {
                dadosUsuario.goals.add(getString(R.string.goal_energy))
            }

            val intent = Intent(this, ResultadoActivity::class.java)
            intent.putExtra("USER_DATA", dadosUsuario)
            startActivity(intent)
        }
    }
}

