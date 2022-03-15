package de.hhu.chicken.domain.student;

import de.hhu.chicken.domain.stereotypes.AggregateRoot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AggregateRoot
public class Student {

  GithubHandle githubHandle;
  Set<KlausurReferenz> klausurReferenzen = new HashSet<>();
  Set<Urlaubstermin> urlaubstermine = new HashSet<>();

  public Student(String githubHandle) {
    this.githubHandle = new GithubHandle(githubHandle);
  }

  public void fuegeKlausurterminHinzu(Long klausurReferenz) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public void fuegeUrlaubsterminHinzu(LocalDate datum, LocalTime von, LocalTime bis) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public double berechneResturlaub() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public String getGithubHandle() {
    return githubHandle.handle();
  }

  public Set<Long> getKlausurReferenzen() {
    return klausurReferenzen.stream()
        .map(KlausurReferenz::id)
        .collect(Collectors.toSet());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Student student = (Student) o;
    return githubHandle.equals(student.githubHandle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(githubHandle);
  }
}
