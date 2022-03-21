package de.hhu.chicken.domain.student;

import static de.hhu.chicken.domain.student.StudentTemplates.fuegeUrlaubsterminHinzu;
import static de.hhu.chicken.domain.student.StudentTemplates.studentMitMehrerenUrlaubsterminen;
import static de.hhu.chicken.domain.student.StudentTemplates.urlaubsterminTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentBerechneResturlaubTest {

  @Test
  @DisplayName("Resturlaub ohne Urlaube betraegt 240min")
  void test_1() {
    Student student = new Student(14529531L, "jensbendisposto");

    int resturlaub = student.berechneResturlaub();

    assertThat(resturlaub).isEqualTo(240);
  }

  @Test
  @DisplayName("Resturlaub mit einer Urlaubslaenge von 30min betraegt 210min")
  void test_2() {
    Student student = new Student(14529531L, "jensbendisposto");
    Urlaubstermin urlaubstermin = urlaubsterminTemplate(10,30,11,0);
    fuegeUrlaubsterminHinzu(urlaubstermin, student, false);

    int resturlaub = student.berechneResturlaub();

    assertThat(resturlaub).isEqualTo(210);
  }

  @Test
  @DisplayName("Resturlaub mit einer Urlaubslaenge von 30min betraegt 210min")
  void test_3() {
    Student student = studentMitMehrerenUrlaubsterminen();

    int resturlaub = student.berechneResturlaub();

    assertThat(resturlaub).isEqualTo(90);
  }
}
