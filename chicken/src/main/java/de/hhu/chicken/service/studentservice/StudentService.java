package de.hhu.chicken.service.studentservice;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.service.repositories.KlausurRepository;
import de.hhu.chicken.service.repositories.StudentRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class StudentService {

  private final StudentRepository studentRepository;
  private final KlausurRepository klausurRepository;

  public StudentService(StudentRepository studentRepository,
                        KlausurRepository klausurRepository) {
    this.studentRepository = studentRepository;
    this.klausurRepository = klausurRepository;
  }

  public void klausurAnmelden(String handle, Long id) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public void urlaubAnmelden(String handle, LocalDate datum, LocalTime von, LocalTime bis) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public void klausurStornieren(String handle, Long id) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public void urlaubStornieren(String handle, LocalDate datum, LocalTime von, LocalTime bis) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public Student findStudentByHandle(String handle) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public List<Klausur> alleAngemeldetenKlausuren(String handle) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
