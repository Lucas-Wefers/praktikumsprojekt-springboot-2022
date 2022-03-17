package de.hhu.chicken.service.studentservice;

import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.service.repositories.KlausurRepository;
import de.hhu.chicken.service.repositories.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentServiceTest {

  @Test
  @DisplayName("Beim Anmelden zu einer Klausur eines nicht vorhandenen Studenten, "
      + "wird dieser angelegt und gespeichert")
  void test_1() {
    StudentRepository studentRepository = mock(StudentRepository.class);
    KlausurRepository klausurRepository = mock(KlausurRepository.class);
    StudentService studentService = new StudentService(studentRepository, klausurRepository);
    String handle = "jens";
    when(studentRepository.findStudentByHandle(handle)).thenReturn(null);
    when(klausurRepository.findKlausurById(1L)).thenReturn(beispielklausur());

    studentService.klausurAnmelden(handle, 1L);

    verify(studentRepository).studentSpeichern(new Student(handle));
  }

  @Test
  @DisplayName("Beim Anmelden zu einer Klausur eines vorhandenen Studenten, "
      + "wird dieser zu der Klausur angemeldet und gespeichert")
  void test_2() {
    StudentRepository studentRepository = mock(StudentRepository.class);
    KlausurRepository klausurRepository = mock(KlausurRepository.class);
    StudentService studentService = new StudentService(studentRepository, klausurRepository);
    String handle = "jens";
    Student student = new Student(handle);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(beispielklausur());

    studentService.klausurAnmelden(handle, 1L);

    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getKlausurReferenzen()).contains(1L);
  }

}
