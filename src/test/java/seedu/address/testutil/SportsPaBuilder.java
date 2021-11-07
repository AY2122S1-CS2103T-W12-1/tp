package seedu.address.testutil;

import seedu.address.model.SportsPa;
import seedu.address.model.facility.Facility;
import seedu.address.model.member.Member;

/**
 * A utility class to help with building SportsPa objects.
 * Example usage: <br>
 *     {@code SportsPa ab = new SportsPaBuilder().withMember("John", "Doe").build();}
 */
public class SportsPaBuilder {

    private SportsPa sportsPa;

    public SportsPaBuilder() {
        sportsPa = new SportsPa();
    }

    public SportsPaBuilder(SportsPa sportsPa) {
        this.sportsPa = sportsPa;
    }

    /**
     * Adds a new {@code Member} to the {@code SportsPa} that we are building.
     */
    public SportsPaBuilder withMember(Member member) {
        sportsPa.addMember(member);
        return this;
    }

    /**
     * Adds a new {@code Facility} to the {@code SportsPa} that we are building.
     */
    public SportsPaBuilder withFacility(Facility facility) {
        sportsPa.addFacility(facility);
        return this;
    }

    public SportsPa build() {
        return sportsPa;
    }
}
