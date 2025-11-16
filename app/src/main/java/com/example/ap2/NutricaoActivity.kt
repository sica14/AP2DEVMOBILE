package com.example.ap2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NutricaoActivity : AppCompatActivity() {

    private lateinit var dadosUsuario: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutricao)

        dadosUsuario = intent.getParcelableExtra("USER_DATA") ?: UserData()

        val seekBarMeals = findViewById<SeekBar>(R.id.seekBarMeals)
        val tvMealsCount = findViewById<TextView>(R.id.tvMealsCount)
        val rgWater = findViewById<RadioGroup>(R.id.rgWater)
        val rgFastFood = findViewById<RadioGroup>(R.id.rgFastFood)
        val btnNext = findViewById<Button>(R.id.btnNext)

        // Configurar SeekBar para refeições
        seekBarMeals.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val refeicoes = progress + 1 // 1-6 refeições
                tvMealsCount.text = "$refeicoes refeições"
                dadosUsuario.mealsPerDay = refeicoes
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnNext.setOnClickListener {
            // Obter consumo de água
            dadosUsuario.waterIntake = when(rgWater.checkedRadioButtonId) {
                R.id.rbWaterYes -> 0
                R.id.rbWaterSometimes -> 1
                else -> 2
            }

            // Obter frequência de fast food
            dadosUsuario.fastFoodFrequency = when(rgFastFood.checkedRadioButtonId) {
                R.id.rbFastFoodDaily -> 0
                R.id.rbFastFoodWeekly -> 1
                R.id.rbFastFoodRarely -> 2
                else -> 3
            }

            val intent = Intent(this, EstiloVidaActivity::class.java)
            intent.putExtra("USER_DATA", dadosUsuario)
            startActivity(intent)
        }
    }
}

