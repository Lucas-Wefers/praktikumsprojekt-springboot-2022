package de.hhu.chicken.infrastructure.web.forms.verification.stereotypes;

import de.hhu.chicken.infrastructure.web.forms.verification.IsVielfacheVon15MinutenVerifizierer;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = IsVielfacheVon15MinutenVerifizierer.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsVielfacheVon15Minuten {

  String message() default "Die Uhrzeit muss ein Vielfaches von 15 Minuten sein!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}