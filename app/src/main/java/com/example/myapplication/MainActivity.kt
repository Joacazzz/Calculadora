package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etPeso = findViewById<EditText>(R.id.etPeso)
        val etAltura = findViewById<EditText>(R.id.etAltura)

        val botao = findViewById<Button>(R.id.btnCalcularIMC)
        val resultado = findViewById<TextView>(R.id.resultado)

        botao.setOnClickListener {
            val peso = etPeso.text.toString().toDoubleOrNull()
            val altura = etAltura.text.toString().toDoubleOrNull()

            if (peso != null && altura != null && altura > 0) {
                val imc = peso / (altura * altura)

                val categoria = when {
                    imc < 18.5 -> getString(R.string.imc_abaixo)
                    imc < 25 -> getString(R.string.imc_normal)
                    imc < 30 -> getString(R.string.imc_sobrepeso)
                    else -> getString(R.string.imc_obesidade)
                }

                resultado.text = getString(R.string.imc_resultado, imc, categoria)
            } else {
                resultado.text = getString(R.string.preencha_campos)
            }
        }
    }
}
