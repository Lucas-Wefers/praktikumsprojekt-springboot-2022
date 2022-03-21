package de.hhu.chicken.infrastructure.persistence.dto;

import de.hhu.chicken.domain.student.Urlaubstermin;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("student")
public class StudentDto implements Persistable<Long> {

  @Id
  private final Long githubId;

  private String handle;

  @Column("student")
  private final List<KlausurReferenzDto> klausurReferenzen;

  @Column("student")
  private final List<Urlaubstermin> urlaubstermine;

  @Transient
  private final boolean isNew;

  @PersistenceConstructor
  public StudentDto(Long githubId, String handle, List<KlausurReferenzDto> klausurReferenzen,
                    List<Urlaubstermin> urlaubstermine) {
    this.githubId = githubId;
    this.handle = handle;
    this.klausurReferenzen = List.copyOf(klausurReferenzen);
    this.urlaubstermine = List.copyOf(urlaubstermine);
    this.isNew = true;
  }

  public StudentDto(Long githubId, boolean isNew, String handle,
                    List<KlausurReferenzDto> klausurReferenzen,
                    List<Urlaubstermin> urlaubstermine) {
    this.githubId = githubId;
    this.handle = handle;
    this.klausurReferenzen = List.copyOf(klausurReferenzen);
    this.urlaubstermine = List.copyOf(urlaubstermine);
    this.isNew = isNew;
  }

  public String getHandle() {
    return handle;
  }

  public List<KlausurReferenzDto> getKlausurReferenzen() {
    return List.copyOf(klausurReferenzen);
  }

  public List<Urlaubstermin> getUrlaubstermine() {
    return List.copyOf(urlaubstermine);
  }

  public Long getGithubId() {
    return githubId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StudentDto that = (StudentDto) o;
    return githubId.equals(that.githubId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(githubId);
  }

  @Override
  public Long getId() {
    return githubId;
  }

  @Override
  public boolean isNew() {
    return isNew;
  }
}
