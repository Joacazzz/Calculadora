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

        val nota1 = findViewById<EditText>(R.id.nota1)
        val nota2 = findViewById<EditText>(R.id.nota2)
        val nota3 = findViewById<EditText>(R.id.nota3)
        val nota4 = findViewById<EditText>(R.id.nota4)

        val botao = findViewById<Button>(R.id.botaoCalcular)
        val resultado = findViewById<TextView>(R.id.resultado)

        botao.setOnClickListener {

            val notas = listOf(
                nota1.text.toString().toDoubleOrNull(),
                nota2.text.toString().toDoubleOrNull(),
                nota3.text.toString().toDoubleOrNull(),
                nota4.text.toString().toDoubleOrNull(),
            )
            if (notas.all { it != null }) {
                val media = notas.asSequence().filterNotNull().average()

                resultado.text = if (media >= 6) {
                    getString(R.string.aluno_aprovado, media)
                } else {
                    getString(R.string.aluno_reprovado, media)
                }
            } else {
                resultado.text = getString(R.string.digite_notas)
            }
        }
    }
}
