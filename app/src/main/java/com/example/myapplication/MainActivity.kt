package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

// Definição das regras de cada loteria
enum class LotteryType(
    val title: String,
    val maxNumber: Int,
    val minSelectable: Int,
    val maxSelectable: Int,
    val includeZero: Boolean = false
) {
    MEGA_SENA("Mega-Sena", 60, 6, 20),
    QUINA("Quina", 80, 5, 15),
    LOTOFACIL("Lotofácil", 25, 15, 20),
    LOTOMANIA("Lotomania", 99, 50, 50, true)
}

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerLottery: Spinner
    private lateinit var etQtdNumeros: EditText
    private lateinit var etQtdJogos: EditText
    private lateinit var tvResult: TextView
    private lateinit var currentLottery: LotteryType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinnerLottery = findViewById(R.id.spinnerLottery)
        etQtdNumeros = findViewById(R.id.etQtdNumeros)
        etQtdJogos = findViewById(R.id.etQtdJogos)
        tvResult = findViewById(R.id.tvResult)
        val btnGenerate = findViewById<Button>(R.id.btnGenerate)

        setupSpinner()

        btnGenerate.setOnClickListener {
            generateGames()
        }
    }

    private fun setupSpinner() {
        spinnerLottery.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentLottery = LotteryType.values()[position]
                
                // Atualiza o campo de quantidade de números com o mínimo da loteria selecionada
                etQtdNumeros.setText(currentLottery.minSelectable.toString())
                
                // Se for Lotomania, o usuário não pode mudar a quantidade (sempre 50)
                etQtdNumeros.isEnabled = currentLottery != LotteryType.LOTOMANIA
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun generateGames() {
        val qtdNumerosStr = etQtdNumeros.text.toString()
        val qtdJogosStr = etQtdJogos.text.toString()

        if (qtdNumerosStr.isEmpty() || qtdJogosStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val qtdNumeros = qtdNumerosStr.toInt()
        val qtdJogos = qtdJogosStr.toInt()

        // Validação da quantidade de jogos
        if (qtdJogos <= 0) {
            Toast.makeText(this, getString(R.string.error_invalid_games), Toast.LENGTH_SHORT).show()
            return
        }

        // Validação da quantidade de números permitida para a loteria atual
        if (qtdNumeros < currentLottery.minSelectable || qtdNumeros > currentLottery.maxSelectable) {
            Toast.makeText(this, getString(R.string.error_invalid_numbers), Toast.LENGTH_SHORT).show()
            return
        }

        val resultBuilder = StringBuilder()

        for (i in 1..qtdJogos) {
            val gameNumbers = generateSingleGame(qtdNumeros, currentLottery)
            
            // Adiciona um título para o jogo se houver mais de um
            if (qtdJogos > 1) {
                resultBuilder.append("Jogo $i:\n")
            }
            
            // Formata os números
            val formattedGame = gameNumbers.joinToString(" - ") { it.toString().padStart(2, '0') }
            resultBuilder.append(formattedGame).append("\n\n")
        }

        tvResult.text = resultBuilder.toString().trim()
    }

    private fun generateSingleGame(qtdNumeros: Int, lottery: LotteryType): List<Int> {
        val numbers = mutableSetOf<Int>()
        val startRange = if (lottery.includeZero) 0 else 1
        val endRange = lottery.maxNumber + 1 // +1 porque Random.nextInt é exclusivo no limite superior

        while (numbers.size < qtdNumeros) {
            numbers.add(Random.nextInt(startRange, endRange))
        }
        return numbers.sorted()
    }
}