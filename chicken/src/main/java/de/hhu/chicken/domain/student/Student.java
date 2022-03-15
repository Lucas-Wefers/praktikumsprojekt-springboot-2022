package de.hhu.chicken.domain.student;

import de.hhu.chicken.domain.stereotypes.AggregateRoot;
import java.util.Set;

@AggregateRoot
public class Student {

  GithubHandle githubHandle;
  Set<KlausurReferenz> klausurReferenzen;
  Set<Urlaubstermin> urlaubstermine;
}
