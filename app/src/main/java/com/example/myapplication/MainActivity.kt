package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Nossa lógica escondida: guarda o número "vencedor" quando o app abre
    private var hiddenNumbers: List<Int> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Sorteamos a "lógica escondida" que o usuário terá que acertar
        hiddenNumbers = generateMegaSenaNumbers()
        
        // Logcat para ajudar você (desenvolvedor) a debugar/testar o resultado "Parabéns"
        Log.d("SorteioOculto", "Números escondidos para ganhar: $hiddenNumbers")

        val btnGenerate = findViewById<Button>(R.id.btnGenerate)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val tvFeedback = findViewById<TextView>(R.id.tvFeedback)

        btnGenerate.setOnClickListener {
            // Gera a aposta do usuário e exibe na tela
            val userNumbers = generateMegaSenaNumbers()
            tvResult.text = userNumbers.joinToString(" - ") { it.toString().padStart(2, '0') }

            // Verifica a intersecção entre a aposta do usuário e o sorteio escondido
            val acertos = userNumbers.intersect(hiddenNumbers.toSet()).size

            // Compara os resultados e exibe mensagens dinâmicas
            if (acertos == 6) { // Para Mega-Sena seriam 6 acertos cravados
                tvFeedback.text = "Parabéns!\nVocê acertou todos os números escondidos!"
                tvFeedback.setTextColor(Color.parseColor("#4CAF50")) // Verde
            } else {
                tvFeedback.text = "Você acertou $acertos número(s).\nTente novamente!"
                tvFeedback.setTextColor(Color.parseColor("#757575")) // Cinza
            }
        }
    }

    private fun generateMegaSenaNumbers(): List<Int> {
        val numbers = mutableSetOf<Int>()
        // Ajuste aqui se quiser sortear *mais números* (ex: 15)
        while (numbers.size < 6) { 
            // Números de 1 a 60
            numbers.add(Random.nextInt(1, 61))
        }
        return numbers.sorted()
    }
}