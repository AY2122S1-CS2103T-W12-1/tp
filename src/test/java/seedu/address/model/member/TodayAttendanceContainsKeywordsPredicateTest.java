package seedu.address.model.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MemberBuilder;

public class TodayAttendanceContainsKeywordsPredicateTest {
    private final List<TodayAttendance> truePredicateKeywordList = Collections.singletonList(new TodayAttendance(true));
    private final List<TodayAttendance> falsePredicateKeywordList =
            Collections.singletonList(new TodayAttendance(false));
    @Test
    public void equals() {
        TodayAttendanceContainsKeywordsPredicate firstPredicate =
                new TodayAttendanceContainsKeywordsPredicate(truePredicateKeywordList);
        TodayAttendanceContainsKeywordsPredicate secondPredicate =
                new TodayAttendanceContainsKeywordsPredicate(falsePredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TodayAttendanceContainsKeywordsPredicate firstPredicateCopy =
                new TodayAttendanceContainsKeywordsPredicate(truePredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_todayAttendanceContainsKeywords_returnsTrue() {
        // matching keyword
        TodayAttendanceContainsKeywordsPredicate predicate =
                new TodayAttendanceContainsKeywordsPredicate(truePredicateKeywordList);
        assertTrue(predicate.test(new MemberBuilder().withTodayAttendance(true).build()));

        predicate = new TodayAttendanceContainsKeywordsPredicate(falsePredicateKeywordList);
        assertTrue(predicate.test(new MemberBuilder().withTodayAttendance(false).build()));

    }

    @Test
    public void test_todayAttendanceDoesNotContainKeywords_returnFalse() {
        // zero keywords
        TodayAttendanceContainsKeywordsPredicate predicate =
                new TodayAttendanceContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MemberBuilder().build()));

        // non-matching keyword
        predicate = new TodayAttendanceContainsKeywordsPredicate(truePredicateKeywordList);
        assertFalse(predicate.test(new MemberBuilder().withTodayAttendance(false).build()));

        predicate = new TodayAttendanceContainsKeywordsPredicate(falsePredicateKeywordList);
        assertFalse(predicate.test(new MemberBuilder().withTodayAttendance(true).build()));
    }
}
