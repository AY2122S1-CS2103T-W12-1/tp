package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFacilityAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilities.TAMPINES_HUB_FIELD_SECTION_B;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalMembers.AMY;
import static seedu.address.testutil.TypicalMembers.BOB;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SportsPa;
import seedu.address.model.UserPrefs;
import seedu.address.model.facility.Facility;
import seedu.address.model.member.Member;
import seedu.address.testutil.FacilityBuilder;

public class AllocateMemberCommandTest {
    @Test
    public void constructor_null_exceptionThrown() {
        // null member index
        assertThrows(NullPointerException.class, () ->
                new AllocateMemberCommand(null, INDEX_FIRST, DayOfWeek.MONDAY));

        // null facility index
        assertThrows(NullPointerException.class, () ->
                new AllocateMemberCommand(INDEX_FIRST, null, DayOfWeek.MONDAY));

        // null day
        assertThrows(NullPointerException.class, () ->
                new AllocateMemberCommand(INDEX_FIRST, INDEX_FIRST, null));
    }

    @Test
    public void execute_allocateMemberNotMaxCapacity_successful() {
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        model.addMember(AMY);
        Facility facility = new FacilityBuilder(TAMPINES_HUB_FIELD_SECTION_B).build();
        model.addFacility(facility);

        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        Map<DayOfWeek, List<Member>> expectedMap = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            expectedMap.put(day, new ArrayList<>());
        }
        expectedMap.put(DayOfWeek.MONDAY, List.of(AMY));
        Facility facilityAfterAllocation = new FacilityBuilder(facility).withAllocationMap(expectedMap).build();
        expectedModel.setFacility(facility, facilityAfterAllocation);

        AllocateMemberCommand command = new AllocateMemberCommand(INDEX_FIRST, INDEX_FIRST, DayOfWeek.MONDAY);
        String expectedMessage = String.format(AllocateMemberCommand.MESSAGE_SUCCESS,
                AMY.getName(), facilityAfterAllocation.getName(), "Monday");
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allocateMemberMaxCapacity_failure() {
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        model.addMember(AMY);
        model.addMember(BOB);
        Facility facility = new FacilityBuilder(TAMPINES_HUB_FIELD_SECTION_B).withCapacity("1").build();
        facility.addMemberToFacilityOnDay(AMY, DayOfWeek.MONDAY);
        model.addFacility(facility);

        AllocateMemberCommand command = new AllocateMemberCommand(INDEX_SECOND, INDEX_FIRST, DayOfWeek.MONDAY);
        assertCommandFailure(command, model, Messages.MESSAGE_FACILITY_AT_MAX_CAPACITY);
    }

    @Test
    public void execute_allocateDuplicateMemberSameDay_failure() {
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        model.addMember(AMY);
        Facility facility = new FacilityBuilder(TAMPINES_HUB_FIELD_SECTION_B).build();
        facility.addMemberToFacilityOnDay(AMY, DayOfWeek.MONDAY);
        model.addFacility(facility);

        AllocateMemberCommand command = new AllocateMemberCommand(INDEX_FIRST, INDEX_FIRST, DayOfWeek.MONDAY);
        assertCommandFailure(command, model, Messages.MESSAGE_MEMBER_ALREADY_ALLOCATED);
    }

    @Test
    public void execute_allocateMemberNotAvailable_failure() {
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        model.addMember(AMY);
        Facility facility = new FacilityBuilder(TAMPINES_HUB_FIELD_SECTION_B).build();
        model.addFacility(facility);

        AllocateMemberCommand command = new AllocateMemberCommand(INDEX_FIRST, INDEX_FIRST, DayOfWeek.SUNDAY);
        assertCommandFailure(command, model, Messages.MESSAGE_MEMBER_NOT_AVAILABLE);
    }

    @Test
    public void execute_invalidMemberIndex_failure() {
        Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSportsPa().getMemberList().size());

        AllocateMemberCommand command = new AllocateMemberCommand(outOfBoundIndex, INDEX_FIRST, DayOfWeek.SUNDAY);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidFacilityIndex_failure() {
        Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        showFacilityAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSportsPa().getFacilityList().size());

        AllocateMemberCommand command = new AllocateMemberCommand(INDEX_FIRST, outOfBoundIndex, DayOfWeek.SUNDAY);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_FACILITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyMemberList_failure() {
        Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        model.updateFilteredMemberList(Model.PREDICATE_SHOW_NO_MEMBERS);

        AllocateMemberCommand command = new AllocateMemberCommand(INDEX_FIRST, INDEX_FIRST, DayOfWeek.SUNDAY);
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
    }

    @Test
    public void execute_emptyFacilityList_failure() {
        Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        model.updateFilteredFacilityList(Model.PREDICATE_SHOW_NO_FACILITIES);

        AllocateMemberCommand command = new AllocateMemberCommand(INDEX_FIRST, INDEX_FIRST, DayOfWeek.SUNDAY);
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
    }
}
