package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EXCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_Y2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MemberBuilder;

public class TagsContainKeywordsPredicateTest {
    private final List<Tag> firstPredicateKeywordList = Arrays.asList(new Tag(VALID_TAG_EXCO), new Tag(VALID_TAG_Y2));
    private final List<Tag> secondPredicateKeywordList = Arrays.asList(new Tag(VALID_TAG_EXCO));

    @Test
    public void equals() {
        TagsContainKeywordsPredicate firstPredicate = new TagsContainKeywordsPredicate(firstPredicateKeywordList);
        TagsContainKeywordsPredicate secondPredicate = new TagsContainKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainKeywordsPredicate firstPredicateCopy = new TagsContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainKeywords_returnsTrue() {
        // one keyword
        TagsContainKeywordsPredicate predicate = new TagsContainKeywordsPredicate(secondPredicateKeywordList);
        assertTrue(predicate.test(new MemberBuilder().withTags(VALID_TAG_EXCO).build()));

        // only one matching keyword
        predicate = new TagsContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(predicate.test(new MemberBuilder().withTags(VALID_TAG_EXCO).build()));

        // multiple keywords
        predicate = new TagsContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(predicate.test(new MemberBuilder().withTags(VALID_TAG_EXCO, VALID_TAG_Y2).build()));

        // mixed-case keywords
        predicate = new TagsContainKeywordsPredicate(Collections.singletonList(new Tag("exCo")));
        assertTrue(predicate.test(new MemberBuilder().withTags(VALID_TAG_EXCO).build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // zero keywords
        TagsContainKeywordsPredicate predicate = new TagsContainKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MemberBuilder().withTags(VALID_TAG_EXCO).build()));

        // non-matching keywords
        predicate = new TagsContainKeywordsPredicate(secondPredicateKeywordList);
        assertFalse(predicate.test(new MemberBuilder().withTags(VALID_TAG_Y2).build()));
    }
}
