package br.ifsp.edu.dmo1.sistema_de_votos.model

import java.util.UUID

// Classe que representa o modelo de um Voto
class Voto(
    val codigo: String, // Código único do voto
    val valor: Int // Valor associado ao voto (ex: 1 para positivo, 0 para negativo)
) {
    companion object {
        // Função para gerar um código único para o voto utilizando UUID
        fun gerarCodigoDeVoto(): String {
            // Gera um UUID, remove os hífens e pega os primeiros 10 caracteres como código do voto
            return UUID.randomUUID().toString().replace("-", "").take(10)
        }
    }
}
