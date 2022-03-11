package de.hhu.chicken.infrastructure.web.controllers.student;

import de.hhu.chicken.infrastructure.web.forms.KlausurForm;
import de.hhu.chicken.infrastructure.web.stereotypes.StudentOnly;
import de.hhu.chicken.service.klausurservice.KlausurService;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@StudentOnly
@Controller
public class KlausurController {

  final KlausurService klausurService;

  public KlausurController(KlausurService klausurService) {
    this.klausurService = klausurService;
  }

  @GetMapping("/klausuren")
  public String klausuren(KlausurForm klausurForm) {
    return "klausurEintragung";
  }

  @PostMapping("/klausuren")
  public String eintragen(@Valid KlausurForm klausurForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "klausurEintragung";
    }
    klausurService.klausurSpeichern(klausurForm.toKlausur());
    return "klausurErfolg";
  }
}