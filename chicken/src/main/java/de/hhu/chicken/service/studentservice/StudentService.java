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
    Student student = studentRepository.findStudentByHandle(handle);
    if(student == null) {
      student = new Student(handle);
    }
    Klausur klausur = klausurRepository.findKlausurById(id);
    student.fuegeKlausurHinzu(id,
        klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
    studentRepository.studentSpeichern(student);
  }

  public void urlaubAnmelden(String handle, LocalDate datum, LocalTime von, LocalTime bis) {
    Student student = studentRepository.findStudentByHandle(handle);
    if(student == null) {
      student = new Student(handle);
    }
    List<Long> klausurReferenzen = student.getKlausurReferenzen();
    List<Klausur> klausurenAmGleichenTag = klausurReferenzen.stream()
        .map(r -> klausurRepository.findKlausurById(r))
        .filter(x -> x.getDatum().equals(datum))
        .toList();

    boolean istKlausurTag = !klausurenAmGleichenTag.isEmpty();
    List<Klausur> ueberschneideneKlausuren = klausurenAmGleichenTag.stream()
        .filter(x -> von.isBefore(x.berechneFreistellungsEndzeitpunkt()))
        .filter(x -> x.berechneFreistellungsStartzeitpunkt().isBefore(bis))
        .toList();
    
    for (Klausur klausur : ueberschneideneKlausuren) {
      student.storniereKlausur(klausur.getId());
    }
    student.fuegeUrlaubsterminHinzu(datum, von, bis, istKlausurTag);
    for (Klausur klausur : ueberschneideneKlausuren) {
      student.fuegeKlausurHinzu(klausur.getId(),
          klausur.getDatum(),
          klausur.berechneFreistellungsStartzeitpunkt(),
          klausur.berechneFreistellungsEndzeitpunkt());
    }
    studentRepository.studentSpeichern(student);
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
