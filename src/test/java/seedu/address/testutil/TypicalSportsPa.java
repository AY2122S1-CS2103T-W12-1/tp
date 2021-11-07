package seedu.address.testutil;

import static seedu.address.testutil.TypicalFacilities.getTypicalFacilities;
import static seedu.address.testutil.TypicalMembers.getTypicalMembers;
import static seedu.address.testutil.TypicalMembers.getTypicalMembersToFind;
import static seedu.address.testutil.TypicalMembers.getTypicalMembersUnsortedName;
import static seedu.address.testutil.TypicalMembers.getTypicalMembersUnsortedTag;

import seedu.address.model.SportsPa;
import seedu.address.model.facility.Facility;
import seedu.address.model.member.Member;

public class TypicalSportsPa {
    private TypicalSportsPa() {} // Prevents instantiation

    /**
     * Returns an {@code SportsPa} with all the typical members and facilities.
     */
    public static SportsPa getTypicalSportsPa() {
        SportsPa ab = new SportsPa();
        for (Member member : getTypicalMembers()) {
            ab.addMember(member);
        }
        for (Facility facility: getTypicalFacilities()) {
            ab.addFacility(facility);
        }
        return ab;
    }

    /**
     * Returns an {@code SportsPa} with all the typical members and facilities.
     */
    public static SportsPa getTypicalSportsPaToFind() {
        SportsPa ab = new SportsPa();
        for (Member member : getTypicalMembersToFind()) {
            ab.addMember(member);
        }
        for (Facility facility: getTypicalFacilities()) {
            ab.addFacility(facility);
        }
        return ab;
    }

    /**
     * Returns an unsorted {@code SportsPa} with all the typical members and facilities.
     */
    public static SportsPa getUnsortedNameSportsPa() {
        SportsPa ab = new SportsPa();
        for (Member member : getTypicalMembersUnsortedName()) {
            ab.addMember(member);
        }
        for (Facility facility: getTypicalFacilities()) {
            ab.addFacility(facility);
        }
        return ab;
    }

    /**
     * Returns an unsorted {@code SportsPa} with all the typical members and facilities.
     */
    public static SportsPa getUnsortedTagSportsPa() {
        SportsPa ab = new SportsPa();
        for (Member member : getTypicalMembersUnsortedTag()) {
            ab.addMember(member);
        }
        for (Facility facility: getTypicalFacilities()) {
            ab.addFacility(facility);
        }
        return ab;
    }

    /**
     * Returns an {@code SportsPa} with all the typical members and no facilities.
     */
    public static SportsPa getTypicalSportsPaEmptyFacilityList() {
        SportsPa ab = new SportsPa();
        for (Member member : getTypicalMembers()) {
            ab.addMember(member);
        }
        return ab;
    }

    /**
     * Returns an {@code SportsPa} with all the typical facilities and no members.
     */
    public static SportsPa getTypicalSportsPaEmptyMemberList() {
        SportsPa ab = new SportsPa();
        for (Facility facility: getTypicalFacilities()) {
            ab.addFacility(facility);
        }
        return ab;
    }
}
