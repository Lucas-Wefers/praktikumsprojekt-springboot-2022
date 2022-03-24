package de.hhu.chicken.infrastructure.web.forms.verification;

import de.hhu.chicken.infrastructure.web.forms.stereotypes.IsValidDatum;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

public class DatumVerifizierer implements ConstraintValidator<IsValidDatum, LocalDate> {

  @Value("${praktikumszeiten.start.datum}")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDatum;

  @Value("${praktikumszeiten.ende.datum}")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endeDatum;

  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }

    if (value.getDayOfWeek().equals(DayOfWeek.SATURDAY)
        || value.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
      return false;
    }

    return !value.isBefore(startDatum) && !value.isAfter(endeDatum);
  }

  void setStartDatum(LocalDate startDatum) {
    this.startDatum = startDatum;
  }

  void setEndeDatum(LocalDate endeDatum) {
    this.endeDatum = endeDatum;
  }
}

