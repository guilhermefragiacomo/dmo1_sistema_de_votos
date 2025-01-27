package br.ifsp.edu.dmo1.sistema_de_votos.ui.view

import ParticipateViewModel
import android.R.id
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.sistema_de_votos.R


class ParticipateActivity : AppCompatActivity() {
    // ViewModel que será utilizado para acessar e gerenciar os dados da atividade
    private lateinit var viewModel: ParticipateViewModel

    // Função chamada quando a activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participate) // Define o layout da activity

        // Inicialização do ViewModel
        viewModel = ViewModelProvider(this)[ParticipateViewModel::class.java]

        // Referência aos elementos da interface
        val etRecord = findViewById<EditText>(R.id.etRecord)
        val etName = findViewById<EditText>(R.id.etName)
        val rgOpinions = findViewById<RadioGroup>(R.id.rgOpinions)
        val btnSend = findViewById<Button>(R.id.btnSend)

        // Configura o clique do botão "Enviar"
        btnSend.setOnClickListener {
            val prontuario = etRecord.text.toString().trim()  // Obtém o prontuário digitado
            val nome = etName.text.toString().trim()  // Obtém o nome digitado
            val opiniaoSelecionada = findViewById<RadioButton>(rgOpinions.getCheckedRadioButtonId());

            System.out.println(opiniaoSelecionada);

            // Validação dos campos
            if (prontuario.isEmpty() || nome.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show() // Exibe mensagem de erro se campos estiverem vazios
            } else if (opiniaoSelecionada == null) {
                Toast.makeText(this, getString(R.string.select_op), Toast.LENGTH_SHORT).show() // Exibe mensagem de erro se nenhuma opinião for selecionada
            } else {
                // Verifica se o estudante já votou
                val estudante = viewModel.getByProntuario(prontuario)
                if (estudante != null) {
                    Toast.makeText(this, estudante.nome + " " + getString(R.string.already_voted), Toast.LENGTH_LONG).show() // Exibe mensagem caso o estudante já tenha votado
                } else {
                    var codigo = ""  // Inicializa a variável para armazenar o código de voto
                    try {
                        // Adiciona o estudante e o voto
                        val opiniao : Int = when (opiniaoSelecionada.text) {
                            getString(R.string.bad) -> 0;
                            getString(R.string.regular) -> 1;
                            getString(R.string.good) -> 2;
                            getString(R.string.great) -> 3;
                            else -> -1;
                        }

                        System.out.println(opiniao);

                        viewModel.addEstudante(prontuario, nome)
                        codigo = viewModel.addVoto(opiniao) // Armazena o código do voto
                    } catch (e: Exception) {
                        // Em caso de erro ao inserir, exibe mensagem de erro e volta para a tela principal
                        Toast.makeText(this, getString(R.string.error_send), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                        finish()
                    }

                    // Altera para a tela onde o código do voto será exibido
                    setContentView(R.layout.activity_code)
                    val tvCode = findViewById<TextView>(R.id.tvCode)
                    val btnBack = findViewById<Button>(R.id.btnBack)
                    val btnCopy = findViewById<ImageButton>(R.id.btnCopy)

                    tvCode.text = codigo  // Exibe o código de voto

                    // Configura o clique do botão "Copiar"
                    btnCopy.setOnClickListener {
                        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText(getString(R.string.vote_code), codigo)
                        clipboard.setPrimaryClip(clip)  // Copia o código para a área de transferência
                        Toast.makeText(this, getString(R.string.code_copyied), Toast.LENGTH_SHORT).show() // Exibe mensagem de sucesso
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
        }
    }
}
