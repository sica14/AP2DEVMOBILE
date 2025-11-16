package com.example.ap2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    var name: String = "",
    var exerciseFrequency: Int = 0, // 0-3 (diário a nunca)
    var exerciseType: String = "",
    var mealsPerDay: Int = 3,
    var waterIntake: Int = 0, // 0-2 (sim a não)
    var fastFoodFrequency: Int = 0, // 0-3 (diário a nunca)
    var sleepHours: Float = 7f,
    var stressLevel: Int = 1, // 0-2 (baixo a alto)
    var smoker: Boolean = false,
    var goals: MutableList<String> = mutableListOf()
) : Parcelable {

    fun calculateScore(): Int {
        var pontuacao = 0

        // Frequência de exercício (0-30 pontos)
        pontuacao += when(exerciseFrequency) {
            0 -> 30 // Diário
            1 -> 20 // 3-5 vezes
            2 -> 10 // 1-2 vezes
            else -> 0 // Raramente/nunca
        }

        // Consumo de água (0-15 pontos)
        pontuacao += when(waterIntake) {
            0 -> 15 // Sim
            1 -> 7  // Às vezes
            else -> 0 // Não
        }

        // Fast food (0-15 pontos - inverso)
        pontuacao += when(fastFoodFrequency) {
            3 -> 15 // Nunca
            2 -> 10 // Raramente
            1 -> 5  // Semanalmente
            else -> 0 // Diariamente
        }

        // Horas de sono (0-20 pontos)
        pontuacao += when {
            sleepHours >= 7f && sleepHours <= 9f -> 20
            sleepHours >= 6f && sleepHours < 7f -> 12
            sleepHours >= 5f && sleepHours < 6f -> 5
            else -> 0
        }

        // Nível de estresse (0-10 pontos - inverso)
        pontuacao += when(stressLevel) {
            0 -> 10 // Baixo
            1 -> 5  // Médio
            else -> 0 // Alto
        }

        // Penalidade para fumante
        if (!smoker) pontuacao += 10

        return pontuacao
    }

    fun getCategory(): String {
        val pontuacao = calculateScore()
        return when {
            pontuacao >= 80 -> "Excelente"
            pontuacao >= 60 -> "Bom"
            pontuacao >= 40 -> "Regular"
            else -> "Precisa Melhorar"
        }
    }

    fun getRecommendations(): List<String> {
        val recomendacoes = mutableListOf<String>()

        if (exerciseFrequency >= 2) {
            recomendacoes.add("📌 Aumente a frequência de exercícios para pelo menos 3x por semana")
        }

        if (waterIntake > 0) {
            recomendacoes.add("💧 Beba mais água! Mínimo 2 litros por dia")
        }

        if (fastFoodFrequency < 2) {
            recomendacoes.add("🥗 Reduza o consumo de fast food para melhorar sua saúde")
        }

        if (sleepHours < 7f) {
            recomendacoes.add("😴 Durma mais! O ideal é entre 7-9 horas por noite")
        }

        if (stressLevel >= 1) {
            recomendacoes.add("🧘 Pratique técnicas de relaxamento para reduzir o estresse")
        }

        if (smoker) {
            recomendacoes.add("🚭 Considere parar de fumar para melhorar significativamente sua saúde")
        }

        if (recomendacoes.isEmpty()) {
            recomendacoes.add("✅ Continue mantendo seus hábitos saudáveis!")
            recomendacoes.add("🎯 Foco em manter a consistência")
        }

        return recomendacoes
    }
}

