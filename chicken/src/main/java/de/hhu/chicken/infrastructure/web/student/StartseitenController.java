package de.hhu.chicken.infrastructure.web.student;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartseitenController {

  @GetMapping("/")
  @Secured("ROLE_USER")
  public String index() {
    return "index";
  }
}