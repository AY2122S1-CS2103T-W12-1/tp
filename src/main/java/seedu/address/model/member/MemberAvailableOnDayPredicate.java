package seedu.address.model.member;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person} is available on the given day.
 */
public class MemberAvailableOnDayPredicate implements Predicate<Member> {
    private final int dayNumber;

    public MemberAvailableOnDayPredicate(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    @Override
    public boolean test(Member member) {
        return member.isAvailableOnDay(dayNumber);
    }
}
