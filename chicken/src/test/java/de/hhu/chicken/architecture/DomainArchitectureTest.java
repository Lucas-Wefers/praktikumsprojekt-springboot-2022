package de.hhu.chicken.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.constructors;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static de.hhu.chicken.architecture.customrules.HaveExactlyOneAggregateRoot.HAVE_EXACTLY_ONE_AGGREGATE_ROOT;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.hhu.chicken.ChickenApplication;
import de.hhu.chicken.domain.stereotypes.AggregateRoot;
import de.hhu.chicken.domain.stereotypes.ValueObject;

@AnalyzeClasses(packagesOf = ChickenApplication.class,
    importOptions = ImportOption.DoNotIncludeTests.class)
public class DomainArchitectureTest {

  @ArchTest
  static final ArchRule aggregateRootsArePublic = classes()
      .that()
      .resideInAPackage("..domain..")
      .and()
      .areNotAnnotations()
      .and()
      .arePublic()
      .should()
      .beAnnotatedWith(AggregateRoot.class)
      .orShould()
      .beAnnotatedWith(ValueObject.class);

  @ArchTest
  static final ArchRule valueObjectsHavePackagePrivateConstructors = constructors()
      .that()
      .areDeclaredInClassesThat()
      .areAnnotatedWith(ValueObject.class)
      .and()
      .areDeclaredInClassesThat()
      .resideInAPackage("..domain..")
      .should()
      .bePackagePrivate();

  @ArchTest
  static final ArchRule oneAggregateRootPerAggregate = slices()
      .matching("..domain.(*)..")
      .should(HAVE_EXACTLY_ONE_AGGREGATE_ROOT);
}
