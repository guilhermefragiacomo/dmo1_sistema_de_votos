package br.ifsp.edu.dmo1.sistema_de_votos

import android.content.ContentValues
import br.ifsp.edu.dmo1.sistema_de_votos.model.Voto

// Classe DAO para manipular os dados da tabela "voto"
class VotoDao(private val dbHelper: DatabaseHelper) {

    // Insere um novo voto na tabela
    fun inserirVoto(voto: Voto): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO, voto.codigo) // Define o código do voto
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_VALOR, voto.valor) // Define o valor do voto
        }

        return db.insert(DatabaseHelper.DATABASE_KEYS.TABLE_VOTO_NAME, null, values) // Insere o registro e retorna o ID
    }

    // Busca todos os votos cadastrados
    fun getAllVotos(): List<Voto> {
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_VALOR, // Coluna do valor do voto
            "COUNT(*) AS Quantidade" // Contagem de votos
        )

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_VOTO_NAME, // Tabela "voto"
            columns, // Colunas a serem retornadas
            null, // Sem condição de filtro
            null, // Sem argumentos para o filtro
            DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_VALOR, // Sem agrupamento
            null, // Sem filtro por grupos
            null // Sem ordenação
        )

        val votos = mutableListOf<Voto>() // Lista para armazenar os votos

        cursor.use {
            while (it.moveToNext()) { // Itera pelos resultados
                votos.add(
                    Voto(codigo = it.getString(0), valor = it.getInt(1)) // Adiciona o voto na lista
                )
            }
        }

        return votos // Retorna a lista de votos
    }

    // Busca um voto pelo código
    fun getVotoByCodigo(codigo: String): Voto? {
        val voto: Voto?
        val db = dbHelper.readableDatabase

        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO, // Coluna do código do voto
            DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_VALOR // Coluna do valor do voto
        )

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO} = ?" // Condição de busca
        val whereArgs = arrayOf(codigo) // Argumento da condição

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_VOTO_NAME, // Tabela "voto"
            columns, // Colunas a serem retornadas
            where, // Condição de filtro
            whereArgs, // Valores para a condição
            null, // Sem agrupamento
            null, // Sem filtro por grupos
            null // Sem ordenação
        )

        cursor.use {
            voto = if (cursor.moveToNext()) { // Verifica se encontrou um resultado
                Voto(cursor.getString(0), cursor.getInt(1)) // Cria o objeto Voto
            } else {
                null // Retorna nulo caso não encontre
            }
        }

        return voto // Retorna o voto encontrado ou nulo
    }
}
