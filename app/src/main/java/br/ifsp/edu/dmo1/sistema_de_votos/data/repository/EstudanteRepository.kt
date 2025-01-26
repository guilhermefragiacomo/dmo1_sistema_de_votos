package br.ifsp.edu.dmo1.sistema_de_votos

import android.content.Context

class EstudanteRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private val dao = EstudanteDao(dbHelper)

    fun addEstudante(estudante: Estudante) : Long = dao.inserirEstudante(estudante)

    fun getByProntuario(prontuario: String): Estudante? = dao.getByProntuario(prontuario)

    fun getAllEstudantes(): List<Estudante> = dao.getAllEstudantes()

}