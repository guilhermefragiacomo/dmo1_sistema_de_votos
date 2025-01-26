package br.ifsp.edu.dmo1.sistema_de_votos.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.ifsp.edu.dmo1.sistema_de_votos.Estudante;
import br.ifsp.edu.dmo1.sistema_de_votos.EstudanteRepository
import br.ifsp.edu.dmo1.sistema_de_votos.Voto
import br.ifsp.edu.dmo1.sistema_de_votos.VotoRepository


class ParticipateViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var estudante_repository: EstudanteRepository;
    private lateinit var voto_repository: VotoRepository;

    private val _estudante = MutableLiveData<List<Estudante>>()
    private val _voto = MutableLiveData<List<Voto>>()
    val estudante: LiveData<List<Estudante>>
        get() = _estudante;
    val voto: LiveData<List<Voto>>
        get() = _voto;

    private fun load() {
        _estudante.value = estudante_repository.getAllEstudantes();
        _voto.value = voto_repository.getAllVotos();
    }

    init {
        estudante_repository = EstudanteRepository(application);
        voto_repository = VotoRepository(application);
        load();
    }

    fun addEstudante(prontuario : String, nome : String) {
        val estudante = Estudante(prontuario, nome);

        estudante_repository.addEstudante(estudante);
        load();

    }

    fun getByProntuario(prontuario : String) : Estudante? {
        return estudante_repository.getByProntuario(prontuario);
    }

    fun getAllEstudantes() : List<Estudante> {
        return estudante_repository.getAllEstudantes();
    }

    fun addVoto(id : Int) : String {
        val codigo = Voto.gerarCodigoDeVoto();

        val voto = Voto(codigo, id);

        voto_repository.addVoto(voto);
        load();

        return codigo;
    }
}