package de.hhu.chicken.infrastructure.web.configuration;

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
    mockMvc.perform(get("/")).andExpect(status().is3xxRedirection());
  }

}