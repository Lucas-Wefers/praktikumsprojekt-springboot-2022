package de.hhu.chicken.infrastructure.persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.infrastructure.persistence.dao.StudentDao;
import de.hhu.chicken.infrastructure.persistence.dto.StudentDto;
import de.hhu.chicken.service.repositories.StudentRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentRepositoryTest {

  @Test
  @DisplayName("Beim Speichern eines Studenten wird die save Methode im Dao aufgerufen")
  void test_1() {
    String handle = "jensbendisposto";
    Student student = new Student(handle);
    StudentDto studentDto = new StudentDto(handle, List.of(), List.of());
    StudentDao studentDao = mock(StudentDao.class);
    StudentRepository repo = new StudentRepositoryImpl(studentDao);

    repo.studentSpeichern(student);

    verify(studentDao).save(studentDto);
  }
}
