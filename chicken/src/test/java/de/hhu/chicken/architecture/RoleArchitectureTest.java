package de.hhu.chicken.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.hhu.chicken.ChickenApplication;
import de.hhu.chicken.infrastructure.web.stereotypes.OrganisatorOnly;
import de.hhu.chicken.infrastructure.web.stereotypes.StudentOnly;
import de.hhu.chicken.infrastructure.web.stereotypes.TutorOnly;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@AnalyzeClasses(packagesOf = ChickenApplication.class,
    importOptions = ImportOption.DoNotIncludeTests.class)
public class RoleArchitectureTest {

  @ArchTest
  static final ArchRule studentControllersAreAnnotatedWithStudentOnly = classes()
      .that()
      .resideInAPackage("..controllers.student..")
      .should()
      .beAnnotatedWith(StudentOnly.class);

  @ArchTest
  static final ArchRule tutorControllersAreAnnotatedWithTutorOnly = classes()
      .that()
      .resideInAPackage("..controllers.tutor..")
      .should()
      .beAnnotatedWith(TutorOnly.class);

  @ArchTest
  static final ArchRule organisatorControllersAreAnnotatedWithOrganisatorOnly = classes()
      .that()
      .resideInAPackage("..controllers.organisator..")
      .should()
      .beAnnotatedWith(OrganisatorOnly.class);
}
