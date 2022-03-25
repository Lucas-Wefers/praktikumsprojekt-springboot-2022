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

  private final StudentDao studentDao = mock(StudentDao.class);
  private final StudentRepository repo = new StudentRepositoryImpl(studentDao);
  private static final String handle = "jensbendisposto";

  @Test
  @DisplayName("Beim Speichern eines vorhandenen Studenten wird die save Methode im Dao aufgerufen")
  void test_1() {
    Student student = new Student(14529531L, handle);
    StudentDto studentDto = new StudentDto(14529531L, handle, List.of(), List.of());

    repo.studentSpeichern(student);

    verify(studentDao).save(studentDto);
  }

  @Test
  @DisplayName("Beim Suchen eines Studenten mit nicht vorhandener Github-Id wird null "
      + "zurueckgegeben")
  void test_2() {
    Student student = repo.findStudentByGithubId(14529531L);

    verify(studentDao).findById(14529531L);
    assertThat(student).isNull();
  }

  @Test
  @DisplayName("Beim Suchen eines Studenten mit vorhandener Github-Id wird das Dao aufgerufen und "
      + "dieser zurueckgegeben")
  void test_3() {
    Student student = new Student(14529531L, handle);
    StudentDto studentDto = new StudentDto(14529531L, handle, List.of(), List.of());
    when(studentDao.findById(14529531L)).thenReturn(Optional.of(studentDto));

    Student studentByHandle = repo.findStudentByGithubId(14529531L);

    verify(studentDao).findById(14529531L);
    assertThat(studentByHandle).isEqualTo(student);
  }
}
