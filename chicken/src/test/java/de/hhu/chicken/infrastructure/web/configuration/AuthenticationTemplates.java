package de.hhu.chicken.infrastructure.web.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

public class AuthenticationTemplates {

  public static MockHttpSession studentSession() {
    return createSession("SebastianStudent", "ROLE_STUDENT");
  }

  public static MockHttpSession organisatorSession() {
    return createSession("Adam Admin", "ROLE_ORGANISATOR");
  }

  public static MockHttpSession tutorSession() {
    return createSession("Loise Leader", "ROLE_TUTOR");
  }

  private static List<GrantedAuthority> buildAuthorities(Map<String, Object> attributes,
                                                         String[] roles) {
    return Arrays.stream(roles).map(r -> new OAuth2UserAuthority(r, attributes))
        .collect(Collectors.toList());
  }

  private static MockHttpSession createSession(String name, String... roles) {
    OAuth2AuthenticationToken principal = buildPrincipal(name, roles);
    MockHttpSession session = new MockHttpSession();
    session.setAttribute(
        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        new SecurityContextImpl(principal));
    return session;
  }

  private static OAuth2AuthenticationToken buildPrincipal(String name, String... roles) {
    Map<String, Object> attributes = new HashMap<>();
    attributes.put("sub", name);
    attributes.put("id", 28324332);
    attributes.put("login", "christianmeter");
    List<GrantedAuthority> authorities =
        buildAuthorities(attributes, roles);
    OAuth2User user = new DefaultOAuth2User(authorities, attributes, "sub");
    return new OAuth2AuthenticationToken(user, authorities, "whatever");
  }
}