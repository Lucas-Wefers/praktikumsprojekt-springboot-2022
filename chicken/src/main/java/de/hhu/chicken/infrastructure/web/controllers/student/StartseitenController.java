package de.hhu.chicken.infrastructure.web.controllers.student;

import de.hhu.chicken.infrastructure.web.stereotypes.StudentOnly;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@StudentOnly
public class StartseitenController {

  @GetMapping("/")
  public String index() {
    return "index";
  }
}
