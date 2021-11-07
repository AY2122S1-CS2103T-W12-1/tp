package seedu.address.model.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MemberBuilder;

public class MemberAvailableOnDayPredicateTest {

    @Test
    public void test_personAvailableOnDay_returnsTrue() {
        List<DayOfWeek> availability = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
        Member member = new MemberBuilder().withAvailability(availability).build();
        MemberAvailableOnDayPredicate predicate = new MemberAvailableOnDayPredicate(1);
        assertTrue(predicate.test(member));
    }

    @Test
    public void test_personNotAvailableOnDay_returnsFalse() {
        List<DayOfWeek> availability = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
        Member member = new MemberBuilder().withAvailability(availability).build();
        MemberAvailableOnDayPredicate predicate = new MemberAvailableOnDayPredicate(3);
        assertFalse(predicate.test(member));
    }
}
