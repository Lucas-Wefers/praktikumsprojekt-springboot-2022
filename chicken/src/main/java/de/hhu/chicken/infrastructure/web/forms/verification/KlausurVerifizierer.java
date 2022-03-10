package de.hhu.chicken.infrastructure.web.forms.verification;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.reactive.function.client.WebClient;

public class KlausurVerifizierer implements ConstraintValidator<IsValidId, Long> {

  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {
    WebClient client = WebClient.create("https://lsf.hhu.de/");
    String lsfSite = client
        .get()
        .uri(
            "qisserver/rds?state=verpublish&status=init&vmfile=no&"
                + "publishid={value}"
                + "&moduleCall=webInfo&publishConfFile=webInfo&publishSubDir=veranstaltung",
            value)
        .retrieve()
        .bodyToMono(String.class)
        .block();
    if (lsfSite == null) {
      return false;
    }
    return lsfSite.contains("Veranstaltungsart");
  }
}
