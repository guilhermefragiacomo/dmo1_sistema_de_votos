package br.ifsp.edu.dmo1.sistema_de_votos.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.ifsp.edu.dmo1.sistema_de_votos.EstudanteRepository
import br.ifsp.edu.dmo1.sistema_de_votos.VotoRepository
import br.ifsp.edu.dmo1.sistema_de_votos.model.Estudante
import br.ifsp.edu.dmo1.sistema_de_votos.model.Voto

class CheckVoteViewModel(application: Application) : AndroidViewModel(application) {
    // Repositórios responsáveis pela interação com os dados no banco
    private lateinit var voto_repository: VotoRepository

    // LiveData para armazenar e observar as listas de Votos
    private val _voto = MutableLiveData<List<Voto>>()

    // Acessores públicos para os LiveData (para observação pela UI)
    val voto: LiveData<List<Voto>> get() = _voto

    // Função privada que carrega todos os Votos
    private fun load() {
        _voto.value = voto_repository.getAllVotos()  // Atualiza a lista de Votos
    }

    // Inicializa os repositórios e carrega os dados
    init {
        voto_repository = VotoRepository(application)  // Inicializa o repositório de Votos
        load()  // Carrega os dados iniciais
    }

    // Função para buscar um Estudante pelo prontuário
    fun getVoteByCode(code: String): Voto? {
        return voto_repository.getVotoByCodigo(code)  // Retorna o Voto com o Código fornecido
    }
}