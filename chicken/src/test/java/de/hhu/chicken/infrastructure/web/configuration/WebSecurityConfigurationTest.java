package de.hhu.chicken.infrastructure.web.configuration;

import static de.hhu.chicken.infrastructure.web.configuration.AuthenticationTemplates.studentSession;
import static de.hhu.chicken.infrastructure.web.configuration.AuthenticationTemplates.tutorSession;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WebSecurityConfigurationTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("Uneingeloggter Nutzer wird auf GitHub redirected")
  void test_1() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  @DisplayName("Studenten dürfen auf die Studentenseite zugreifen")
  void test_2() throws Exception {
    mockMvc.perform(get("/").session(studentSession()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Studenten dürfen nicht auf die Tutorenseite zugreifen")
  void test_3() throws Exception {
    mockMvc.perform(get("/tutor").session(studentSession()))
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Studenten dürfen nicht auf die Organisatorenseite zugreifen")
  void test_4() throws Exception {
    mockMvc.perform(get("/organisator").session(studentSession()))
        .andExpect(status().isForbidden());
  }


}