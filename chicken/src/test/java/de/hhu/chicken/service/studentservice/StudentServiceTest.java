package de.hhu.chicken.service.studentservice;

import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur;
import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur2;
import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.domain.student.Urlaubstermin;
import de.hhu.chicken.service.repositories.KlausurRepository;
import de.hhu.chicken.service.repositories.StudentRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentServiceTest {

  private final StudentRepository studentRepository = mock(StudentRepository.class);
  private final KlausurRepository klausurRepository = mock(KlausurRepository.class);
  private final StudentService studentService = new StudentService(studentRepository, klausurRepository);
  private final String handle = "jens";

  private void assertThatUrlaubstermineSindGleich(Urlaubstermin urlaubstermin, int klausurTag, int hVon,
                                                  int minVon, int hBis, int minBis) {
    assertThat(urlaubstermin.getDatum()).isEqualTo(LocalDate.of(2022, 3, klausurTag));
    assertThat(urlaubstermin.getVon()).isEqualTo(LocalTime.of(hVon, minVon));
    assertThat(urlaubstermin.getBis()).isEqualTo(LocalTime.of(hBis, minBis));
  }
  private void fuegeKlausurHinzu(Student student, Klausur klausur) {
    student.fuegeKlausurHinzu(klausur.getId(),
        klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
  }
  private void urlaubsterminAnmelden(int klausurTag, int hVon, int minVon, int hBis, int minBis) {
    studentService.urlaubsterminAnmelden(handle,
        LocalDate.of(2022, 3, klausurTag),
        LocalTime.of(hVon, minVon),
        LocalTime.of(hBis, minBis));
  }

  @Test
  @DisplayName("Beim Anmelden zu einer Klausur eines nicht vorhandenen Studenten, "
      + "wird dieser angelegt und gespeichert")
  void test_1() {
    when(studentRepository.findStudentByHandle(handle)).thenReturn(null);
    when(klausurRepository.findKlausurById(1L)).thenReturn(beispielklausur());

    studentService.klausurAnmelden(handle, 1L);

    verify(studentRepository).studentSpeichern(new Student(handle));
  }

  @Test
  @DisplayName("Beim Anmelden zu einer Klausur eines vorhandenen Studenten, "
      + "wird dieser zu der Klausur angemeldet und gespeichert")
  void test_2() {
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
    Student student = new Student(handle);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);

    urlaubsterminAnmelden(15, 9, 30, 13, 30);

    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThatUrlaubstermineSindGleich(urlaubstermin, 15, 9, 30, 13, 30);
  }

  @Test
  @DisplayName("Ein Urlaub der innerhalb eines Klausurfreistellungszeitraums angelegt wird, "
      + "wird ignoriert")
  void test_4() {
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    fuegeKlausurHinzu(student, klausur);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);

    urlaubsterminAnmelden(17, 10, 45, 11, 15);

    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).isEmpty();
  }

  @Test
  @DisplayName("Ein Urlaub der den Klausurfreistellungszeitraum rechts schneidet, wird angepasst "
      + "und angelegt")
  void test_5() {
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    fuegeKlausurHinzu(student, klausur);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);

    urlaubsterminAnmelden(17, 11, 0, 13, 30);

    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).hasSize(1);
    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    assertThatUrlaubstermineSindGleich(urlaubstermin, 17, 11, 30, 13, 30);
  }

  @Test
  @DisplayName("Ein Urlaub der den Klausurfreistellungszeitraum links schneidet, wird angepasst "
      + "und angelegt")
  void test_6() {
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    fuegeKlausurHinzu(student, klausur);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);

    urlaubsterminAnmelden(17, 9, 30, 11, 30);

    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).hasSize(1);
    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    assertThatUrlaubstermineSindGleich(urlaubstermin, 17, 9, 30, 10, 0);
  }

  @Test
  @DisplayName("Ein Urlaub der den Klausurfreistellungszeitraum beidseitig schneidet, wird "
      + "angepasst und angelegt")
  void test_7() {
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    fuegeKlausurHinzu(student, klausur);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);

    urlaubsterminAnmelden(17, 9, 30, 13, 30);

    verify(studentRepository).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).hasSize(2);
    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    Urlaubstermin urlaubstermin2 = student.getUrlaubstermine().get(1);
    assertThatUrlaubstermineSindGleich(urlaubstermin, 17, 9, 30, 10, 0);
    assertThatUrlaubstermineSindGleich(urlaubstermin2, 17, 11, 30, 13, 30);
  }

  @Test
  @DisplayName("Bei zwei Klausuren an einem Tag, wird ein ganztaegiger Urlaub entsprechend "
      + "modifiziert")
  void test_8() {
    Student student = new Student(handle);
    Klausur klausur = beispielklausur2();
    Klausur klausur2 = beispielklausur3();
    fuegeKlausurHinzu(student, klausur);
    fuegeKlausurHinzu(student, klausur2);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(klausur);
    when(klausurRepository.findKlausurById(2L)).thenReturn(klausur2);

    urlaubsterminAnmelden(17, 9, 30, 13, 30);

    assertThat(student.getUrlaubstermine()).hasSize(3);
    Urlaubstermin urlaubstermin = student.getUrlaubstermine().get(0);
    Urlaubstermin urlaubstermin2 = student.getUrlaubstermine().get(1);
    Urlaubstermin urlaubstermin3 = student.getUrlaubstermine().get(2);
    assertThatUrlaubstermineSindGleich(urlaubstermin, 17, 9, 30, 10, 0);
    assertThatUrlaubstermineSindGleich(urlaubstermin2, 17, 11, 30, 11, 45);
    assertThatUrlaubstermineSindGleich(urlaubstermin3, 17, 13, 0, 13, 30);
  }

  @Test
  @DisplayName("Beim Anmelden eines Urlaubs eines nicht vorhandenen Studenten, "
      + "wird dieser angelegt und gespeichert")
  void test_9() {
    when(studentRepository.findStudentByHandle(handle)).thenReturn(null);

    urlaubsterminAnmelden(15, 10, 30, 11, 30);

    verify(studentRepository).studentSpeichern(new Student(handle));
  }

  @Test
  @DisplayName("Ein Student wird geladen und eine Klausur wird nach Id geloescht und der Student "
      + "wird wieder gespeichert")
  void test_10() {
    Student student = new Student(handle);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    when(klausurRepository.findKlausurById(1L)).thenReturn(beispielklausur());
    studentService.klausurAnmelden(handle, 1L);

    studentService.klausurStornieren(handle, 1L);

    verify(studentRepository, times(2)).studentSpeichern(student);
    assertThat(student.getKlausurReferenzen()).isEmpty();
  }

  @Test
  @DisplayName("Ein Student wird geladen und ein Urlaub wird geloescht und der Student "
      + "wird wieder gespeichert")
  void test_11() {
    Student student = new Student(handle);
    when(studentRepository.findStudentByHandle(handle)).thenReturn(student);
    urlaubsterminAnmelden(17, 9, 30, 13, 30);

    studentService.urlaubsterminStornieren(handle,
        LocalDate.of(2022, 3, 17),
        LocalTime.of(9, 30),
        LocalTime.of(13, 30));

    verify(studentRepository, times(2)).studentSpeichern(student);
    assertThat(student.getUrlaubstermine()).isEmpty();
  }
}
