package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AvailabilityContainsKeywordsPredicateTest {
    private final List<DayOfWeek> monList = Collections.singletonList(DayOfWeek.MONDAY);
    private final List<DayOfWeek> monTueList = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);
    private final List<DayOfWeek> wedThuFriList =
            Arrays.asList( DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    private final Availability mon = new Availability(Collections.singletonList(DayOfWeek.MONDAY));
    private final Availability monTue = new Availability(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));

    @Test
    public void equals() {
        List<Availability> firstPredicateKeywordList = Collections.singletonList(mon);
        List<Availability> secondPredicateKeywordList = Collections.singletonList(monTue);

        AvailabilityContainsKeywordsPredicate firstPredicate =
                new AvailabilityContainsKeywordsPredicate(firstPredicateKeywordList);
        AvailabilityContainsKeywordsPredicate secondPredicate =
                new AvailabilityContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AvailabilityContainsKeywordsPredicate firstPredicateCopy =
                new AvailabilityContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_availContainsKeywords_returnsTrue() {
        // person's availability exactly matches the given availability of 1 day
        AvailabilityContainsKeywordsPredicate predicate =
                new AvailabilityContainsKeywordsPredicate(Collections.singletonList(mon));
        assertTrue(predicate.test(new PersonBuilder().withAvailability(monList).build()));

        // person's availability exactly matches the given availability of 2 days
        predicate = new AvailabilityContainsKeywordsPredicate(Collections.singletonList(monTue));
        assertTrue(predicate.test(new PersonBuilder().withAvailability(monTueList).build()));

        // person's availability includes the given availability
        predicate = new AvailabilityContainsKeywordsPredicate(Collections.singletonList(mon));
        assertTrue(predicate.test(new PersonBuilder().withAvailability(monTueList).build()));
    }

    @Test
    public void test_availDoesNotContainKeywords_returnsFalse() {
        // zero keywords
        AvailabilityContainsKeywordsPredicate predicate =
                new AvailabilityContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAvailability(monList).build()));

        // person's availability has zero matching days
        predicate = new AvailabilityContainsKeywordsPredicate(Collections.singletonList(monTue));
        assertFalse(predicate.test(new PersonBuilder().withAvailability(wedThuFriList).build()));

        // person's availability matches only 1 day out of 2 given days
        predicate = new AvailabilityContainsKeywordsPredicate(Collections.singletonList(monTue));
        assertFalse(predicate.test(new PersonBuilder().withAvailability(monList).build()));

        // person has default availability
        predicate = new AvailabilityContainsKeywordsPredicate(Collections.singletonList(monTue));
        assertFalse(predicate.test(new PersonBuilder().build()));
    }
}
