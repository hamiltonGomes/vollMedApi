package med.voll.api.paciente;

public record DadosListagemPaciente(String nome, String email, String telefone, String cpf) {
    // This way I pass patient values into my DTO, bringing the information I need.
    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf());
    }
}
