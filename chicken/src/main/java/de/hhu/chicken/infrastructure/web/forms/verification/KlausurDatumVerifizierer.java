package de.hhu.chicken.infrastructure.web.forms.verification;

import de.hhu.chicken.infrastructure.web.forms.verification.stereotypes.IsValidDatum;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

public class KlausurDatumVerifizierer implements ConstraintValidator<IsValidDatum, LocalDate> {

  @Value("${praktikumszeiten.start.datum}")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate startDatum;

  @Value("${praktikumszeiten.ende.datum}")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate endeDatum;

  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    return value != null && !value.isBefore(startDatum) && !value.isAfter(endeDatum);
  }

  void setStartDatum(LocalDate startDatum) {
    this.startDatum = startDatum;
  }

  void setEndeDatum(LocalDate endeDatum) {
    this.endeDatum = endeDatum;
  }
}

