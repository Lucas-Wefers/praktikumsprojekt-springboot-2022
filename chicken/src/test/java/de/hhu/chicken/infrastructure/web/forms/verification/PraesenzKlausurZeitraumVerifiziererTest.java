package de.hhu.chicken.infrastructure.web.forms.verification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalTime;
import java.util.List;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PraesenzKlausurZeitraumVerifiziererTest {

  private static final PraesenzKlausurZeitraumVerifizierer
      verifizierer = new PraesenzKlausurZeitraumVerifizierer();

  @BeforeAll
  static void setup() {
    verifizierer.setStart(LocalTime.of(9, 30));
    verifizierer.setEnde(LocalTime.of(13, 30));
  }

  @Test
  @DisplayName("Eine Praesenz-Klausur die um 6:30 endet ist ausserhalb des Zeitraums")
  void test_1() {
    LocalTime von = LocalTime.of(5, 30);
    LocalTime bis = LocalTime.of(6, 30);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Eine Praesenz-Klausur die um 16:00 anfaengt ist ausserhalb des Zeitraums")
  void test_2() {
    LocalTime von = LocalTime.of(16, 0);
    LocalTime bis = LocalTime.of(18, 0);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Eine Praesenz-Klausur, bei welcher der Start- nach dem Endzeitpunkt liegt,"
      + " ist nicht valide")
  void test_3() {
    LocalTime von = LocalTime.of(12, 30);
    LocalTime bis = LocalTime.of(11, 30);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Eine Praesenz-Klausur von 11:30 bis 12:30 ist valide")
  void test_4() {
    LocalTime von = LocalTime.of(11, 30);
    LocalTime bis = LocalTime.of(12, 30);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }

  @Test
  @DisplayName("Eine Praesenz-Klausur, die um 15:15 beginnt, ist valide")
  void test_5() {
    LocalTime von = LocalTime.of(15, 15);
    LocalTime bis = LocalTime.of(15, 30);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }

  @Test
  @DisplayName("Eine Praesenz-Klausur, die um 7:45 endet ist valide")
  void test_6() {
    LocalTime von = LocalTime.of(7, 15);
    LocalTime bis = LocalTime.of(7, 45);
    List<LocalTime> zeitraum = List.of(von, bis);

    boolean isValid = verifizierer.isValid(zeitraum, mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }
}
