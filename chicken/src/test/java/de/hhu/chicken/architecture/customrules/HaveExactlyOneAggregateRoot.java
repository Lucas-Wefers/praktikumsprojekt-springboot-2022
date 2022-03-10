package de.hhu.chicken.architecture.customrules;

import static com.tngtech.archunit.lang.SimpleConditionEvent.satisfied;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static java.util.stream.Collectors.toList;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.library.dependencies.Slice;
import de.hhu.chicken.domain.stereotypes.AggregateRoot;
import java.util.List;

public class HaveExactlyOneAggregateRoot extends ArchCondition<Slice> {

  public static final HaveExactlyOneAggregateRoot HAVE_EXACTLY_ONE_AGGREGATE_ROOT =
      new HaveExactlyOneAggregateRoot("have exactly one Aggregate Root");

  HaveExactlyOneAggregateRoot(String description, Object... args) {
    super(description, args);
  }

  public void check(Slice slice, ConditionEvents events) {
    List<String> rootNames = getAggregateRootNames(slice);
    String packageName = slice.iterator().next().getPackageName();
    int numberOfAggregateRoots = rootNames.size();

    if (isStereotypesInDomain(slice, events, packageName)) {
      return;
    }

    switch (numberOfAggregateRoots) {
      case 0 -> events.add(violated(slice, packageName + " has no aggregate root"));
      case 1 -> events.add(satisfied(slice, "ok!"));
      default -> events.add(violated(slice, packageName + " has more than one aggregate root"));
    }
  }

  private boolean isStereotypesInDomain(Slice slice, ConditionEvents events, String packageName) {
    if (packageName.equals("de.hhu.chicken.domain.stereotypes")) {
      events.add(satisfied(slice, "ok!"));
      return true;
    }
    return false;
  }

  private List<String> getAggregateRootNames(Slice slice) {
    return slice.stream()
        .filter(c -> c.isAnnotatedWith(AggregateRoot.class))
        .map(JavaClass::getName)
        .collect(toList());
  }
}