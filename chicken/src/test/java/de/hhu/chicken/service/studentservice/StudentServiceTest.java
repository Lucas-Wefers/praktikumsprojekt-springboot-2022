package de.hhu.chicken.service.studentservice;

import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur;
import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur2;
import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.domain.student.Urlaubstermin;
import de.hhu.chicken.service.repositories.KlausurRepository;
import de.hhu.chicken.service.repositories.StudentRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.tomcat.jni.Local;
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

  @Test
  @DisplayName("Bei einem Tag ohne Klausuren wird der Urlaub angelegt")
  void test_3() {
    StudentRepository studentRepository = mock(StudentRepository.class);
    KlausurRepository klausurRepository = mock(KlausurRepository.class);
    StudentService studentService = new StudentService(studentRepository, klausurRepository);
    String handle = "jens";
    Student student = new Student(handle);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);

    studentService.urlaubAnmelden(handle, LocalDate.of(2022, 3, 15),
        LocalTime.of(9, 30), LocalTime.of(13, 30));

    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(urlaubstermin.getDatum()).isEqualTo(LocalDate.of(2022, 3, 15));
    assertThat(urlaubstermin.getVon()).isEqualTo(LocalTime.of(9, 30));
    assertThat(urlaubstermin.getBis()).isEqualTo(LocalTime.of(13, 30));
  }

  @Test
  @DisplayName("Ein Urlaub der innerhalb eines Klausurfreistellungszeitraums angelegt wird, "
      + "wird ignoriert")
  void test_4() {
    StudentRepository studentRepository = mock(StudentRepository.class);
    KlausurRepository klausurRepository = mock(KlausurRepository.class);
    StudentService studentService = new StudentService(studentRepository, klausurRepository);
    String handle = "jens";
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    student.fuegeKlausurHinzu(1L, klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);

    studentService.urlaubAnmelden(handle, LocalDate.of(2022, 3, 17),
        LocalTime.of(10, 45), LocalTime.of(11, 15));

    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).isEmpty();
  }

  @Test
  @DisplayName("Ein Urlaub der den Klausurfreistellungszeitraum rechts schneidet, wird angepasst "
      + "und angelegt")
  void test_5() {
    StudentRepository studentRepository = mock(StudentRepository.class);
    KlausurRepository klausurRepository = mock(KlausurRepository.class);
    StudentService studentService = new StudentService(studentRepository, klausurRepository);
    String handle = "jens";
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    student.fuegeKlausurHinzu(1L, klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);

    studentService.urlaubAnmelden(handle, LocalDate.of(2022, 3, 17),
        LocalTime.of(11, 0), LocalTime.of(13, 30));

    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).hasSize(1);
    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    assertThat(urlaubstermin.getDatum()).isEqualTo(LocalDate.of(2022, 3, 17));
    assertThat(urlaubstermin.getVon()).isEqualTo(LocalTime.of(11, 30));
    assertThat(urlaubstermin.getBis()).isEqualTo(LocalTime.of(13, 30));
  }

  @Test
  @DisplayName("Ein Urlaub der den Klausurfreistellungszeitraum links schneidet, wird angepasst "
      + "und angelegt")
  void test_6() {
    StudentRepository studentRepository = mock(StudentRepository.class);
    KlausurRepository klausurRepository = mock(KlausurRepository.class);
    StudentService studentService = new StudentService(studentRepository, klausurRepository);
    String handle = "jens";
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    student.fuegeKlausurHinzu(1L, klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);

    studentService.urlaubAnmelden(handle, LocalDate.of(2022, 3, 17),
        LocalTime.of(9, 30), LocalTime.of(11, 30));

    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).hasSize(1);
    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    assertThat(urlaubstermin.getDatum()).isEqualTo(LocalDate.of(2022, 3, 17));
    assertThat(urlaubstermin.getVon()).isEqualTo(LocalTime.of(9, 30));
    assertThat(urlaubstermin.getBis()).isEqualTo(LocalTime.of(10, 0));
  }

  @Test
  @DisplayName("Ein Urlaub der den Klausurfreistellungszeitraum beidseitig schneidet, wird "
      + "angepasst und angelegt")
  void test_7() {
    StudentRepository studentRepository = mock(StudentRepository.class);
    KlausurRepository klausurRepository = mock(KlausurRepository.class);
    StudentService studentService = new StudentService(studentRepository, klausurRepository);
    String handle = "jens";
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    student.fuegeKlausurHinzu(1L, klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);

    studentService.urlaubAnmelden(handle, LocalDate.of(2022, 3, 17),
        LocalTime.of(9, 30), LocalTime.of(13, 30));

    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).hasSize(2);
    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    Urlaubstermin urlaubstermin2 = student.getUrlaubstermine().get(1);
    assertThat(urlaubstermin.getDatum()).isEqualTo(LocalDate.of(2022, 3, 17));
    assertThat(urlaubstermin.getVon()).isEqualTo(LocalTime.of(9, 30));
    assertThat(urlaubstermin.getBis()).isEqualTo(LocalTime.of(10, 0));
    assertThat(urlaubstermin2.getDatum()).isEqualTo(LocalDate.of(2022, 3, 17));
    assertThat(urlaubstermin2.getVon()).isEqualTo(LocalTime.of(11, 30));
    assertThat(urlaubstermin2.getBis()).isEqualTo(LocalTime.of(13, 30));
  }

  @Test
  @DisplayName("Bei zwei Klausuren an einem Tag, wird ein ganztaegiger Urlaub entsprechend "
      + "modifiziert")
  void test_8() {
    StudentRepository studentRepository = mock(StudentRepository.class);
    KlausurRepository klausurRepository = mock(KlausurRepository.class);
    StudentService studentService = new StudentService(studentRepository, klausurRepository);
    String handle = "jens";
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    Klausur klausur2 = beispielklausur3();
    student.fuegeKlausurHinzu(1L, klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
    student.fuegeKlausurHinzu(2L, klausur2.getDatum(),
        klausur2.berechneFreistellungsStartzeitpunkt(),
        klausur2.berechneFreistellungsEndzeitpunkt());
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);
    when(klausurRepository.findKlausurById(2L)).thenReturn(klausur2);

    studentService.urlaubAnmelden(handle, LocalDate.of(2022, 3, 17),
        LocalTime.of(9, 30), LocalTime.of(13, 30));

    assertThat(student.getUrlaubstermine()).hasSize(3);
    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    Urlaubstermin urlaubstermin2 = student.getUrlaubstermine().get(1);
    Urlaubstermin urlaubstermin3 = student.getUrlaubstermine().get(2);
    assertThat(urlaubstermin.getDatum()).isEqualTo(LocalDate.of(2022, 3, 17));
    assertThat(urlaubstermin.getVon()).isEqualTo(LocalTime.of(9, 30));
    assertThat(urlaubstermin.getBis()).isEqualTo(LocalTime.of(10, 0));
    assertThat(urlaubstermin2.getDatum()).isEqualTo(LocalDate.of(2022, 3, 17));
    assertThat(urlaubstermin2.getVon()).isEqualTo(LocalTime.of(11, 30));
    assertThat(urlaubstermin2.getBis()).isEqualTo(LocalTime.of(11, 45));
    assertThat(urlaubstermin3.getDatum()).isEqualTo(LocalDate.of(2022, 3, 17));
    assertThat(urlaubstermin3.getVon()).isEqualTo(LocalTime.of(13, 0));
    assertThat(urlaubstermin3.getBis()).isEqualTo(LocalTime.of(13, 30));
  }

  @Test
  @DisplayName("Beim Anmelden eines Urlaubs eines nicht vorhandenen Studenten, "
      + "wird dieser angelegt und gespeichert")
  void test_9() {
    StudentRepository studentRepository = mock(StudentRepository.class);
    KlausurRepository klausurRepository = mock(KlausurRepository.class);
    StudentService studentService = new StudentService(studentRepository, klausurRepository);
    String handle = "jens";
    when(studentRepository.findStudentByHandle(handle)).thenReturn(null);

    studentService.urlaubAnmelden(handle, LocalDate.of(2022, 3, 15),
        LocalTime.of(10, 30), LocalTime.of(11, 30));

    verify(studentRepository).studentSpeichern(new Student(handle));
  }
}
