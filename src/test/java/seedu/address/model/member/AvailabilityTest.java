package seedu.address.model.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

@SuppressWarnings("checkstyle:Regexp")
public class AvailabilityTest {
    private final Availability emptyAvailability = new Availability(new ArrayList<>());
    private final Availability nonEmptyAvailability = new Availability(new ArrayList<>(Arrays.asList
            (DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Availability(null));
    }

    @Test
    public void isValidAvailability() {
        // null availability
        assertThrows(NullPointerException.class, () -> Availability.isValidAvailability(null));

        // invalid availabilities
        assertFalse(Availability.isValidAvailability(Arrays.asList("one", "two"))); // not integer
        assertFalse(Availability.isValidAvailability(Arrays.asList("0", "8"))); // out of range

        // valid availabilities
        assertTrue(Availability.isValidAvailability(Arrays.asList("1", "2")));
        assertTrue(Availability.isValidAvailability(Arrays.asList("")));
        assertTrue(Availability.isValidAvailability(Arrays.asList("1", "2", "3", "4", "5", "6", "7")));
    }

    @Test
    public void isEmpty() {
        Availability nullAvailability = null;
        // null availability
        assertThrows(NullPointerException.class, () -> nullAvailability.isEmpty());
        // empty availability list
        assertTrue(emptyAvailability.isEmpty());
        // non-empty availability list
        assertFalse(nonEmptyAvailability.isEmpty());
    }
}
