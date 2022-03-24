package de.hhu.chicken.configuration;

import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class PraktikumsUhrzeitConfiguration {

  private LocalTime praktikumsStartuhrzeit;
  private LocalTime praktikumsEnduhrzeit;

  public PraktikumsUhrzeitConfiguration(@Value("${praktikumszeiten.start.uhrzeit}") String praktikumsStartuhrzeit,
                                        @Value("${praktikumszeiten.ende.uhrzeit}") String praktikumsEnduhrzeit) {
    this.praktikumsStartuhrzeit = LocalTime.parse(praktikumsStartuhrzeit);
    this.praktikumsEnduhrzeit = LocalTime.parse(praktikumsEnduhrzeit);
  }

  public LocalTime getPraktikumsStartuhrzeit() {
    return praktikumsStartuhrzeit;
  }

  public LocalTime getPraktikumsEnduhrzeit() {
    return praktikumsEnduhrzeit;
  }
}
