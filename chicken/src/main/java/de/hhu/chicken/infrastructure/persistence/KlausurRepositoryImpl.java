package de.hhu.chicken.infrastructure.persistence;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.klausur.Klausurart;
import de.hhu.chicken.domain.klausur.VeranstaltungsId;
import de.hhu.chicken.infrastructure.persistence.dao.KlausurDao;
import de.hhu.chicken.infrastructure.persistence.dto.KlausurDto;
import de.hhu.chicken.service.repositories.KlausurRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class KlausurRepositoryImpl implements KlausurRepository {

  private final KlausurDao klausurDao;

  public KlausurRepositoryImpl(KlausurDao klausurDao) {
    this.klausurDao = klausurDao;
  }

  private Klausur klausurDtoToKlausur(KlausurDto klausurDto) {
    Klausurart klausurart = Klausurart.PRAESENZ;
    if (klausurDto.getKlausurart() == 0) {
      klausurart = Klausurart.ONLINE;
    }
    return new Klausur(klausurDto.getUuid(),
        klausurDto.getFach(),
        klausurDto.getDatum(),
        klausurDto.getVon(),
        klausurDto.getBis(),
        klausurart,
        new VeranstaltungsId(klausurDto.getVeranstaltungsId()));
  }

  private KlausurDto klausurToKlausurDto(Klausur klausur) {
    return new KlausurDto(klausur.getUuid(),
        klausur.getFach(),
        klausur.getDatum(),
        klausur.getVon(),
        klausur.getBis(),
        klausur.getKlausurart().ordinal(),
        klausur.getVeranstaltungsId().getId());
  }

  @Override
  public List<Klausur> alleKlausuren() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public void klausurSpeichern(Klausur klausur) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public Klausur findKlausurByUuid(UUID uuid) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
