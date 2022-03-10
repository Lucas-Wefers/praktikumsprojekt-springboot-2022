package de.hhu.chicken.infrastructure.persistence.dao;

import de.hhu.chicken.infrastructure.persistence.dto.KlausurDto;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface KlausurDao extends CrudRepository<KlausurDto, UUID> {

}
