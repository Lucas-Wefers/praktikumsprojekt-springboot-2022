package de.hhu.chicken.infrastructure.web.forms.verification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalTime;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IsVielfacheVon15MinutenVerifiziererTest {

  static IsVielfacheVon15MinutenVerifizierer verifizierer = new IsVielfacheVon15MinutenVerifizierer();

  @Test
  @DisplayName("9:30 ist eine gueltige Uhrzeit")
  void test_1() {
    boolean isValid = verifizierer.isValid(LocalTime.of(9, 30),
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }

  @Test
  @DisplayName("9:37 ist keine gueltige Uhrzeit")
  void test_2() {
    boolean isValid = verifizierer.isValid(LocalTime.of(9, 37),
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }
}
