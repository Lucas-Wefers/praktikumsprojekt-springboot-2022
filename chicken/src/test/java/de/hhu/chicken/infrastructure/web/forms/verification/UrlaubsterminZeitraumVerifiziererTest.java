package de.hhu.chicken.infrastructure.web.forms.verification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalTime;
import java.util.List;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UrlaubsterminZeitraumVerifiziererTest {

  private static final UrlaubsterminZeitraumVerifizierer
      verifizierer = new UrlaubsterminZeitraumVerifizierer();

  @Test
  @DisplayName("Der Startzeitpunkt liegt nach dem Endzeitpunkt und es wird false zurueckgegeben")
  void test_1() {
    LocalTime von = LocalTime.of(11, 30);
    LocalTime bis = LocalTime.of(10, 30);

    boolean isValid = verifizierer.isValid(List.of(von, bis),
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Der Startzeitpunkt liegt vor 9:30 Uhr und es wird false zurueckgegeben")
  void test_2() {
    LocalTime von = LocalTime.of(8, 30);
    LocalTime bis = LocalTime.of(10, 30);

    boolean isValid = verifizierer.isValid(List.of(von, bis),
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Der Endzeitpunkt liegt nach 13:30 Uhr und es wird false zurueckgegeben")
  void test_3() {
    LocalTime von = LocalTime.of(10, 30);
    LocalTime bis = LocalTime.of(14, 30);

    boolean isValid = verifizierer.isValid(List.of(von, bis),
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Beide Zeitpunkte liegen ausserhalb der Praktikumszeit und es wird false " +
      "zurueckgegeben")
  void test_4() {
    LocalTime von = LocalTime.of(3, 30);
    LocalTime bis = LocalTime.of(14, 30);

    boolean isValid = verifizierer.isValid(List.of(von, bis),
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Ein Zeitraum vor der Praktikumszeit ist nicht valide")
  void test_5() {
    LocalTime von = LocalTime.of(3, 30);
    LocalTime bis = LocalTime.of(6, 30);

    boolean isValid = verifizierer.isValid(List.of(von, bis),
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Ein Zeitraum nach der Praktikumszeit ist nicht valide")
  void test_6() {
    LocalTime von = LocalTime.of(13, 30);
    LocalTime bis = LocalTime.of(15, 30);

    boolean isValid = verifizierer.isValid(List.of(von, bis),
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Ein Zeitraum in der Praktikumszeit ist valide")
  void test_7() {
    LocalTime von = LocalTime.of(9, 30);
    LocalTime bis = LocalTime.of(12, 30);

    boolean isValid = verifizierer.isValid(List.of(von, bis),
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }
}
