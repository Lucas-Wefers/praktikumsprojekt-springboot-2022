package de.hhu.chicken.domain.student;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {

  @Test
  @DisplayName("In einem Urlaubsblock sind 4h valide")
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
}
