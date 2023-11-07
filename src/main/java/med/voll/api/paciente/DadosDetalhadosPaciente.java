package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

public record DadosDetalhadosPaciente(String nome, String cpf, String telefone, String email, Endereco endereco) {
    public DadosDetalhadosPaciente(Paciente paciente) {
        this(paciente.getNome(), paciente.getCpf(), paciente.getTelefone(), paciente.getEmail(), paciente.getEndereco());
    }
}
