package br.ifsp.edu.dmo1.sistema_de_votos

import android.content.Context
import br.ifsp.edu.dmo1.sistema_de_votos.model.Voto

// Classe que atua como repositório para acessar e manipular os dados de "Voto"
class VotoRepository(context: Context) {

    // Cria uma instância do DatabaseHelper e do DAO (Data Access Object) para "Voto"
    private val dbHelper = DatabaseHelper(context)
    private val dao = VotoDao(dbHelper)

    // Função para adicionar um voto ao banco de dados
    fun addVoto(voto: Voto): Long = dao.inserirVoto(voto)

    // Função para buscar todos os votos cadastrados
    fun getAllVotos(): List<Voto> = dao.getAllVotos()

    // Função para buscar um voto pelo código
    fun getVotoByCodigo(codigo: String): Voto? = dao.getVotoByCodigo(codigo)
}
