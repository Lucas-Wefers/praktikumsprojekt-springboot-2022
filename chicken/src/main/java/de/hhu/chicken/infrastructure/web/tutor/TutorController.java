package de.hhu.chicken.infrastructure.web.tutor;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TutorController {

  @GetMapping("/tutor")
  @Secured("ROLE_TUTOR")
  public String index() {
    return "tutorIndex";
  }
}
