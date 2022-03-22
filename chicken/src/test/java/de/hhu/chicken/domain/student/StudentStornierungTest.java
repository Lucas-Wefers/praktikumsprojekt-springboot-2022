package de.hhu.chicken.domain.student;

import static de.hhu.chicken.domain.student.StudentTemplates.studentMitEinemUrlaubstermin;
import static de.hhu.chicken.domain.student.StudentTemplates.studentMitMehrerenUrlaubsterminen;
import static de.hhu.chicken.domain.student.StudentTemplates.urlaubsterminTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import de.hhu.chicken.domain.klausur.Klausur;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentStornierungTest {

  @Test
  @DisplayName("Eine Klausur kann storniert werden")
  void test_1() {
    Student student = studentMitMehrerenUrlaubsterminen();
    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 3, 15),
        LocalTime.of(10, 30),
        LocalTime.of(11, 30));

    student.storniereKlausur(1L);

    assertThat(student.getKlausurReferenzen()).isEmpty();
  }

  @Test
  @DisplayName("Eine Klausur, deren Id nicht vorhanden ist, wird nicht storniert")
  void test_2() {
    Student student = studentMitMehrerenUrlaubsterminen();
    student.fuegeKlausurHinzu(1L,
        LocalDate.of(2022, 3, 15),
        LocalTime.of(10, 30),
        LocalTime.of(11, 30));

    student.storniereKlausur(2L);

    assertThat(student.getKlausurReferenzen()).hasSize(1);
    assertThat(student.getKlausurReferenzen()).contains(1L);
  }

  @Test
  @DisplayName("Ein Urlaub kann storniert werden")
  void test_3() {
    Student student = studentMitEinemUrlaubstermin(10, 30, 11, 30);

    student.storniereUrlaub(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(10, 30),
        LocalTime.of(11, 30));

    assertThat(student.getUrlaubstermine()).isEmpty();
  }

  @Test
  @DisplayName("Ein nicht vorhandener Urlaub kann nicht storniert werden")
  void test_4() {
    Student student = studentMitEinemUrlaubstermin(10, 30, 11, 30);
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(10, 30, 11, 30);

    student.storniereUrlaub(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(10, 15),
        LocalTime.of(11, 30));

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
  }

  @Test
  @DisplayName("Ein Urlaub, der 3 Tage in der Zukunft liegt, ist stornierbar")
  void test_5() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(10, 30, 11, 30);
    LocalDate heute = LocalDate.of(2022, 2, 12);

    assertThat(urlaubstermin.isStornierbar(heute)).isTrue();
  }

  @Test
  @DisplayName("Ein Urlaub, der am gleichen Tag stattfindet, ist nicht stornierbar")
  void test_6() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(10, 30, 11, 30);
    LocalDate heute = LocalDate.of(2022, 2, 15);

    assertThat(urlaubstermin.isStornierbar(heute)).isFalse();
  }

  @Test
  @DisplayName("Ein Urlaub, der 2 Tage in der Vergangenheit liegt, ist nicht stornierbar")
  void test_7() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(10, 30, 11, 30);
    LocalDate heute = LocalDate.of(2022, 2, 17);

    assertThat(urlaubstermin.isStornierbar(heute)).isFalse();
  }
}
