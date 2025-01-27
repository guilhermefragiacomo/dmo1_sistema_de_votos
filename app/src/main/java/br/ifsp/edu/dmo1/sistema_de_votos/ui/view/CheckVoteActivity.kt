package br.ifsp.edu.dmo1.sistema_de_votos.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.sistema_de_votos.R
import br.ifsp.edu.dmo1.sistema_de_votos.ui.viewmodel.CheckVoteViewModel

class CheckVoteActivity: AppCompatActivity() {
    private lateinit var viewModel : CheckVoteViewModel;

    // Função chamada quando a activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_vote) // Define o layout da activity

        viewModel = ViewModelProvider(this)[CheckVoteViewModel::class.java]

        val etCode = findViewById<EditText>(R.id.etCode);
        val btnNext = findViewById<Button>(R.id.btnNext);
        val btnBack = findViewById<Button>(R.id.btnBack);
        val tvResult = findViewById<TextView>(R.id.tvResult);

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)  // Volta para a tela principal
            finish()
        }

        btnNext.setOnClickListener {
            val codigo = etCode.text.toString().trim();

            if (codigo.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val voto = viewModel.getVoteByCode(codigo);

                    if (voto != null) {
                        val str = when (voto.valor) {
                            0 -> getString(R.string.bad);
                            1 -> getString(R.string.regular);
                            2 -> getString(R.string.good);
                            3 -> getString(R.string.great);
                            else -> getString(R.string.havent_voted_yet)
                        }
                        tvResult.setText("Seu voto foi: " + str);
                    } else {
                        tvResult.setText(getString(R.string.havent_voted_yet));
                    }
                } catch (e : Exception) {
                    System.out.println(e);
                }
            }
        }
    }
}