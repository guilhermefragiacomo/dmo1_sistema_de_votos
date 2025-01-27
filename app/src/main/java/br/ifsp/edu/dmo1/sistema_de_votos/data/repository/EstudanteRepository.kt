package br.ifsp.edu.dmo1.sistema_de_votos

import android.content.Context
import br.ifsp.edu.dmo1.sistema_de_votos.model.Estudante

// Classe que atua como repositório para acessar e manipular os dados de "Estudante"
class EstudanteRepository(context: Context) {

    // Cria uma instância do DatabaseHelper e do DAO (Data Access Object) para "Estudante"
    private val dbHelper = DatabaseHelper(context)
    private val dao = EstudanteDao(dbHelper)

    // Função para adicionar um estudante ao banco de dados
    fun addEstudante(estudante: Estudante): Long = dao.inserirEstudante(estudante)

    // Função para buscar um estudante pelo prontuário
    fun getByProntuario(prontuario: String): Estudante? = dao.getByProntuario(prontuario)

    // Função para buscar todos os estudantes cadastrados
    fun getAllEstudantes(): List<Estudante> = dao.getAllEstudantes()
}
