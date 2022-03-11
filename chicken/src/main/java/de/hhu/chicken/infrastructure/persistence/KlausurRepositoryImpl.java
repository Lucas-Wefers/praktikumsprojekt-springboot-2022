package de.hhu.chicken.infrastructure.persistence;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.infrastructure.persistence.dao.KlausurDao;
import de.hhu.chicken.infrastructure.persistence.dto.KlausurDto;
import de.hhu.chicken.service.repositories.KlausurRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class KlausurRepositoryImpl implements KlausurRepository {

  private final KlausurDao klausurDao;

  public KlausurRepositoryImpl(KlausurDao klausurDao) {
    this.klausurDao = klausurDao;
  }

  private Klausur klausurDtoToKlausur(KlausurDto klausurDto) {
    return new Klausur(klausurDto.id(),
        klausurDto.fach(),
        klausurDto.datum(),
        klausurDto.von(),
        klausurDto.bis(),
        klausurDto.isPraesenz(),
        klausurDto.veranstaltungsId());
  }

  private KlausurDto klausurToKlausurDto(Klausur klausur) {
    return new KlausurDto(klausur.getId(),
        klausur.getFach(),
        klausur.getDatum(),
        klausur.getVon(),
        klausur.getBis(),
        klausur.isPraesenz(),
        klausur.getVeranstaltungsId());
  }

  @Override
  public List<Klausur> alleKlausuren() {
    List<KlausurDto> klausurDtos = klausurDao.findAll();
    return klausurDtos.stream()
        .map(this::klausurDtoToKlausur)
        .toList();
  }

  @Override
  public void klausurSpeichern(Klausur klausur) {
    klausurDao.save(klausurToKlausurDto(klausur));
  }

  @Override
  public Klausur findKlausurById(Long id) {
    Optional<KlausurDto> klausurDto = klausurDao.findById(id);
    if (klausurDto.isEmpty()) {
      return null;
    }
    return klausurDtoToKlausur(klausurDto.get());
  }
}
