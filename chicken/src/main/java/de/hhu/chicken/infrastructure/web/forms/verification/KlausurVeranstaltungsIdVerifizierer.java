package de.hhu.chicken.infrastructure.web.forms.verification;

import de.hhu.chicken.infrastructure.web.forms.stereotypes.IsValidId;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class KlausurVeranstaltungsIdVerifizierer implements ConstraintValidator<IsValidId, Long> {

  @Value("${lsf.domain}")
  private String lsfDomain;

  @Value("${lsf.uri}")
  private String lsfUri;

  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {
    WebClient client = WebClient.create(lsfDomain);
    String lsfSite = client
        .get()
        .uri(
            lsfUri,
            value)
        .retrieve()
        .bodyToMono(String.class)
        .block();
    if (lsfSite == null) {
      return false;
    }
    return lsfSite.contains("Veranstaltungsart");
  }

  public void setLsfDomain(String lsfDomain) {
    this.lsfDomain = lsfDomain;
  }

  public void setLsfUri(String lsfUri) {
    this.lsfUri = lsfUri;
  }
}
