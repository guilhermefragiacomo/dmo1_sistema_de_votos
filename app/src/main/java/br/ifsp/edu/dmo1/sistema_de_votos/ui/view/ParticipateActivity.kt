package br.ifsp.edu.dmo1.sistema_de_votos.ui.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.sistema_de_votos.R
import br.ifsp.edu.dmo1.sistema_de_votos.ui.viewmodel.ParticipateViewModel


class ParticipateActivity : AppCompatActivity() {
    private lateinit var viewModel: ParticipateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participate)

        viewModel = ViewModelProvider(this)[ParticipateViewModel::class.java]
        val etRecord = findViewById<EditText>(R.id.etRecord)
        val etName = findViewById<EditText>(R.id.etName)
        val rgOpinions = findViewById<RadioGroup>(R.id.rgOpinions);
        val btnSend = findViewById<Button>(R.id.btnSend)

        btnSend.setOnClickListener {
            val prontuario = etRecord.text.toString().trim()
            val nome = etName.text.toString().trim()

            val opiniaoSelecionada = rgOpinions.checkedRadioButtonId

            if (prontuario.isEmpty() || nome.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            } else if (opiniaoSelecionada == -1) {
                Toast.makeText(this, getString(R.string.select_op), Toast.LENGTH_SHORT).show()
            } else {
                val estudante = viewModel.getByProntuario(prontuario);
                if (estudante != null) {
                    Toast.makeText(this, estudante.nome + " " + getString(R.string.already_voted), Toast.LENGTH_LONG).show()
                } else {
                    var codigo = "";
                    try {
                        viewModel.addEstudante(prontuario, nome);
                        codigo = viewModel.addVoto(opiniaoSelecionada);
                    } catch (e : Exception) {
                        Toast.makeText(this, getString(R.string.error_send), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        };
                        startActivity(intent);
                        finish()
                    }
                    setContentView(R.layout.activity_code);
                    val tvCode = findViewById<TextView>(R.id.tvCode);
                    val btnBack = findViewById<Button>(R.id.btnBack);
                    val btnCopy = findViewById<ImageButton>(R.id.btnCopy);

                    tvCode.setText(codigo);

                    btnCopy.setOnClickListener {
                        val clipboard: ClipboardManager =
                            getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText(getString(R.string.vote_code), codigo);
                        clipboard.setPrimaryClip(clip)

                        Toast.makeText(this, getString(R.string.code_copyied), Toast.LENGTH_SHORT).show()
                    }

                    btnBack.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        };
                        startActivity(intent);
                        finish()
                    }
                }
            }
        }
    }
}