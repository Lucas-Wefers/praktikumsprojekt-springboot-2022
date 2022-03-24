package de.hhu.chicken.infrastructure.web.controllers.student;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.infrastructure.web.forms.KlausurForm;
import de.hhu.chicken.infrastructure.web.stereotypes.StudentOnly;
import de.hhu.chicken.service.klausurservice.KlausurService;
import de.hhu.chicken.service.studentservice.StudentService;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@StudentOnly
@Controller
public class KlausurController {

  private final KlausurService klausurService;
  private final StudentService studentService;

  public KlausurController(KlausurService klausurService,
                           StudentService studentService) {
    this.klausurService = klausurService;
    this.studentService = studentService;
  }

  @GetMapping("/klausuren")
  public String klausuren(KlausurForm klausurForm) {
    return "klausurEintragung";
  }

  @PostMapping("/klausuren")
  public String klausurEintragen(@Valid KlausurForm klausurForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "klausurEintragung";
    }
    klausurService.klausurSpeichern(klausurForm.toKlausur());
    return "klausurErfolg";
  }

  @GetMapping("/klausuranmeldung")
  public String klausurAnmelden(Model model) {
    List<Klausur> klausuren = klausurService.alleKlausuren();
    model.addAttribute("klausuren", klausuren);
    return "klausurAnmeldung";
  }

  @PostMapping("/klausuranmeldung")
  public String klausurAnmelden(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                                Long id) {

    if (id == null
        || klausurService.findKlausurById(id) == null) {
      return "klausurAnmeldung";
    }

    Long githubId = ((Integer) principal.getAttributes().get("id")).longValue();
    String handle = (String) principal.getAttributes().get("login");

    studentService.klausurAnmelden(githubId, handle, id);

    return "redirect:/";
  }

  @PostMapping("/klausurstornieren")
  public String klausurstornieren(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                                  Long id) {
    Long githubId = ((Integer) principal.getAttributes().get("id")).longValue();

    studentService.klausurStornieren(githubId, id, LocalDate.now());

    return "redirect:/";
  }
}