package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilities.FIELD;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPaEmptyFacilityList;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.facility.Facility;
import seedu.address.model.member.Member;
import seedu.address.model.member.exceptions.DuplicateMemberException;
import seedu.address.testutil.FacilityBuilder;
import seedu.address.testutil.MemberBuilder;

public class SportsPaTest {

    private final SportsPa sportsPa = new SportsPa();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), sportsPa.getMemberList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> sportsPa.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        SportsPa newData = getTypicalSportsPa();
        sportsPa.resetData(newData);
        assertEquals(newData, sportsPa);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Member editedAlice = new MemberBuilder(ALICE).build();
        List<Member> newMembers = Arrays.asList(ALICE, editedAlice);
        SportsPaStub newData = new SportsPaStub(newMembers);

        assertThrows(DuplicateMemberException.class, () -> sportsPa.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> sportsPa.hasMember(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(sportsPa.hasMember(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        sportsPa.addMember(ALICE);
        assertTrue(sportsPa.hasMember(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        sportsPa.addMember(ALICE);
        Member editedAlice = new MemberBuilder(ALICE).build();
        assertTrue(sportsPa.hasMember(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> sportsPa.getMemberList().remove(0));
    }

    @Test
    public void split_noPersonsInFilteredList_noChangesToFacilities() {
        ObservableList<Member> emptyPersonsList = FXCollections.emptyObservableList();
        FilteredList<Member> emptyList = new FilteredList<>(emptyPersonsList);
        ObservableList<Facility> facilities = sportsPa.getFacilityList();
        sportsPa.split(emptyList, 1);
        assertEquals(facilities, sportsPa.getFacilityList());
    }

    @Test
    public void split_personPresentInFilteredList_facilitiesUpdate() {
        SportsPa newData = new SportsPa(getTypicalSportsPaEmptyFacilityList());
        Facility firstFacility = new FacilityBuilder(FIELD).build();
        newData.addFacility(firstFacility);
        FilteredList<Member> filteredList = new FilteredList<>(newData.getMemberList());
        newData.split(filteredList, 1);

        Facility withAllocatedMembers = new FacilityBuilder(FIELD).build();
        for (Member member : filteredList) {
            withAllocatedMembers.addMemberToFacilityOnDay(member, DayOfWeek.of(1));
        }

        SportsPa expected = new SportsPa(getTypicalSportsPaEmptyFacilityList());
        expected.addFacility(withAllocatedMembers);

        assertEquals(expected, newData);
    }

    /**
     * A stub ReadOnlySportsPa whose persons list can violate interface constraints.
     */
    private static class SportsPaStub implements ReadOnlySportsPa {
        private final ObservableList<Member> members = FXCollections.observableArrayList();
        private final ObservableList<Facility> facilities = FXCollections.observableArrayList();

        SportsPaStub(Collection<Member> members) {
            this.members.setAll(members);
        }

        @Override
        public ObservableList<Member> getMemberList() {
            return members;
        }

        @Override
        public ObservableList<Facility> getFacilityList() {
            return facilities;
        }
    }

}
