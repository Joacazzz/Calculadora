
package com.example.media

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

            val n1 = nota1.text.toString().toDoubleOrNull()
            val n2 = nota2.text.toString().toDoubleOrNull()
            val n3 = nota3.text.toString().toDoubleOrNull()
            val n4 = nota4.text.toString().toDoubleOrNull()

            if (n1 != null && n2 != null && n3 != null && n4 != null) {

                val media = (n1 + n2 + n3 + n4) / 4

                if (media >= 6) {
                    resultado.text = "Média: %.2f\nAluno APROVADO!".format(media)
                } else {
                    resultado.text = "Média: %.2f\nAluno REPROVADO!".format(media)
                }

            } else {
                resultado.text = "Digite todas as notas."
            }
        }
    }
}
