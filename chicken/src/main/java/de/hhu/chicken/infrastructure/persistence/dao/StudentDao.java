package de.hhu.chicken.infrastructure.persistence.dao;

import de.hhu.chicken.infrastructure.persistence.dto.StudentDto;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<StudentDto, String> {

}
