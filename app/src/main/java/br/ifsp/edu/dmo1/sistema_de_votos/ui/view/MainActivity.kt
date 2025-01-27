package br.ifsp.edu.dmo1.sistema_de_votos.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.ifsp.edu.dmo1.sistema_de_votos.R

// Classe que representa a tela principal (MainActivity) da aplicação
class MainActivity : AppCompatActivity() {

    // Função chamada quando a activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Define o layout da activity

        // Referência aos botões da interface
        val btnParticipar = findViewById<Button>(R.id.btnParticipate)
        val btnChecarVoto = findViewById<Button>(R.id.btnCheckVote)
        val btnFinalizar = findViewById<Button>(R.id.btnEnd)

        // Configura o clique do botão "Participar"
        btnParticipar.setOnClickListener {
            val intent = Intent(this, ParticipateActivity::class.java) // Cria a intent para abrir a tela de participar
            startActivity(intent) // Inicia a activity de participação
        }

        // Configura o clique do botão "Checar Voto"
        btnChecarVoto.setOnClickListener {
            val intent = Intent(this, CheckVoteActivity::class.java) // Cria a intent para abrir a tela de checar voto
            startActivity(intent) // Inicia a activity para checar voto
        }

        // Configura o clique do botão "Finalizar"
        btnFinalizar.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java) // Cria a intent para abrir a tela de resultados
            startActivity(intent) // Inicia a activity de resultados
        }
    }
}
