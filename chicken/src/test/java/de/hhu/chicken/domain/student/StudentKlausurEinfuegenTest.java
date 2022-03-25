package de.hhu.chicken.domain.student;

import static de.hhu.chicken.domain.student.StudentTemplates.studentMitEinemUrlaubstermin;
import static de.hhu.chicken.domain.student.StudentTemplates.studentMitMehrerenUrlaubsterminen;
import static de.hhu.chicken.domain.student.StudentTemplates.urlaubsterminTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentKlausurEinfuegenTest {

  @Test
  @DisplayName("Klausur wird beim Studenten hinzugefuegt")
  void test_1() {
    Student student = studentMitMehrerenUrlaubsterminen();

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(13, 0),
        LocalTime.of(13, 30));

    assertThat(student.getKlausurReferenzen()).contains(1L);
  }

  @Test
  @DisplayName("Bei einer Klausur die vor dem Urlaubszeitraum liegt bleibt der Urlaub unveraendert")
  void test_2() {
    Student student = studentMitEinemUrlaubstermin(12, 30, 13, 30);

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 0),
        LocalTime.of(10, 30));

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine())
        .contains(urlaubsterminTemplate(12, 30, 13, 30));
  }

  @Test
  @DisplayName("Bei einer Klausur die nach dem Urlaubszeitraum liegt"
      + " bleibt der Urlaub unveraendert")
  void test_3() {
    Student student = studentMitEinemUrlaubstermin(9, 30, 10, 30);

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(13, 0),
        LocalTime.of(13, 30));

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine())
        .contains(urlaubsterminTemplate(9, 30, 10, 30));
  }

  @Test
  @DisplayName("Eine Klausur kann nicht doppelt eingefuegt werden")
  void test_4() {
    Student student = studentMitMehrerenUrlaubsterminen();

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(13, 0),
        LocalTime.of(13, 30));
    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(13, 0),
        LocalTime.of(13, 30));

    assertThat(student.getKlausurReferenzen()).hasSize(1);
    assertThat(student.getUrlaubstermine()).hasSize(3);
  }

  @Test
  @DisplayName("Urlaub 10:00 - 11:00, Klausur 10:45 - 11:45 -> Neuer Urlaub von 10:00 bis 10:45")
  void test_5() {
    Student student = studentMitEinemUrlaubstermin(10, 0, 11, 0);

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(10, 45),
        LocalTime.of(11, 45));

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine())
        .contains(urlaubsterminTemplate(10, 0, 10, 45));
  }

  @Test
  @DisplayName(" Urlaub 10:00 - 11:00, Klausur 9:45 - 10:45 -> Neuer Urlaub von 10:45 bis 11:00")
  void test_6() {
    Student student = studentMitEinemUrlaubstermin(10, 0, 11, 0);

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 45),
        LocalTime.of(10, 45));

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine())
        .contains(urlaubsterminTemplate(10, 45, 11, 0));
  }

  @Test
  @DisplayName(" Urlaub 9:30 - 13:30, Klausur 10:45 - 12:45 -> Neue Urlaube von 9:30 bis 10:45 "
      + "und von 12:45 bis 13:30")
  void test_7() {
    Student student = studentMitEinemUrlaubstermin(9, 30, 13, 30);

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(10, 45),
        LocalTime.of(12, 45));

    assertThat(student.getUrlaubstermine()).hasSize(2);
    assertThat(student.getUrlaubstermine())
        .contains(urlaubsterminTemplate(9, 30, 10, 45));
    assertThat(student.getUrlaubstermine())
        .contains(urlaubsterminTemplate(12, 45, 13, 30));
  }
}
