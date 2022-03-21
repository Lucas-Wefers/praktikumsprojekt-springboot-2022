package de.hhu.chicken.infrastructure.web.forms.verification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KlausurVeranstaltungsIdVerifiziererTest {

  private static final KlausurVeranstaltungsIdVerifizierer
      verifizierer = new KlausurVeranstaltungsIdVerifizierer();

  @Test
  @DisplayName("Die Veranstaltung mit veranstaltungsId 1 existiert nicht")
  void test_1() {
    Long veranstaltungsId = 1L;

    boolean isValid = verifizierer.isValid(veranstaltungsId,
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Die Veranstaltung mit veranstaltungsId 1234 existiert")
  void test_2() {
    Long veranstaltungsId = 1234L;

    boolean isValid = verifizierer.isValid(veranstaltungsId,
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }
}