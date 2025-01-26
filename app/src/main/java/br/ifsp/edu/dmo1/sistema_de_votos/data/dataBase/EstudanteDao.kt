package br.ifsp.edu.dmo1.sistema_de_votos

import android.content.ContentValues

class EstudanteDao(private val dbHelper: DatabaseHelper) {
    fun inserirEstudante(estudante: Estudante) : Long{
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO, estudante.prontuario)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_NOME, estudante.nome)
        }

        return db.insert(DatabaseHelper.DATABASE_KEYS.TABLE_ESTUDANTE_NAME, null, values)
    }

    fun getByProntuario(prontuario: String): Estudante?{
        val estudante: Estudante?
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO,
            DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_NOME
        )

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO} = ?"
        val whereArgs = arrayOf(prontuario)

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_ESTUDANTE_NAME,
            columns,
            where,
            whereArgs,
            null,
            null,
            null
        )

        cursor.use {
            estudante = if(cursor.moveToNext()){
                Estudante(cursor.getString(0), cursor.getString(1))
            }else{
                null
            }
        }

        return estudante
    }

    fun getAllEstudantes() : List<Estudante>{
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO,
            DatabaseHelper.DATABASE_KEYS.COLUMN_ESTUDANTE_NOME
        )

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_ESTUDANTE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        val estudantes = mutableListOf<Estudante>()

        cursor.use {
            while (it.moveToNext()){
                estudantes.add(
                    Estudante(prontuario = it.getString(0), nome = it.getString(1))
                )
            }
        }

        return estudantes
    }

}