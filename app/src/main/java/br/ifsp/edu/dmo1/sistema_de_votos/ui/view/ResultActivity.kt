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
        val tvTotal = findViewById<TextView>(R.id.tvTotal);
        val btnBack = findViewById<Button>(R.id.btnBack)

        val list = viewModel.getAllVotes();

        var lista_contagem = ArrayList<Int>(Arrays.asList(0,0,0,0,0));

        try {
            for (i in list) {
                System.out.println(i.valor);
                lista_contagem[i.valor]++;
                lista_contagem[4]++;
            }

            tvBad.setText(getString(R.string.bad) + ": " + (lista_contagem[0].toString()));
            tvRegular.setText(getString(R.string.regular) + ": " + (lista_contagem[1].toString()));
            tvGood.setText(getString(R.string.good) + ": " + (lista_contagem[2].toString()));
            tvGreat.setText(getString(R.string.great) + ": " + (lista_contagem[3].toString()));
            tvTotal.setText(getString(R.string.total) + ": " + (lista_contagem[4].toString()));

        } catch (e : Exception) {
            System.out.println(e);
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