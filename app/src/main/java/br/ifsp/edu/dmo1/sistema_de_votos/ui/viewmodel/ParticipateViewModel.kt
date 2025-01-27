import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.ifsp.edu.dmo1.sistema_de_votos.EstudanteRepository
import br.ifsp.edu.dmo1.sistema_de_votos.VotoRepository
import br.ifsp.edu.dmo1.sistema_de_votos.model.Estudante
import br.ifsp.edu.dmo1.sistema_de_votos.model.Voto

// ViewModel que gerencia os dados relacionados a Estudantes e Votos
class ParticipateViewModel(application: Application) : AndroidViewModel(application) {

    // Repositórios responsáveis pela interação com os dados no banco
    private lateinit var estudante_repository: EstudanteRepository
    private lateinit var voto_repository: VotoRepository

    // LiveData para armazenar e observar as listas de Estudantes e Votos
    private val _estudante = MutableLiveData<List<Estudante>>()
    private val _voto = MutableLiveData<List<Voto>>()

    // Acessores públicos para os LiveData (para observação pela UI)
    val estudante: LiveData<List<Estudante>> get() = _estudante
    val voto: LiveData<List<Voto>> get() = _voto

    // Função privada que carrega todos os Estudantes e Votos
    private fun load() {
        _estudante.value = estudante_repository.getAllEstudantes()  // Atualiza a lista de Estudantes
        _voto.value = voto_repository.getAllVotos()  // Atualiza a lista de Votos
    }

    // Inicializa os repositórios e carrega os dados
    init {
        estudante_repository = EstudanteRepository(application)  // Inicializa o repositório de Estudantes
        voto_repository = VotoRepository(application)  // Inicializa o repositório de Votos
        load()  // Carrega os dados iniciais
    }

    // Função para adicionar um novo Estudante
    fun addEstudante(prontuario: String, nome: String) {
        val estudante = Estudante(prontuario, nome)  // Cria um novo objeto Estudante
        estudante_repository.addEstudante(estudante)  // Adiciona o Estudante ao repositório
        load()  // Atualiza a lista de Estudantes
    }

    // Função para buscar um Estudante pelo prontuário
    fun getByProntuario(prontuario: String): Estudante? {
        return estudante_repository.getByProntuario(prontuario)  // Retorna o Estudante com o prontuário fornecido
    }

    // Função para obter todos os Estudantes
    fun getAllEstudantes(): List<Estudante> {
        return estudante_repository.getAllEstudantes()  // Retorna todos os Estudantes
    }

    // Função para adicionar um Voto
    fun addVoto(id: Int): String {
        val codigo = Voto.gerarCodigoDeVoto()  // Gera um código único para o Voto
        val voto = Voto(codigo, id)  // Cria o objeto Voto com o código gerado e o id da opinião
        voto_repository.addVoto(voto)  // Adiciona o Voto ao repositório
        load()  // Atualiza a lista de Votos
        return codigo  // Retorna o código gerado para o Voto
    }
}
