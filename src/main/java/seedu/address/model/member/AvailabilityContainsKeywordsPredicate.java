package seedu.address.model.member;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Availability} matches any of the availability keywords given.
 */
public class AvailabilityContainsKeywordsPredicate implements Predicate<Member> {
    private final List<Availability> availability;

    public AvailabilityContainsKeywordsPredicate(List<Availability> availability) {
        this.availability = availability;
    }
    @Override
    public boolean test(Member member) {
        return availability.stream()
                .anyMatch(availability -> member.getAvailability().containsAll(availability));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AvailabilityContainsKeywordsPredicate
                && availability.equals(((AvailabilityContainsKeywordsPredicate) other).availability));
    }
}
