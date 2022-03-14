package de.hhu.chicken.infrastructure.web.forms.verification;

import de.hhu.chicken.infrastructure.web.forms.verification.stereotypes.IsValidOnlineKlausurZeitraum;
import java.time.LocalTime;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlineKlausurZeitraumVerifizierer implements
    ConstraintValidator<IsValidOnlineKlausurZeitraum, List<LocalTime>> {

  private final LocalTime start = LocalTime.of(9, 30);
  private final LocalTime ende = LocalTime.of(13, 30);

  @Override
  public boolean isValid(List<LocalTime> zeitraum, ConstraintValidatorContext context) {
    if (zeitraum == null) {
      return true;
    }

    LocalTime von = zeitraum.get(0);
    LocalTime bis = zeitraum.get(1);

    if (von.isAfter(bis)) {
      return false;
    }

    if (von.minusMinutes(30L).isBefore(von)) {
      von = von.minusMinutes(30L);
    }

    return !bis.isBefore(start) && !von.isAfter(ende);
  }
}
