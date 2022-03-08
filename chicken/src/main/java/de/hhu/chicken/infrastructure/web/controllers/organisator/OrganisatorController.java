package de.hhu.chicken.infrastructure.web.controllers.organisator;

import de.hhu.chicken.infrastructure.web.stereotypes.OrganisatorOnly;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@OrganisatorOnly
public class OrganisatorController {

  @GetMapping("/organisator")
  public String index() {
    return "organisatorIndex";
  }
}
