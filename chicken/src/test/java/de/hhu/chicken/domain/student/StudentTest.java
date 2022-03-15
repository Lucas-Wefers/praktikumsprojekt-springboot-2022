package de.hhu.chicken.domain.student;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {

  @Test
  @DisplayName("Ein Urlaubsblock von 4 Stunden wird hinzugefuegt")
  void test_1() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 30),
        LocalTime.of(13, 30));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
  }

  @Test
  @DisplayName("Ein Urlaubsblock von 2 Stunden wird hinzugefuegt")
  void test_2() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 30),
        LocalTime.of(11, 30));

    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
  }

  @Test
  @DisplayName("Ein Urlaubsblock von 3 Stunden wird nicht hinzugefuegt")
  void test_3() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(10, 30),
        LocalTime.of(13, 30));

    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(0);
  }

  @Test
  @DisplayName("Zwei Urlaubstermine am Anfang und Ende, die je eine Stunde dauern, werden "
      + "hinzugefuegt")
  void test_4() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 30),
        LocalTime.of(10, 30));
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(12, 30),
        LocalTime.of(13, 30));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin2.getDatum(),
        urlaubstermin2.getVon(),
        urlaubstermin2.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(2);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
    assertThat(student.getUrlaubstermine().get(1)).isEqualTo(urlaubstermin2);
  }

  @Test
  @DisplayName("Zwei Urlaubstermine am Ende und Anfang (in dieser Reihenfolge), "
      + "die je eine Stunde dauern, werden hinzugefuegt")
  void test_5() {
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 30),
        LocalTime.of(10, 30));
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(12, 30),
        LocalTime.of(13, 30));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin2.getDatum(),
        urlaubstermin2.getVon(),
        urlaubstermin2.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(2);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
    assertThat(student.getUrlaubstermine().get(1)).isEqualTo(urlaubstermin2);
  }

  @Test
  @DisplayName("Am Anfang 2h Urlaubsblock, am Ende 1h Urlaubsblock, wird nur der 1. hinzugefuegt")
  void test_6() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 30),
        LocalTime.of(11, 30));
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(12, 30),
        LocalTime.of(13, 30));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin2.getDatum(),
        urlaubstermin2.getVon(),
        urlaubstermin2.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
  }

  @Test
  @DisplayName("Am Anfang 1h Urlaubsblock, am Ende 2h Urlaubsblock, wird nur der 1. hinzugefuegt")
  void test_7() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 30),
        LocalTime.of(10, 30));
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(11, 30),
        LocalTime.of(13, 30));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin2.getDatum(),
        urlaubstermin2.getVon(),
        urlaubstermin2.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
  }

  @Test
  @DisplayName("Am Anfang 1h Urlaubsblock, mittendrin 1h Urlaubsblock, wird nur der 1. hinzugefuegt")
  void test_8() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 30),
        LocalTime.of(10, 30));
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(11, 30),
        LocalTime.of(12, 30));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin2.getDatum(),
        urlaubstermin2.getVon(),
        urlaubstermin2.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
  }

  @Test
  @DisplayName("Mittendrin 1h Urlaubsblock, am Ende 2h Urlaubsblock, wird nur der 1. hinzugefuegt")
  void test_9() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(10, 30),
        LocalTime.of(11, 30));
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(11, 30),
        LocalTime.of(13, 30));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin2.getDatum(),
        urlaubstermin2.getVon(),
        urlaubstermin2.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
  }

  @Test
  @DisplayName("Bei zwei 1h Urlaubsbloecken mittendrin, wird nur der 1. hinzugefuegt")
  void test_10() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(10, 30),
        LocalTime.of(11, 30));
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(12, 0),
        LocalTime.of(13, 0));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin2.getDatum(),
        urlaubstermin2.getVon(),
        urlaubstermin2.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
  }

  @Test
  @DisplayName("Bei drei 30min Urlaubsbloecken am Anfang und Ende und einem mittendrin, werden nur"
      + " 2 hinzugefuegt")
  void test_11() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 30),
        LocalTime.of(10, 0));
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(13, 0),
        LocalTime.of(13, 30));
    Urlaubstermin urlaubstermin3 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(11, 0),
        LocalTime.of(11, 30));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        false);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin2.getDatum(),
        urlaubstermin2.getVon(),
        urlaubstermin2.getBis(),
        false);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin3.getDatum(),
        urlaubstermin3.getVon(),
        urlaubstermin3.getBis(),
        false);

    assertThat(student.getUrlaubstermine()).hasSize(2);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
    assertThat(student.getUrlaubstermine().get(1)).isEqualTo(urlaubstermin2);
  }

  @Test
  @DisplayName("Bei drei 30min Urlaubsbloecken mittendrin, werden an einem Klausurtag alle " +
      "hinzugefuegt")
  void test_12() {
    Urlaubstermin urlaubstermin = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(10, 30),
        LocalTime.of(11, 0));
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(12, 0),
        LocalTime.of(12, 30));
    Urlaubstermin urlaubstermin3 = new Urlaubstermin(
        LocalDate.of(2022, 2, 15),
        LocalTime.of(9, 0),
        LocalTime.of(9, 30));
    Student student = new Student("Jens");

    student.fuegeUrlaubsterminHinzu(
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        true);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin2.getDatum(),
        urlaubstermin2.getVon(),
        urlaubstermin2.getBis(),
        true);
    student.fuegeUrlaubsterminHinzu(
        urlaubstermin3.getDatum(),
        urlaubstermin3.getVon(),
        urlaubstermin3.getBis(),
        true);

    assertThat(student.getUrlaubstermine()).hasSize(3);
    assertThat(student.getUrlaubstermine().get(0)).isEqualTo(urlaubstermin);
    assertThat(student.getUrlaubstermine().get(1)).isEqualTo(urlaubstermin2);
    assertThat(student.getUrlaubstermine().get(2)).isEqualTo(urlaubstermin3);
  }
}
