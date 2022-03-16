package de.hhu.chicken.domain.student;

import static de.hhu.chicken.domain.student.StudentTemplates.fuegeUrlaubsterminHinzu;
import static de.hhu.chicken.domain.student.StudentTemplates.urlaubsterminTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentUrlaubsterminEinfuegenTest {

  @Test
  @DisplayName("Ein Urlaubsblock von 4 Stunden wird hinzugefuegt")
  void test_1() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(9, 30, 13, 30);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
  }

  @Test
  @DisplayName("Ein Urlaubsblock von 2 Stunden wird hinzugefuegt")
  void test_2() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(9, 30, 11, 30);

    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
  }

  @Test
  @DisplayName("Ein Urlaubsblock von 3 Stunden wird nicht hinzugefuegt")
  void test_3() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(10, 30, 13, 30);

    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(0);
  }

  @Test
  @DisplayName("Zwei Urlaubstermine am Anfang und Ende, die je eine Stunde dauern, werden "
      + "hinzugefuegt")
  void test_4() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(9, 30, 10, 30);
    Urlaubstermin urlaubstermin2 = urlaubsterminTemplate(12, 30, 13, 30);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(2);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin2);
  }

  @Test
  @DisplayName("Zwei Urlaubstermine am Ende und Anfang (in dieser Reihenfolge), "
      + "die je eine Stunde dauern, werden hinzugefuegt")
  void test_5() {
    Urlaubstermin urlaubstermin2 = urlaubsterminTemplate(9, 30, 10, 30);
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(12, 30, 13, 30);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(2);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin2);
  }

  @Test
  @DisplayName("Am Anfang 2h Urlaubsblock, am Ende 1h Urlaubsblock, wird nur der 1. hinzugefuegt")
  void test_6() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(9, 30, 11, 30);
    Urlaubstermin urlaubstermin2 = urlaubsterminTemplate(12, 30, 13, 30);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
  }

  @Test
  @DisplayName("Am Anfang 1h Urlaubsblock, am Ende 2h Urlaubsblock, wird nur der 1. hinzugefuegt")
  void test_7() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(9, 30, 10, 30);
    Urlaubstermin urlaubstermin2 = urlaubsterminTemplate(11, 30, 13, 30);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
  }

  @Test
  @DisplayName("Am Anfang 1h Urlaubsblock, mittendrin 1h Urlaubsblock, wird nur der 1. hinzugefuegt")
  void test_8() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(9, 30, 10, 30);
    Urlaubstermin urlaubstermin2 = urlaubsterminTemplate(11, 30, 12, 30);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
  }

  @Test
  @DisplayName("Mittendrin 1h Urlaubsblock, am Ende 2h Urlaubsblock, wird nur der 1. hinzugefuegt")
  void test_9() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(10, 30, 11, 30);
    Urlaubstermin urlaubstermin2 = urlaubsterminTemplate(11, 30, 13, 30);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
  }

  @Test
  @DisplayName("Bei zwei 1h Urlaubsbloecken mittendrin, wird nur der 1. hinzugefuegt")
  void test_10() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(10, 30, 11, 30);
    Urlaubstermin urlaubstermin2 = urlaubsterminTemplate(12, 0, 13, 0);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
  }

  @Test
  @DisplayName("Bei drei 30min Urlaubsbloecken am Anfang und Ende und einem mittendrin, werden nur"
      + " 2 hinzugefuegt")
  void test_11() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(9, 30, 10, 0);
    Urlaubstermin urlaubstermin2 = urlaubsterminTemplate(13, 0, 13, 30);
    Urlaubstermin urlaubstermin3 = urlaubsterminTemplate(11, 0, 11, 30);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, false);
    fuegeUrlaubsterminHinzu(urlaubstermin3, student, false);

    assertThat(student.getUrlaubstermine()).hasSize(2);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin2);
  }

  @Test
  @DisplayName("Bei drei 30min Urlaubsbloecken mittendrin, werden an einem Klausurtag alle " +
      "hinzugefuegt")
  void test_12() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(10, 30, 11, 0);
    Urlaubstermin urlaubstermin2 = urlaubsterminTemplate(12, 0, 12, 30);
    Urlaubstermin urlaubstermin3 = urlaubsterminTemplate(9, 0, 9, 30);
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, true);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, true);
    fuegeUrlaubsterminHinzu(urlaubstermin3, student, true);

    assertThat(student.getUrlaubstermine()).hasSize(3);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin2);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin3);
  }

  @Test
  @DisplayName("Bei zwei 4h Urlaubsterminen, wird nur der 1. hinzugefuegt")
  void test_13() {
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(9, 30, 13, 30);
    Urlaubstermin urlaubstermin2 = new Urlaubstermin(
        LocalDate.of(2022, 2, 16),
        LocalTime.of(9, 30),
        LocalTime.of(13, 30));
    Student student = new Student("Jens");

    fuegeUrlaubsterminHinzu(urlaubstermin, student, true);
    fuegeUrlaubsterminHinzu(urlaubstermin2, student, true);

    assertThat(student.getUrlaubstermine()).hasSize(1);
    assertThat(student.getUrlaubstermine()).contains(urlaubstermin);
  }
}
