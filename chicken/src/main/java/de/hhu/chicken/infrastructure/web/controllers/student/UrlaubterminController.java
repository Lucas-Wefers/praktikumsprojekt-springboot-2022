package de.hhu.chicken.infrastructure.web.controllers.student;

import de.hhu.chicken.infrastructure.web.forms.UrlaubsterminForm;
import de.hhu.chicken.infrastructure.web.stereotypes.StudentOnly;
import de.hhu.chicken.service.studentservice.StudentService;
import java.time.LocalDate;
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

  @GetMapping("/urlaubsterminanmeldung")
  public String urlaubsterminAnmelden(UrlaubsterminForm urlaubsterminForm) {
    return "urlaubsterminAnmeldung";
  }

  @PostMapping("/urlaubsterminanmeldung")
  public String urlaubsterminEintragen(
      @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
      @Valid UrlaubsterminForm urlaubstermin,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "urlaubsterminAnmeldung";
    }

    Long githubId = ((Integer) principal.getAttributes().get("id")).longValue();
    String handle = (String) principal.getAttributes().get("login");

    studentService.urlaubsterminAnmelden(githubId,
        handle,
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis());

    return "redirect:/";
  }

  @PostMapping("/urlaubsterminstornieren")
  public String urlaubsterminStornieren(
      @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
      UrlaubsterminForm urlaubstermin) {
    Long githubId = ((Integer) principal.getAttributes().get("id")).longValue();

    studentService.urlaubsterminStornieren(githubId,
        urlaubstermin.getDatum(),
        urlaubstermin.getVon(),
        urlaubstermin.getBis(),
        LocalDate.now());

    return "redirect:/";
  }
}