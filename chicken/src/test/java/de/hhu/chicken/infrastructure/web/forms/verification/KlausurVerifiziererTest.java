package de.hhu.chicken.infrastructure.web.forms.verification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KlausurVerifiziererTest {

  @Test
  @DisplayName("Die Veranstaltung mit id 1 existiert nicht")
  void test_1() {
    Long veranstaltungsId = 1L;
    KlausurVerifizierer verifizierer = new KlausurVerifizierer();

    boolean valid = verifizierer.isValid(veranstaltungsId, mock(ConstraintValidatorContext.class));

    assertThat(valid).isFalse();
  }

  @Test
  @DisplayName("Die Veranstaltung mit id 1234 existiert")
  void test_2() {
    Long veranstaltungsId = 1234L;
    KlausurVerifizierer verifizierer = new KlausurVerifizierer();

    boolean valid = verifizierer.isValid(veranstaltungsId, mock(ConstraintValidatorContext.class));

    assertThat(valid).isTrue();
  }
}