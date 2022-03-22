package de.hhu.chicken.infrastructure.web.controllers.student;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.infrastructure.web.stereotypes.StudentOnly;
import de.hhu.chicken.service.studentservice.StudentService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@StudentOnly
public class UebersichtController {

  private final StudentService studentService;

  public UebersichtController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/")
  public String index(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                      Model model) {
    Long githubId = ((Integer) principal.getAttributes().get("id")).longValue();

    Student student = studentService.findStudentByGithubId(githubId);
    List<Klausur> klausuren = studentService.alleAngemeldetenKlausuren(githubId);

    model.addAttribute("heute", LocalDate.now());
    model.addAttribute("student", student);
    model.addAttribute("klausuren", klausuren);

    return "uebersicht";
  }
}