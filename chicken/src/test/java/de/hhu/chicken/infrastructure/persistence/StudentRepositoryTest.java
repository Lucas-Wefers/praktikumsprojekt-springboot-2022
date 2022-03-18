package de.hhu.chicken.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.infrastructure.persistence.dao.StudentDao;
import de.hhu.chicken.infrastructure.persistence.dto.StudentDto;
import de.hhu.chicken.service.repositories.StudentRepository;
import java.util.List;
import java.util.Optional;
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

  @Test
  @DisplayName("Beim Suchen eines Studenten mit nicht vorhandenem Handle wird null "
      + "zurueckgegeben")
  void test_2() {
    String handle = "jensbendisposto";
    StudentDao studentDao = mock(StudentDao.class);
    StudentRepository repo = new StudentRepositoryImpl(studentDao);

    Student student = repo.findStudentByHandle(handle);

    verify(studentDao).findById(handle);
    assertThat(student).isNull();
  }

  @Test
  @DisplayName("Beim Suchen eines Studenten mit vorhandenem Handle wird das Dao aufgerufen und "
      + "dieser zurueckgegeben")
  void test_3() {
    String handle = "jensbendisposto";
    Student student = new Student(handle);
    StudentDto studentDto = new StudentDto(handle, List.of(), List.of());
    StudentDao studentDao = mock(StudentDao.class);
    StudentRepository repo = new StudentRepositoryImpl(studentDao);
    when(studentDao.findById(handle)).thenReturn(Optional.of(studentDto));

    Student studentByHandle = repo.findStudentByHandle(handle);

    verify(studentDao).findById(handle);
    assertThat(studentByHandle).isEqualTo(student);
  }
}
