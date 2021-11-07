package seedu.address.model.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MemberBuilder;

public class TotalAttendanceContainsKeywordsPredicateTest {
    private final List<TotalAttendance> firstPredicateKeywordList = Collections.singletonList(new TotalAttendance(0));
    private final List<TotalAttendance> secondPredicateKeywordList = Collections.singletonList(new TotalAttendance(10));

    @Test
    public void equals() {
        TotalAttendanceContainsKeywordsPredicate firstPredicate =
                new TotalAttendanceContainsKeywordsPredicate(firstPredicateKeywordList);
        TotalAttendanceContainsKeywordsPredicate secondPredicate =
                new TotalAttendanceContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TotalAttendanceContainsKeywordsPredicate firstPredicateCopy =
                new TotalAttendanceContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_totalAttendanceContainsKeywords_returnTrue() {
        // matching keyword
        TotalAttendanceContainsKeywordsPredicate predicate =
                new TotalAttendanceContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(predicate.test(new MemberBuilder().withTotalAttendance(0).build()));

        predicate = new TotalAttendanceContainsKeywordsPredicate(secondPredicateKeywordList);
        assertTrue(predicate.test(new MemberBuilder().withTotalAttendance(10).build()));
    }

    @Test
    public void test_totalAttendanceDoesNotContainKeywords_returnFalse() {
        // zero keywords
        TotalAttendanceContainsKeywordsPredicate predicate =
                new TotalAttendanceContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MemberBuilder().build()));

        // non-matching keyword
        predicate = new TotalAttendanceContainsKeywordsPredicate(firstPredicateKeywordList);
        assertFalse(predicate.test(new MemberBuilder().withTotalAttendance(10).build()));
    }
}
