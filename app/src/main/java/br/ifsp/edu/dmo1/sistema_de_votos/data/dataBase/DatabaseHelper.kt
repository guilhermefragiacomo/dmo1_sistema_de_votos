package br.ifsp.edu.dmo1.sistema_de_votos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Classe para gerenciar o banco de dados
class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_KEYS.DATABASE_NAME, // Nome do banco de dados
    null,
    DATABASE_KEYS.DATABASE_VERSION // Versão do banco de dados
) {
    // Objeto contendo constantes relacionadas ao banco de dados
    object DATABASE_KEYS {
        const val DATABASE_NAME = "pesquisaOpiniao.db" // Nome do banco de dados
        const val DATABASE_VERSION = 2 // Versão do banco de dados

        // Constantes da tabela "estudante"
        const val TABLE_ESTUDANTE_NAME = "estudante"
        const val COLUMN_ESTUDANTE_PRONTUARIO = "prontuario"
        const val COLUMN_ESTUDANTE_NOME = "nome"

        // Constantes da tabela "voto"
        const val TABLE_VOTO_NAME = "voto"
        const val COLUMN_VOTO_CODIGO = "codigo"
        const val COLUMN_VOTO_VALOR = "valor"
    }

    // Instruções SQL para criar e manipular tabelas
    private companion object {
        // Criação da tabela "estudante"
        const val CREATE_ESTUDANTE_TABLE = "CREATE TABLE ${DATABASE_KEYS.TABLE_ESTUDANTE_NAME} (" +
                "${DATABASE_KEYS.COLUMN_ESTUDANTE_PRONTUARIO} TEXT PRIMARY KEY NOT NULL, " +
                "${DATABASE_KEYS.COLUMN_ESTUDANTE_NOME} TEXT NOT NULL)"

        // Criação da tabela "voto"
        const val CREATE_VOTO_TABLE = "CREATE TABLE ${DATABASE_KEYS.TABLE_VOTO_NAME} (" +
                "${DATABASE_KEYS.COLUMN_VOTO_CODIGO} TEXT NOT NULL, " +
                "${DATABASE_KEYS.COLUMN_VOTO_VALOR} INTEGER NOT NULL, " +
                "PRIMARY KEY (${DATABASE_KEYS.COLUMN_VOTO_CODIGO}))"

        /*
        // Consulta para buscar um voto específico pelo código
        const val BUSCA_ESTUDANTE_VOTO = "SELECT ${DATABASE_KEYS.COLUMN_VOTO_CODIGO}, ${DATABASE_KEYS.COLUMN_VOTO_VALOR}" +
                "FROM ${DATABASE_KEYS.TABLE_VOTO_NAME}" +
                "WHERE ${DATABASE_KEYS.COLUMN_VOTO_CODIGO} = ?"

        // Consulta para buscar todos os votos agrupados por valor
        const val BUSCA_TODOS_VOTOS = "SELECT ${DATABASE_KEYS.COLUMN_VOTO_VALOR}, COUNT(*) AS Quantidade" +
                "FROM ${DATABASE_KEYS.TABLE_VOTO_NAME}" +
                "GROUP BY ${DATABASE_KEYS.COLUMN_VOTO_VALOR}"
         */

        // Comandos para excluir as tabelas existentes
        const val DROP_TABLE_ESTUDANTE = "DROP TABLE IF EXISTS ${DATABASE_KEYS.TABLE_ESTUDANTE_NAME}"
        const val DROP_TABLE_VOTO = "DROP TABLE IF EXISTS ${DATABASE_KEYS.TABLE_VOTO_NAME}"
    }

    // Método chamado ao criar o banco de dados
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_ESTUDANTE_TABLE) // Cria a tabela "estudante"
        db.execSQL(CREATE_VOTO_TABLE) // Cria a tabela "voto"
    }

    // Método chamado ao atualizar a versão do banco de dados
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_ESTUDANTE) // Remove a tabela "estudante"
        db.execSQL(DROP_TABLE_VOTO) // Remove a tabela "voto"
        onCreate(db) // Recria as tabelas
    }
}
