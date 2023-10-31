package med.voll.api.medico;

import org.springframework.data.jpa.repository.JpaRepository;

// Inserts two types of objects, the first is the entity used in this repository and the other is the type of primary key in the entity.
// This interface inherits from JpaRepository.
public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
