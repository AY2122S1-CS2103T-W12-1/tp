package seedu.address.model.member;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MemberBuilder;

public class PhoneContainsKeywordsPredicateTest {
    private final List<Phone> firstPredicateKeywordList = Collections.singletonList(new Phone(VALID_PHONE_AMY));
    private final List<Phone> secondPredicateKeywordList = Collections.singletonList(new Phone(VALID_PHONE_BOB));
    @Test
    public void equals() {
        PhoneContainsKeywordsPredicate firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy = new PhoneContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // matching keyword
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(predicate.test(new MemberBuilder().withPhone(VALID_PHONE_AMY).build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // zero keywords
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MemberBuilder().withPhone(VALID_PHONE_BOB).build()));

        // non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertFalse(predicate.test(new MemberBuilder().withPhone(VALID_PHONE_BOB).build()));
    }
}
