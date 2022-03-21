package de.hhu.chicken.infrastructure.web.forms.verification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalTime;
import java.util.List;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OnlineKlausurZeitraumVerifiziererTest {

  private static final OnlineKlausurZeitraumVerifizierer
      verifizierer = new OnlineKlausurZeitraumVerifizierer();

  @Test
  @DisplayName("Eine Online-Klausur die um 8:30 endet ist ausserhalb des Zeitraums")
  void test_1() {
    LocalTime von = LocalTime.of(7, 30);
    LocalTime bis = LocalTime.of(8, 30);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Eine Online-Klausur die um 14:30 anfaengt ist ausserhalb des Zeitraums")
  void test_2() {
    LocalTime von = LocalTime.of(14, 30);
    LocalTime bis = LocalTime.of(16, 30);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Eine Online-Klausur, bei welcher der Start- nach dem Endzeitpunkt liegt,"
      + " ist nicht valide")
  void test_3() {
    LocalTime von = LocalTime.of(12, 30);
    LocalTime bis = LocalTime.of(11, 30);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Eine Online-Klausur von 11:30 bis 12:30 ist valide")
  void test_4() {
    LocalTime von = LocalTime.of(11, 30);
    LocalTime bis = LocalTime.of(12, 30);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }

  @Test
  @DisplayName("Eine Online-Klausur, die um 13:45 beginnt, ist valide")
  void test_5() {
    LocalTime von = LocalTime.of(13, 45);
    LocalTime bis = LocalTime.of(15, 30);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }
}
