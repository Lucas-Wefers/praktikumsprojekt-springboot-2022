package de.hhu.chicken.configuration;

import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class PraktikumsUhrzeitConfiguration {

  private LocalTime praktikumsStartuhrzeit;
  private LocalTime praktikumsEnduhrzeit;

  public PraktikumsUhrzeitConfiguration(@Value("${praktikumszeiten.start.uhrzeit}") LocalTime praktikumsStartuhrzeit,
                                        @Value("${praktikumszeiten.ende.uhrzeit}") LocalTime praktikumsEnduhrzeit) {
    this.praktikumsStartuhrzeit = praktikumsStartuhrzeit;
    this.praktikumsEnduhrzeit = praktikumsEnduhrzeit;
  }

  public LocalTime getPraktikumsStartuhrzeit() {
    return praktikumsStartuhrzeit;
  }

  public LocalTime getPraktikumsEnduhrzeit() {
    return praktikumsEnduhrzeit;
  }
}
