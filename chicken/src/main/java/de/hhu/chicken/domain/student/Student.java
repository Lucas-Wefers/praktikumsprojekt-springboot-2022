package de.hhu.chicken.domain.student;

import de.hhu.chicken.domain.stereotypes.AggregateRoot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AggregateRoot
public class Student {

  private final GithubId githubId;
  private final GithubHandle handle;
  private List<KlausurReferenz> klausurReferenzen = new ArrayList<>();
  private List<Urlaubstermin> urlaubstermine = new ArrayList<>();

  public Student(Long githubId, String handle) {
    this.githubId = new GithubId(githubId);
    this.handle = new GithubHandle(handle);
  }

  public Student(Long githubId, String handle,
                 List<Long> klausurReferenzen,
                 List<Urlaubstermin> urlaubstermine) {
    this.githubId = new GithubId(githubId);
    this.handle = new GithubHandle(handle);
    this.klausurReferenzen = klausurReferenzen.stream()
        .map(KlausurReferenz::new)
        .collect(Collectors.toList());
    this.urlaubstermine = new ArrayList<>(urlaubstermine);
  }

  public int berechneResturlaub() {
    return 240 - urlaubstermine.stream()
        .mapToInt(x -> (int) x.dauer().toMinutes())
        .sum();
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

  public boolean fuegeUrlaubsterminHinzu(LocalDate datum, LocalTime von, LocalTime bis,
                                         boolean istKlausurtag, LocalTime startUhrzeit,
                                         LocalTime endUhrzeit) {
    if (berechneResturlaub() == 0) {
      return false;
    }

    List<Urlaubstermin> alleUrlaubsTermine = new ArrayList<>(urlaubstermine);
    List<Urlaubstermin> termineAmSelbemTag = getUrlaubstermineMitSelbemDatum(datum);
    List<Urlaubstermin> ueberschneidendeTermine = termineAmSelbemTag
        .stream()
        .filter(x -> termineUeberschneidenSich(von, bis, x.getVon(), x.getBis()))
        .collect(Collectors.toList());
    ueberschneidendeTermine.add(new Urlaubstermin(datum, von, bis));

    LocalTime minVon = ueberschneidendeTermine
        .stream()
        .map(Urlaubstermin::getVon)
        .reduce((acc, t) -> t.isBefore(acc) ? t : acc)
        .orElse(von);

    LocalTime maxBis = ueberschneidendeTermine
        .stream()
        .map(Urlaubstermin::getBis)
        .reduce((acc, t) -> t.isAfter(acc) ? t : acc)
        .orElse(bis);

    Urlaubstermin vereinigt = new Urlaubstermin(datum, minVon, maxBis);
    termineAmSelbemTag.removeAll(ueberschneidendeTermine);
    termineAmSelbemTag.add(vereinigt);

    alleUrlaubsTermine.removeAll(ueberschneidendeTermine);
    alleUrlaubsTermine.add(vereinigt);

    if (istValiderUrlaub(istKlausurtag, alleUrlaubsTermine, termineAmSelbemTag,
        startUhrzeit, endUhrzeit)) {
      urlaubstermine = alleUrlaubsTermine;
      return true;
    }
    return false;
  }

  public boolean isUrlaubsterminStornierbar(LocalDate datum, LocalDate heute) {
    return heute.isBefore(datum);
  }

  public void storniereKlausur(Long klausurId) {
    klausurReferenzen.remove(new KlausurReferenz(klausurId));
  }

  public void storniereUrlaub(LocalDate datum, LocalTime von, LocalTime bis) {
    urlaubstermine.remove(new Urlaubstermin(datum, von, bis));
  }

  private boolean istValiderUrlaub(boolean istKlausurtag, List<Urlaubstermin> alleUrlaubsTermine,
                                   List<Urlaubstermin> termineAmSelbemTag,
                                   LocalTime startUhrzeit, LocalTime endUhrzeit) {
    return (istKlausurtag
        || hatEinenUrlaubsblockDerDenGanzenTagDauert(termineAmSelbemTag)
        || hatEinenUrlaubsblockDerKuerzerAlsZweiStundenIst(termineAmSelbemTag)
        || hatZweiUrlaubsbloeckeWobeiEinerAmAnfangUndEinerAmEndeIst(termineAmSelbemTag,
        startUhrzeit, endUhrzeit))
        && esIstGenugUrlaubszeitVorhanden(alleUrlaubsTermine);
  }

  private boolean esIstGenugUrlaubszeitVorhanden(List<Urlaubstermin> alleUrlaubsTermine) {
    return alleUrlaubsTermine.stream().mapToInt(t -> (int) t.dauer().toMinutes()).sum() <= 240;
  }

  private boolean hatZweiUrlaubsbloeckeWobeiEinerAmAnfangUndEinerAmEndeIst(
      List<Urlaubstermin> termineAmSelbemTag, LocalTime startUhrzeit,
      LocalTime endUhrzeit) {
    return termineAmSelbemTag.stream().mapToInt(t -> (int) t.dauer().toMinutes()).sum() <= 150
        && termineAmSelbemTag.size() == 2
        && termineAmSelbemTag
        .stream()
        .anyMatch(t -> t.getVon()
            .equals(startUhrzeit))
        && termineAmSelbemTag
        .stream()
        .anyMatch(t -> t.getBis()
            .equals(endUhrzeit));
  }

  private boolean hatEinenUrlaubsblockDerKuerzerAlsZweiStundenIst(
      List<Urlaubstermin> termineAmSelbemTag) {
    return termineAmSelbemTag.stream().mapToInt(t -> (int) t.dauer().toMinutes()).sum() <= 150
        && termineAmSelbemTag.size() == 1;
  }

  private boolean hatEinenUrlaubsblockDerDenGanzenTagDauert(
      List<Urlaubstermin> termineAmSelbemTag) {
    int dauerTag = termineAmSelbemTag.stream().mapToInt(t -> (int) t.dauer().toMinutes()).sum();
    return dauerTag == 240 && termineAmSelbemTag.size() == 1;
  }

  private boolean termineUeberschneidenSich(LocalTime von1,
                                            LocalTime bis1,
                                            LocalTime von2,
                                            LocalTime bis2) {
    return !von1.isAfter(bis2) && !von2.isAfter(bis1);
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
        .anyMatch(x -> x.klausurId().equals(klausurReferenz));
  }

  private List<Urlaubstermin> getUrlaubstermineMitSelbemDatum(LocalDate datum) {
    return urlaubstermine.stream()
        .filter(x -> x.getDatum().equals(datum))
        .collect(Collectors.toList());
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

  public String getHandle() {
    return handle.handle();
  }

  public List<Long> getKlausurReferenzen() {
    return klausurReferenzen.stream()
        .map(KlausurReferenz::klausurId)
        .toList();
  }

  public List<Urlaubstermin> getUrlaubstermine() {
    return List.copyOf(urlaubstermine);
  }

  public Long getGithubId() {
    return githubId.id();
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
    return githubId.equals(student.githubId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(githubId);
  }
}