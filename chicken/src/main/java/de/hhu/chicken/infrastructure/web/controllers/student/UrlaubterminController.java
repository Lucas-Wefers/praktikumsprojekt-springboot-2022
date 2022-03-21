package de.hhu.chicken.infrastructure.web.controllers.student;

import de.hhu.chicken.infrastructure.web.forms.UrlaubsterminForm;
import de.hhu.chicken.infrastructure.web.stereotypes.StudentOnly;
import de.hhu.chicken.service.studentservice.StudentService;
import javax.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@StudentOnly
@Controller
public class UrlaubterminController {

  private final StudentService studentService;

  public UrlaubterminController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/urlaubsanmeldung")
  public String anmelden(UrlaubsterminForm urlaubsterminForm) {
    return "urlaubsterminAnmeldung";
  }
}