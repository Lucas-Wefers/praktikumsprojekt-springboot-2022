package de.hhu.chicken.domain.student;

import de.hhu.chicken.domain.stereotypes.AggregateRoot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AggregateRoot
public class Student {

  GithubHandle githubHandle;
  List<KlausurReferenz> klausurReferenzen = new ArrayList<>();
  List<Urlaubstermin> urlaubstermine = new ArrayList<>();

  public Student(String githubHandle) {
    this.githubHandle = new GithubHandle(githubHandle);
  }

  public void fuegeKlausurHinzu(Long klausurReferenz, LocalDate datum,
                                LocalTime vonKlausurFreistellung,
                                LocalTime bisKlausurFreistellung) {
    if (containsKlausurReferenz(klausurReferenz)) {
      return;
    }
    klausurReferenzen.add(new KlausurReferenz(klausurReferenz));

    List<Urlaubstermin> urlaubstermineMitSelbemDatum = getUrlaubstermineMitSelbemDatum(datum);
    urlaubstermine.removeAll(urlaubstermineMitSelbemDatum);

    aktualisiereUrlaubstermine(datum, vonKlausurFreistellung, bisKlausurFreistellung,
        urlaubstermineMitSelbemDatum);
  }

  public void fuegeUrlaubsterminHinzu(LocalDate datum, LocalTime von, LocalTime bis,
                                      boolean istKlausurtag) {
    Urlaubstermin urlaubstermin = new Urlaubstermin(datum, von, bis);
    int urlaubsdauer = (int) urlaubstermin.dauer().toMinutes();

    if (urlaubsdauer > berechneResturlaub()) {
      return;
    }

    if (istKlausurtag) {
      urlaubstermine.add(urlaubstermin);

    } else if (istValideUrlaubsdauer(urlaubstermin)) {
      urlaubstermine.add(urlaubstermin);
    }
  }

  public int berechneResturlaub() {
    return 240 - urlaubstermine.stream()
        .mapToInt(x -> (int) x.dauer().toMinutes())
        .sum();
  }

  private void aktualisiereUrlaubstermine(LocalDate datum, LocalTime vonKlausurFreistellung,
                                          LocalTime bisKlausurFreistellung,
                                          List<Urlaubstermin> urlaubstermineMitSelbemDatum) {
    for (Urlaubstermin urlaubstermin : urlaubstermineMitSelbemDatum) {
      LocalTime vonUrlaub = urlaubstermin.getVon();
      LocalTime bisUrlaub = urlaubstermin.getBis();

      if (isAusserhalbVonUrlaubstermin(vonKlausurFreistellung, bisKlausurFreistellung, vonUrlaub,
          bisUrlaub)) {
        urlaubstermine.add(urlaubstermin);
      } else {
        spalteUrlaubstermin(datum, vonKlausurFreistellung, bisKlausurFreistellung, vonUrlaub,
            bisUrlaub);
      }
    }
  }

  private boolean containsKlausurReferenz(Long klausurReferenz) {
    return klausurReferenzen.stream()
        .anyMatch(x -> x.id().equals(klausurReferenz));
  }

  private List<Urlaubstermin> getUrlaubstermineMitSelbemDatum(LocalDate datum) {
    return urlaubstermine.stream()
        .filter(x -> x.getDatum().equals(datum))
        .toList();
  }

  private void spalteUrlaubstermin(LocalDate datum, LocalTime vonKlausurFreistellung,
                                   LocalTime bisKlausurFreistellung, LocalTime vonUrlaub,
                                   LocalTime bisUrlaub) {
    if (vonUrlaub.isBefore(vonKlausurFreistellung)) {
      urlaubstermine.add(new Urlaubstermin(datum, vonUrlaub, vonKlausurFreistellung));
    }
    if (bisKlausurFreistellung.isBefore(bisUrlaub)) {
      urlaubstermine.add(new Urlaubstermin(datum, bisKlausurFreistellung, bisUrlaub));
    }
  }

  private boolean isAusserhalbVonUrlaubstermin(LocalTime vonKlausurFreistellung,
                                               LocalTime bisKlausurFreistellung,
                                               LocalTime vonUrlaub, LocalTime bisUrlaub) {
    return bisKlausurFreistellung.isBefore(vonUrlaub)
        || bisUrlaub.isBefore(vonKlausurFreistellung);
  }

  public String getGithubHandle() {
    return githubHandle.handle();
  }

  public List<Long> getKlausurReferenzen() {
    return klausurReferenzen.stream()
        .map(KlausurReferenz::id)
        .toList();
  }

  private boolean istValideUrlaubsdauer(Urlaubstermin urlaubstermin) {
    List<Urlaubstermin> urlaubstermineMitGleichemDatum = getUrlaubstermineMitSelbemDatum(
        urlaubstermin.getDatum());

    if (urlaubstermineMitGleichemDatum.size() == 1) {
      Urlaubstermin urlaubstermin2 = urlaubstermineMitGleichemDatum.get(0);
      return istValideUrlaubsdauerFuerZweiUrlaube(urlaubstermin, urlaubstermin2);
    }

    if (urlaubstermineMitGleichemDatum.size() == 0) {
      return istValideUrlaubsdauerFuerEinenUrlaub(urlaubstermin);
    }

    return false;
  }

  private boolean istValideUrlaubsdauerFuerZweiUrlaube(Urlaubstermin urlaubstermin,
                                                       Urlaubstermin urlaubstermin2) {
    boolean istValideUrlaubsDauerFuerZweiUrlaube =
        urlaubstermin.dauer().plus(urlaubstermin2.dauer()).toMinutes() <= 150;

    if (urlaubstermin.getVon().equals(LocalTime.of(9, 30))
        && urlaubstermin2.getBis().equals(LocalTime.of(13, 30))) {
      return istValideUrlaubsDauerFuerZweiUrlaube;
    }
    if (urlaubstermin2.getVon().equals(LocalTime.of(9, 30))
        && urlaubstermin.getBis().equals(LocalTime.of(13, 30))) {
      return istValideUrlaubsDauerFuerZweiUrlaube;
    }
    return false;
  }

  private boolean istValideUrlaubsdauerFuerEinenUrlaub(Urlaubstermin urlaubstermin) {
    return urlaubstermin.dauer().toMinutes() <= 150
        || urlaubstermin.dauer().toMinutes() == 240;
  }

  public List<Urlaubstermin> getUrlaubstermine() {
    return List.copyOf(urlaubstermine);
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
