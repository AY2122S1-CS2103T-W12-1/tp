package seedu.address.model.member;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the phone keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Member> {
    private final List<Phone> phone;

    public PhoneContainsKeywordsPredicate(List<Phone> phone) {
        this.phone = phone;
    }

    @Override
    public boolean test(Member member) {
        return phone.stream()
                .anyMatch(phone -> member.getPhone().equals(phone));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && phone.equals(((PhoneContainsKeywordsPredicate) other).phone)); // state check
    }
}
