package de.hhu.chicken.infrastructure.web.organisator;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;


@Controller
public class OrganisatorController {

  @GetMapping("/organisator")
  @Secured("ROLE_ADMIN")
  public String index() {
    return "organisatorIndex";
  }
}
