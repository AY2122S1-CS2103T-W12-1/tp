package seedu.address.model.facility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilities.KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1;
import static seedu.address.testutil.TypicalFacilities.KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_10;
import static seedu.address.testutil.TypicalFacilities.KENT_RIDGE_SPORT_HALL_5_COURT_1;
import static seedu.address.testutil.TypicalFacilities.TAMPINES_HUB_FIELD_SECTION_B;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.EnumMap;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.facility.exceptions.DuplicateFacilityException;
import seedu.address.model.facility.exceptions.FacilityNotFoundException;
import seedu.address.model.member.Member;
import seedu.address.testutil.FacilityBuilder;
import seedu.address.testutil.MemberBuilder;

public class UniqueFacilityListTest {
    public final UniqueFacilityList uniqueFacilityList = new UniqueFacilityList();
    public final Facility facility = new Facility(
            new FacilityName("Court 1"),
            new Location("Kent Ridge Sports Hall"),
            new Time("1300"),
            new Capacity("5"),
            new AllocationMap(new EnumMap<>(DayOfWeek.class)));

    @Test
    public void add_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> uniqueFacilityList.add(null));
    }

    @Test
    public void setFacility_replaceExistingFacility_success() {
        uniqueFacilityList.add(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1);
        uniqueFacilityList.add(TAMPINES_HUB_FIELD_SECTION_B);

        Facility editedFacility = KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_10;

        UniqueFacilityList expectedList = new UniqueFacilityList();
        expectedList.add(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_10);
        expectedList.add(TAMPINES_HUB_FIELD_SECTION_B);

        uniqueFacilityList.replaceFacility(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1, editedFacility);
        assertEquals(expectedList, uniqueFacilityList);

    }

    @Test
    public void allocateMembersToFacilities_addsMembersToFacilities_success() {
        uniqueFacilityList.add(new FacilityBuilder(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1).build());
        uniqueFacilityList.add(new FacilityBuilder(TAMPINES_HUB_FIELD_SECTION_B).build());

        ObservableList<Member> members = FXCollections.observableArrayList();
        Member firstMember = new MemberBuilder().withAvailability(Arrays.asList(DayOfWeek.MONDAY,
                DayOfWeek.FRIDAY)).build();
        Member secondMember = new MemberBuilder().withAvailability(Arrays.asList(DayOfWeek.MONDAY,
                DayOfWeek.THURSDAY)).build();
        members.add(firstMember);
        members.add(secondMember);

        Facility updatedFacility = new FacilityBuilder(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1).build();
        updatedFacility.addMemberToFacilityOnDay(firstMember, DayOfWeek.of(1));
        updatedFacility.addMemberToFacilityOnDay(secondMember, DayOfWeek.of(1));
        UniqueFacilityList expected = new UniqueFacilityList();
        expected.setFacilities(Arrays.asList(updatedFacility, TAMPINES_HUB_FIELD_SECTION_B));

        FilteredList<Member> toAllocate = new FilteredList<>(members);
        uniqueFacilityList.allocateMembersToFacilitiesOnDay(toAllocate, 1);
        assertEquals(expected, uniqueFacilityList);
    }

    @Test
    public void replaceFacility_replaceNonExistentFacility_exceptionThrown() {
        uniqueFacilityList.add(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1);
        uniqueFacilityList.add(TAMPINES_HUB_FIELD_SECTION_B);

        Facility editedFacility = KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_10;

        assertThrows(FacilityNotFoundException.class, () -> uniqueFacilityList
                .replaceFacility(KENT_RIDGE_SPORT_HALL_5_COURT_1, editedFacility));
    }

    @Test
    public void remove_nullFacility_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFacilityList.remove(null));
    }

    @Test
    public void remove_facilityDoesNotExist_throwsFacilityNotFoundException() {
        assertThrows(FacilityNotFoundException.class, () -> uniqueFacilityList.remove(facility));
    }

    @Test
    public void remove_existingFacility_removesFacility() {
        uniqueFacilityList.add(facility);
        uniqueFacilityList.remove(facility);
    }

    @Test
    public void resetFacilities_clearsFacilityList() {
        uniqueFacilityList.add(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1);
        uniqueFacilityList.resetFacilities();
        UniqueFacilityList expectedUniqueFacilityList = new UniqueFacilityList();
        assertEquals(expectedUniqueFacilityList, uniqueFacilityList);
    }

    @Test
    public void setFacility_nullTargetFacility_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFacilityList
                .setFacility(null, KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1));
    }

    @Test
    public void setFacility_nullEditedFacility_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueFacilityList
                .setFacility(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1, null));
    }

    @Test
    public void setFacility_targetFacilityNotInList_throwsFacilityNotFoundException() {
        assertThrows(FacilityNotFoundException.class, () -> uniqueFacilityList
                .setFacility(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1, KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1));
    }

    @Test
    public void setFacility_editedFacilityIsSameFacility_success() {
        uniqueFacilityList.add(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1);
        uniqueFacilityList
                .setFacility(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1, KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1);
        UniqueFacilityList expectedUniqueFacilityList = new UniqueFacilityList();
        expectedUniqueFacilityList.add(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1);
        assertEquals(expectedUniqueFacilityList, uniqueFacilityList);
    }

    @Test
    public void setFacility_editedFacilityHasSameParameter_success() {
        uniqueFacilityList.add(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1);
        Facility editedFacil = new FacilityBuilder(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1).build();
        uniqueFacilityList.setFacility(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1, editedFacil);
        UniqueFacilityList expectedUniqueFacilityList = new UniqueFacilityList();
        expectedUniqueFacilityList.add(editedFacil);
        assertEquals(expectedUniqueFacilityList, uniqueFacilityList);
    }

    @Test
    public void setFacility_editedFacilityHasDifferentParameter_success() {
        uniqueFacilityList.add(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1);
        uniqueFacilityList
                .setFacility(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1, KENT_RIDGE_SPORT_HALL_5_COURT_1);
        UniqueFacilityList expectedUniqueFacilityList = new UniqueFacilityList();
        expectedUniqueFacilityList.add(KENT_RIDGE_SPORT_HALL_5_COURT_1);
        assertEquals(expectedUniqueFacilityList, uniqueFacilityList);
    }

    @Test
    public void setFacility_editedFacilityHasNonUniqueParameter_throwsDuplicateFacilityException() {
        uniqueFacilityList.add(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1);
        uniqueFacilityList.add(KENT_RIDGE_SPORT_HALL_5_COURT_1);
        assertThrows(DuplicateFacilityException.class, () -> uniqueFacilityList
                .setFacility(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1, KENT_RIDGE_SPORT_HALL_5_COURT_1));
    }
}
