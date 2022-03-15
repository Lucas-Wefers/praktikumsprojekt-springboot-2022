package de.hhu.chicken.domain.student;

import java.time.LocalDate;
import java.time.LocalTime;

record Urlaubstermin(LocalDate datum, LocalTime von, LocalTime bis) {

}
