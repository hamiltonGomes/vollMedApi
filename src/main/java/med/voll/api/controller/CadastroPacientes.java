package med.voll.api.controller;

import med.voll.api.paciente.DadosCadastroPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class CadastroPacientes {

    @Autowired
    private PacienteRepository repository;

    public void cadastrar(DadosCadastroPaciente dados) {

        repository.save(new Paciente(dados));
    }

}
