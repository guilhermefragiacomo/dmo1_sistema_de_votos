package br.ifsp.edu.dmo1.sistema_de_votos.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.ifsp.edu.dmo1.sistema_de_votos.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnParticipar = findViewById<Button>(R.id.btnParticipate)
        val btnChecarVoto = findViewById<Button>(R.id.btnCheckVote)
        val btnFinalizar = findViewById<Button>(R.id.btnEnd)

        btnParticipar.setOnClickListener {
            val intent = Intent(this, ParticipateActivity::class.java)
            startActivity(intent)
        }

        btnChecarVoto.setOnClickListener {
            val intent = Intent(this, CheckVoteActivity::class.java)
            startActivity(intent)
        }

        btnFinalizar.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}