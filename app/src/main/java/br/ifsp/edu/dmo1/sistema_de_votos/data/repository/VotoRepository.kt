package br.ifsp.edu.dmo1.sistema_de_votos

import android.content.Context

class VotoRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private val dao = VotoDao(dbHelper)

    fun addVoto(voto: Voto) : Long = dao.inserirVoto(voto)

    fun getAllVotos(): List<Voto> = dao.getAllVotos()

    fun getVotoByCodigo(codigo: String): Voto? = dao.getVotoByCodigo(codigo)

}