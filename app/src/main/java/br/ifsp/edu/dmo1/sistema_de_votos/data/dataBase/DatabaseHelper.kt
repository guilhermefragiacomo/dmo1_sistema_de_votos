package br.ifsp.edu.dmo1.sistema_de_votos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_KEYS.DATABASE_NAME, null,
    DATABASE_KEYS.DATABASE_VERSION
) {
    object DATABASE_KEYS{
        const val DATABASE_NAME = "pesquisaOpiniao.db"
        const val DATABASE_VERSION = 2

        const val TABLE_ESTUDANTE_NAME = "estudante"
        const val COLUMN_ESTUDANTE_PRONTUARIO = "prontuario"
        const val COLUMN_ESTUDANTE_NOME = "nome"

        const val TABLE_VOTO_NAME = "voto"
        const val COLUMN_VOTO_CODIGO = "codigo"
        const val COLUMN_VOTO_VALOR= "valor"
    }

    private companion object{
        const val CREATE_ESTUDANTE_TABLE = "CREATE TABLE ${DATABASE_KEYS.TABLE_ESTUDANTE_NAME} (" +
                "${DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO} TEXT PRIMARY KEY NOT NULL, " +
                "${DATABASE_KEYS.COLUMN_ESTUDANTE_NOME} TEXT NOT NULL)"

        const val CREATE_VOTO_TABLE = "CREATE TABLE ${DATABASE_KEYS.TABLE_VOTO_NAME} (" +
                "${DATABASE_KEYS.COLUMN_VOTO_CODIGO} TEXT NOT NULL, " +
                "${DATABASE_KEYS.COLUMN_VOTO_VALOR} INTEGER NOT NULL, " +
                "PRIMARY KEY (${DATABASE_KEYS.COLUMN_VOTO_CODIGO}))"

        const val BUSCA_ESTUDANTE_VOTO = "SELECT ${DATABASE_KEYS.COLUMN_VOTO_CODIGO}, ${DATABASE_KEYS.COLUMN_VOTO_VALOR}" +
                "FROM ${DATABASE_KEYS.TABLE_VOTO_NAME}" +
                "WHERE ${DATABASE_KEYS.COLUMN_VOTO_CODIGO} = ?"

        const val BUSCA_TODOS_VOTOS = "SELECT ${DATABASE_KEYS.COLUMN_VOTO_VALOR}, COUNT(*) AS Quantidade" +
                "FROM ${DATABASE_KEYS.TABLE_VOTO_NAME}" +
                "GROUP BY ${DATABASE_KEYS.COLUMN_VOTO_VALOR}"

        const val DROP_TABLE_ESTUDANTE = "DROP TABLE IF EXISTS ${DATABASE_KEYS.TABLE_ESTUDANTE_NAME}"
        const val DROP_TABLE_VOTO = "DROP TABLE IF EXISTS ${DATABASE_KEYS.TABLE_VOTO_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_ESTUDANTE_TABLE)
        db.execSQL(CREATE_VOTO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_ESTUDANTE)
        db.execSQL(DROP_TABLE_VOTO)
        onCreate(db)
    }
}
