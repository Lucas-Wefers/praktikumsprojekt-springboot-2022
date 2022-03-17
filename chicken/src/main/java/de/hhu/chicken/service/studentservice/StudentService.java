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
    if (student == null) {
      student = new Student(handle);
    }
    Klausur klausur = klausurRepository.findKlausurById(id);
    student.fuegeKlausurHinzu(id,
        klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
    studentRepository.studentSpeichern(student);
  }

  public void urlaubsterminAnmelden(String handle, LocalDate datum, LocalTime von, LocalTime bis) {
    Student student = studentRepository.findStudentByHandle(handle);
    if (student == null) {
      student = new Student(handle);
    }
    List<Long> klausurReferenzen = student.getKlausurReferenzen();
    List<Klausur> klausurenAmGleichenTag = klausurReferenzen.stream()
        .map(klausurRepository::findKlausurById)
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
    Student student = studentRepository.findStudentByHandle(handle);
    student.storniereKlausur(id);
    studentRepository.studentSpeichern(student);
  }

  public void urlaubsterminStornieren(String handle, LocalDate datum, LocalTime von,
                                      LocalTime bis) {
    Student student = studentRepository.findStudentByHandle(handle);
    student.storniereUrlaub(datum, von, bis);
    studentRepository.studentSpeichern(student);
  }

  public Student findStudentByHandle(String handle) {
    return studentRepository.findStudentByHandle(handle);
  }

  public List<Klausur> alleAngemeldetenKlausuren(String handle) {
    Student student = studentRepository.findStudentByHandle(handle);
    if (student == null) {
      return List.of();
    }
    List<Long> klausurReferenzen = student.getKlausurReferenzen();
    return klausurReferenzen.stream()
        .map(klausurRepository::findKlausurById)
        .toList();
  }
}
