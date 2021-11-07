package seedu.address.model.member;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Represents a Person's availability in the address book.
 * Guarantees: immutable; is always valid
 */
public class Availability {

    public static final String MESSAGE_CONSTRAINTS =
            "Availability should be given as a list of numbers from 1 to 7, separated by a space each, "
            + "where 1 represents Monday, 2 represents Tuesday... and 7 represents Sunday";

    public static final String VALIDATION_REGEX = "[1-7]"; // numbers from 1 to 7

    public final List<DayOfWeek> values;

    /**
     * Constructs an {@code Availability}.
     *
     * @param availability A valid availability string.
     */
    public Availability(List<DayOfWeek> availability) {
        requireNonNull(availability);
        values = availability;
    }

    /**
     * Returns true if a given availability list is valid.
     */
    public static boolean isValidAvailability(List<String> test) {
        if (test.get(0).isEmpty()) { // empty but valid
            return true;
        }
        for (String s : test) {
            if (!s.matches(VALIDATION_REGEX)) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(DayOfWeek day) {
        return values.contains(day);
    }

    public boolean containsAll(Availability availability) {
        return this.values.containsAll(availability.values);
    }
    /**
     * Returns true if availability list is empty.
     */
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public String toString() {
        return values.stream().map(dayOfWeek -> dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()))
                .collect(Collectors.joining(" "));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Availability // instanceof handles nulls
                && values.equals(((Availability) other).values)); // state check
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }
}
