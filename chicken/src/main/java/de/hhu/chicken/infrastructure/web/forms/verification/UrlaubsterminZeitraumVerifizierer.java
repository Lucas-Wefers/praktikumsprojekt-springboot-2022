package de.hhu.chicken.infrastructure.web.forms.verification;

import de.hhu.chicken.infrastructure.web.forms.stereotypes.IsValidUrlaubsterminZeitraum;
import java.time.LocalTime;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

public class UrlaubsterminZeitraumVerifizierer implements
    ConstraintValidator<IsValidUrlaubsterminZeitraum, List<LocalTime>> {

  @Value("${praktikumszeiten.start.uhrzeit}")
  @DateTimeFormat(pattern = "HH:mm")
  private LocalTime start;

  @Value("${praktikumszeiten.ende.uhrzeit}")
  @DateTimeFormat(pattern = "HH:mm")
  private LocalTime ende;

  @Override
  public boolean isValid(List<LocalTime> zeitraum, ConstraintValidatorContext context) {
    if (zeitraum == null) {
      return true;
    }

    LocalTime von = zeitraum.get(0);
    LocalTime bis = zeitraum.get(1);

    return !von.isAfter(bis)
        && !von.isBefore(start)
        && !bis.isAfter(ende);
  }

  void setStart(LocalTime start) {
    this.start = start;
  }

  void setEnde(LocalTime ende) {
    this.ende = ende;
  }
}
