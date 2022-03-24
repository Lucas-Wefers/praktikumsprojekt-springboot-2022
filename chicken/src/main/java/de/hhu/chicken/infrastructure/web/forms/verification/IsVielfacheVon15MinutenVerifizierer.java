package de.hhu.chicken.infrastructure.web.forms.verification;

import de.hhu.chicken.infrastructure.web.forms.stereotypes.IsVielfacheVon15Minuten;
import java.time.LocalTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsVielfacheVon15MinutenVerifizierer implements ConstraintValidator<IsVielfacheVon15Minuten, LocalTime> {

  @Override
  public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
    if(value == null) return false;
    return value.getMinute() % 15 == 0;
  }
}
