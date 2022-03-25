package de.hhu.chicken.architecture;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.hhu.chicken.ChickenApplication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@AnalyzeClasses(packagesOf = ChickenApplication.class,
    importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

  @ArchTest
  static final ArchRule onionArchitectureTest = layeredArchitecture()
      .layer("domain").definedBy("..domain..")
      .layer("service").definedBy("..service..")
      .layer("infrastructure").definedBy("..infrastructure..")
      .whereLayer("infrastructure").mayNotBeAccessedByAnyLayer()
      .whereLayer("service").mayOnlyBeAccessedByLayers("infrastructure")
      .whereLayer("domain").mayOnlyBeAccessedByLayers("infrastructure", "service");

}
