package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    // This is called dependency injections. It's occur when we need to command Spring to instantiate the attribute in the class.
    @Autowired
    private MedicoRepository repository;

    @PostMapping("/cadastrarMedico")
    @Transactional
    // This annotation allows active transactions with database, since I'm entering values. From spring. When I want add or update some information in the database.
    // This "@Valid" is essencial to execute Bean Validation.
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {

        //Instantiate the object and saving in
        repository.save(new Medico(dados));
    }

//    @GetMapping("/listarMedicos")
//    public List<DadosListagemMedico> listar() {
//        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
//    }

    @GetMapping("/listarMedicos")
    // // If there are parameters directly in the url, the configuration below in PageableDefault is overwritten.
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable paginacao) {
        // sort ascending (default) -> http://localhost:8080/medicos/listarMedicos?sort=nome or http://localhost:8080/medicos/listarMedicos?sort=crm
        // sort descending -> http://localhost:8080/medicos/listarMedicos?sort=email,desc
        // pagination -> http://localhost:8080/medicos/listarMedicos?size=10&page=2 or
        http:
//localhost:8080/medicos/listarMedicos?size=20&page=0
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping("/atualizarMedico")
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/deletarMedico/{id}")
    @Transactional
    public void deletar(@PathVariable long id) { // this way I inform to spring that this paramter is the same in path above
//        repository.deleteById(id); // really delete from database and system

        repository.getReferenceById(id).desativarMedico(); // logic delete
    }

}
