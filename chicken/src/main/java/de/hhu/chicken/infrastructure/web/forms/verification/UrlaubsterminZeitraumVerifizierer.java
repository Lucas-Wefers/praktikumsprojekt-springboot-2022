package de.hhu.chicken.infrastructure.web.forms.verification;

import de.hhu.chicken.infrastructure.web.forms.verification.stereotypes.IsValidUrlaubsterminZeitraum;
import java.time.LocalTime;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlaubsterminZeitraumVerifizierer implements
    ConstraintValidator<IsValidUrlaubsterminZeitraum, List<LocalTime>> {

  private final LocalTime start = LocalTime.of(9, 30);
  private final LocalTime ende = LocalTime.of(13, 30);

  @Override
  public boolean isValid(List<LocalTime> zeitraum, ConstraintValidatorContext context) {

    LocalTime von = zeitraum.get(0);
    LocalTime bis = zeitraum.get(1);

    return !von.isAfter(bis)
        && !von.isBefore(start)
        && !bis.isAfter(ende);
  }
}
