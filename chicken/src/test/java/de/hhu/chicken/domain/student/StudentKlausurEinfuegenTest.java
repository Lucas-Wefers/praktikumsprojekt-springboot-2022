package de.hhu.chicken.domain.student;

import static de.hhu.chicken.domain.student.StudentTemplates.studentMitEinemUrlaubstermin;
import static de.hhu.chicken.domain.student.StudentTemplates.studentMitMehrerenUrlaubsterminen;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentKlausurEinfuegenTest {

  @Test
  @DisplayName("Klausur, welche nicht im Urlaubszeitraum liegt wird hinzugefuegt"
      + " und der Urlaub wird nicht abgeaendert")
  void test_1() {
    Student student = studentMitMehrerenUrlaubsterminen();

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(13, 0),
        LocalTime.of(13, 30));

    assertThat(student.klausurReferenzen).contains(new KlausurReferenz(1L));
    assertThat(student.getUrlaubstermine()).hasSize(3);
  }

  @Test
  @DisplayName("Eine Klausur kann nicht doppelt eingefügt werden")
  void test_2() {
    Student student = studentMitMehrerenUrlaubsterminen();

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(13, 0),
        LocalTime.of(13, 30));
    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(13, 0),
        LocalTime.of(13, 30));

    assertThat(student.klausurReferenzen).contains(new KlausurReferenz(1L));
    assertThat(student.klausurReferenzen).hasSize(1);
    assertThat(student.getUrlaubstermine()).hasSize(3);
  }

  @Test
  @DisplayName("Urlaub 10:00 - 11:00, Klausur 10:45 - 11:45 -> Neuer Urlaub von 10:00 bis 10:45")
  void test_3() {
    Student student = studentMitEinemUrlaubstermin(10, 0, 11, 0);

    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 2, 15),
        LocalTime.of(10, 45),
        LocalTime.of(11, 45));

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine())
        .contains(new Urlaubstermin(LocalDate.of(2022, 2, 15),
            LocalTime.of(10, 0),
            LocalTime.of(10, 45)));
  }
}
