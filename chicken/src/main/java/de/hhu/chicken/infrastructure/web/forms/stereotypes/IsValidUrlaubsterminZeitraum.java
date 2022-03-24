package de.hhu.chicken.infrastructure.web.forms.stereotypes;

import de.hhu.chicken.infrastructure.web.forms.verification.UrlaubsterminZeitraumVerifizierer;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UrlaubsterminZeitraumVerifizierer.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidUrlaubsterminZeitraum {

  String message() default "Die Uhrzeit ist ungültig!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
