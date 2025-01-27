package br.ifsp.edu.dmo1.sistema_de_votos

import android.content.ContentValues

// Classe DAO para manipular os dados da tabela "estudante"
class EstudanteDao(private val dbHelper: DatabaseHelper) {

    // Insere um novo estudante na tabela
    fun inserirEstudante(estudante: Estudante): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO, estudante.prontuario) // Define o prontuário
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_NOME, estudante.nome) // Define o nome
        }

        return db.insert(DatabaseHelper.DATABASE_KEYS.TABLE_ESTUDANTE_NAME, null, values) // Insere o registro e retorna o ID
    }

    // Busca um estudante pelo prontuário
    fun getByProntuario(prontuario: String): Estudante? {
        val estudante: Estudante?
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO, // Coluna do prontuário
            DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_NOME // Coluna do nome
        )

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO} = ?" // Condição de busca
        val whereArgs = arrayOf(prontuario) // Argumento da condição

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_ESTUDANTE_NAME, // Tabela "estudante"
            columns, // Colunas a serem retornadas
            where, // Condição de filtro
            whereArgs, // Valores para a condição
            null, // Sem agrupamento
            null, // Sem filtro por grupos
            null // Sem ordenação
        )

        cursor.use {
            estudante = if (cursor.moveToNext()) { // Verifica se encontrou um resultado
                Estudante(cursor.getString(0), cursor.getString(1)) // Cria o objeto Estudante
            } else {
                null // Retorna nulo caso não encontre
            }
        }

        return estudante // Retorna o estudante encontrado ou nulo
    }

    // Busca todos os estudantes cadastrados
    fun getAllEstudantes(): List<Estudante> {
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO, // Coluna do prontuário
            DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_NOME // Coluna do nome
        )

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_ESTUDANTE_NAME, // Tabela "estudante"
            columns, // Colunas a serem retornadas
            null, // Sem condição de filtro
            null, // Sem argumentos para o filtro
            null, // Sem agrupamento
            null, // Sem filtro por grupos
            null // Sem ordenação
        )

        val estudantes = mutableListOf<Estudante>() // Lista para armazenar os estudantes

        cursor.use {
            while (it.moveToNext()) { // Itera pelos resultados
                estudantes.add(
                    Estudante(prontuario = it.getString(0), nome = it.getString(1)) // Adiciona o estudante na lista
                )
            }
        }

        return estudantes // Retorna a lista de estudantes
    }
}