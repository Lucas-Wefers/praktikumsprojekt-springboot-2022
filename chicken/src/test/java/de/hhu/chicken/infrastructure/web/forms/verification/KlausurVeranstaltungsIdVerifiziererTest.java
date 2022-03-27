package de.hhu.chicken.infrastructure.web.forms.verification;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KlausurVeranstaltungsIdVerifiziererTest {

  private static final KlausurVeranstaltungsIdVerifizierer
      verifizierer = new KlausurVeranstaltungsIdVerifizierer();
  private static final WireMockServer wireMockServer = new WireMockServer(options().port(8888));

  @BeforeAll
  static void setup() {
    verifizierer.setLsfDomain("http://localhost:8888/");
    verifizierer.setLsfUri("/{value}");

    wireMockServer.start();
    wireMockServer.stubFor(
        get(urlMatching("/1234")).willReturn(aResponse().withBody("Veranstaltungsart")));
    wireMockServer.stubFor(
        get(urlMatching("/12345")).willReturn(aResponse().withBody("Veranstaltungsart")));
    wireMockServer.stubFor(
        get(urlMatching("/1")).willReturn(aResponse().withBody("")));
  }

  @Test
  @DisplayName("Die Veranstaltung mit veranstaltungsId 1 existiert nicht")
  void test_1() {
    Long veranstaltungsId = 1L;

    boolean isValid = verifizierer.isValid(veranstaltungsId,
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Die Veranstaltung mit veranstaltungsId 1234 existiert")
  void test_2() {
    Long veranstaltungsId = 1234L;

    boolean isValid = verifizierer.isValid(veranstaltungsId,
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }

  @Test
  @DisplayName("Die Veranstaltung mit veranstaltungsId 12345 existiert")
  void test_3() {
    Long veranstaltungsId = 12345L;

    boolean isValid = verifizierer.isValid(veranstaltungsId,
        mock(ConstraintValidatorContext.class));

    assertThat(isValid).isTrue();
  }
}