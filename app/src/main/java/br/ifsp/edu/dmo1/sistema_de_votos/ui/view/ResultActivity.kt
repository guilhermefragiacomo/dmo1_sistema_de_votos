package br.ifsp.edu.dmo1.sistema_de_votos.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.sistema_de_votos.R
import br.ifsp.edu.dmo1.sistema_de_votos.ui.viewmodel.ResultViewModel
import java.util.Arrays

class ResultActivity : AppCompatActivity() {
    // ViewModel que será utilizado para acessar e gerenciar os dados da atividade
    private lateinit var viewModel: ResultViewModel

    // Função chamada quando a activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result) // Define o layout da activity

        // Inicialização do ViewModel
        viewModel = ViewModelProvider(this)[ResultViewModel::class.java]

        // Referência aos elementos da interface
        val tvBad = findViewById<TextView>(R.id.tvBad)
        val tvRegular = findViewById<TextView>(R.id.tvRegular)
        val tvGood = findViewById<TextView>(R.id.tvGood)
        val tvGreat = findViewById<TextView>(R.id.tvGreat)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val list = viewModel.getAllVotes()

        // Inicializa contagem
        val listaContagem = IntArray(4) // Para votos: 0, 1, 2, 3

        try {
            for (i in list) {
                if (i.valor in 0..3) { // Valida o intervalo dos votos
                    listaContagem[i.valor]++
                } else {
                    println("Valor inválido encontrado: ${i.valor}")
                }
            }

            val totalVotes = listaContagem.sum() // Soma os votos diretamente

            // Atualiza os TextViews
            tvBad.text = getString(R.string.bad) + ": " + listaContagem[0]
            tvRegular.text = getString(R.string.regular) + ": " + listaContagem[1]
            tvGood.text = getString(R.string.good) + ": " + listaContagem[2]
            tvGreat.text = getString(R.string.great) + ": " + listaContagem[3]
            tvTotal.text = getString(R.string.total) + ": " + totalVotes
        } catch (e: Exception) {
            println("Erro: ${e.message}")
        }

        // Configura o clique do botão "Voltar"
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)  // Volta para a tela principal
            finish()
        }
    }
}