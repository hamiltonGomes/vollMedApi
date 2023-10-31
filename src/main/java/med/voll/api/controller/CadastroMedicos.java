package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medicos")
public class CadastroMedicos {

    // This is called dependency injections. It's occur when we need to command Spring to instantiate the attribute in the class.
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional // This annotation allows active transactions with database, since I'm entering values. From spring.
    // This "@Valid" is essencial to execute Bean Validation.
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {

        //Instantiate the object and saving in
        repository.save(new Medico(dados));
    }

}
