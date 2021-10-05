package seedu.address.model.facility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;


public class FacilityTest {

    @Test
    public void constructor_null_throwsException() {
        assertThrows(NullPointerException.class, () -> new Facility(null, new Location("loc"),
                new Time("time"), new Capacity("5")));
        assertThrows(NullPointerException.class, () -> new Facility(new FacilityName("Court"), null,
                new Time("time"), new Capacity("5")));
        assertThrows(NullPointerException.class, () -> new Facility(new FacilityName("Court"), new Location("loc"),
                null, new Capacity("5")));
        assertThrows(NullPointerException.class, () -> new Facility(new FacilityName("Court"), new Location("loc"),
                new Time("time"), null));
    }
    @Test
    public void equals() {
        Facility facility = new Facility(new FacilityName("Court 1"), new Location("University Sports Hall"),
                new Time("11:30"), new Capacity("10"));

        Facility facilityCopy = new Facility(new FacilityName("Court 1"), new Location("University Sports Hall"),
                new Time("11:30"), new Capacity("10"));

        Facility differentFacility = new Facility(new FacilityName("Field"), new Location("Opp University Hall"),
                new Time("20:45"), new Capacity("5"));

        assertTrue(facility.equals(facility));

        assertTrue(facility.equals(facilityCopy));

        assertFalse(facility.equals(null));

        assertFalse(facility.equals("5"));

        assertFalse(facility.equals(differentFacility));

        Facility differentName = new Facility(new FacilityName("Field"), new Location("University Sports Hall"),
                new Time("11:30"), new Capacity("10"));
        assertFalse(facility.equals(differentName));

        Facility differentLocation = new Facility(new FacilityName("Court 1"), new Location("Somewhere"),
                new Time("11:30"), new Capacity("10"));
        assertFalse(facility.equals(differentLocation));

        Facility differentTime = new Facility(new FacilityName("Court 1"), new Location("University Sports Hall"),
                new Time("20:30"), new Capacity("10"));
        assertFalse(facility.equals(differentTime));

        Facility differentCapacity = new Facility(new FacilityName("Court 1"), new Location("University Sports Hall"),
                new Time("11:30"), new Capacity("8"));
        assertFalse(facility.equals(differentCapacity));
    }
}
