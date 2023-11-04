package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("medicos/cadastrarMedico/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhadosMedico(medico));
        // the POST method needs to return code 201 (created), header location with uri and in the response body it needs to return the thing created
    }

//    @GetMapping("/listarMedicos")
//    public List<DadosListagemMedico> listar() {
//        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
//    }

    @GetMapping("/listarMedicos")
    // // If there are parameters directly in the url, the configuration below in PageableDefault is overwritten.
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable paginacao) {
        // sort ascending (default) -> http://localhost:8080/medicos/listarMedicos?sort=nome or http://localhost:8080/medicos/listarMedicos?sort=crm
        // sort descending -> http://localhost:8080/medicos/listarMedicos?sort=email,desc
        // pagination -> http://localhost:8080/medicos/listarMedicos?size=10&page=2 or http://localhost:8080/medicos/listarMedicos?size=20&page=0
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/detalharMedico/{id}")
    public ResponseEntity detalhar(@PathVariable long id) {
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhadosMedico(medico));
    }

    @PutMapping("/atualizarMedico")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhadosMedico(medico));
        // create another DTO because it is not recommended to return the JPA entity in the controller
    }

    @DeleteMapping("/deletarMedico/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable long id) { // this way I inform to spring that this paramter is the same in path above
        repository.getReferenceById(id).desativarMedico(); // logic delete

        return ResponseEntity.noContent().build(); // more appropriate is request 204, as the requisition has been processed and has no return arguments
        // noContent() creates an object and we call build() to build the ResponseEntity object.
    }

}
