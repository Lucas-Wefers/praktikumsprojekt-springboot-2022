package de.hhu.chicken.infrastructure.web.controllers.student;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import de.hhu.chicken.service.klausurservice.KlausurService;
import de.hhu.chicken.service.studentservice.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class UrlaubsterminControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  KlausurService klausurService;

  @MockBean
  StudentService studentService;

  @Test
  @DisplayName("Die richtige Seite fuer das Eintragen von Urlaubsterminen wird aufgerufen und ist "
      + "erreichbar")
  void test_1() throws Exception {
    mvc.perform(get("/urlaubsanmeldung"))
        .andExpect(status().isOk())
        .andExpect(view().name("urlaubsterminAnmeldung"));
  }
}
