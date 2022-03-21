package de.hhu.chicken.infrastructure.persistence.dao;

import de.hhu.chicken.infrastructure.persistence.dto.KlausurDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface KlausurDao extends CrudRepository<KlausurDto, Long> {

  List<KlausurDto> findAll();

  Optional<KlausurDto> findById(Long id);
}
