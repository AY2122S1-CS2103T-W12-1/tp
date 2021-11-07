package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACILITY_NAME_COURT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACILITY_NAME_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_COURT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFacilityAtIndex;
import static seedu.address.testutil.TypicalFacilities.TAMPINES_HUB_FIELD_SECTION_B;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalMembers.AMY;
import static seedu.address.testutil.TypicalMembers.BOB;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditFacilityCommand.EditFacilityDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SportsPa;
import seedu.address.model.UserPrefs;
import seedu.address.model.facility.Facility;
import seedu.address.testutil.EditFacilityDescriptorBuilder;
import seedu.address.testutil.FacilityBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditFacilityCommand.
 */
public class EditFacilityCommandTest {

    private Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Facility editedFacility = new FacilityBuilder().build();

        EditFacilityDescriptor descriptor = new EditFacilityDescriptorBuilder(editedFacility).build();
        EditFacilityCommand command = new EditFacilityCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditFacilityCommand.MESSAGE_EDIT_FACILITY_SUCCESS, editedFacility);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());
        expectedModel.setFacility(model.getFilteredFacilityList().get(0), editedFacility);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastFacility = Index.fromOneBased(model.getFilteredFacilityList().size());
        Facility lastFacility = model.getFilteredFacilityList().get(indexLastFacility.getZeroBased());

        FacilityBuilder facilityInList = new FacilityBuilder(lastFacility);
        Facility editedFacility = facilityInList.withFacilityName(VALID_FACILITY_NAME_COURT)
                .withLocation(VALID_LOCATION_COURT).build();

        EditFacilityDescriptor descriptor =
                new EditFacilityDescriptorBuilder().withFacilityName(VALID_FACILITY_NAME_COURT)
                        .withLocation(VALID_LOCATION_COURT).build();
        EditFacilityCommand command = new EditFacilityCommand(indexLastFacility, descriptor);

        String expectedMessage = String.format(EditFacilityCommand.MESSAGE_EDIT_FACILITY_SUCCESS, editedFacility);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());
        expectedModel.setFacility(lastFacility, editedFacility);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditFacilityCommand command = new EditFacilityCommand(INDEX_FIRST, new EditFacilityDescriptor());
        Facility editedFacility = model.getFilteredFacilityList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditFacilityCommand.MESSAGE_EDIT_FACILITY_SUCCESS, editedFacility);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showFacilityAtIndex(model, INDEX_FIRST);

        Facility facilityInFilteredList = model.getFilteredFacilityList().get(INDEX_FIRST.getZeroBased());
        Facility editedFacility = new FacilityBuilder(facilityInFilteredList)
                .withFacilityName(VALID_FACILITY_NAME_COURT).build();
        EditFacilityCommand command = new EditFacilityCommand(INDEX_FIRST,
                new EditFacilityDescriptorBuilder().withFacilityName(VALID_FACILITY_NAME_COURT).build());

        String expectedMessage = String.format(EditFacilityCommand.MESSAGE_EDIT_FACILITY_SUCCESS, editedFacility);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());
        expectedModel.setFacility(model.getFilteredFacilityList().get(0), editedFacility);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListCapacityBelowNumberOfAllocations_clearsAllocation() {
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        Facility facility = new FacilityBuilder(TAMPINES_HUB_FIELD_SECTION_B).build();
        facility.addMemberToFacilityOnDay(AMY, DayOfWeek.MONDAY);
        facility.addMemberToFacilityOnDay(BOB, DayOfWeek.MONDAY);

        Facility editedFacility = new FacilityBuilder(facility)
                .withCapacity("1").build();
        editedFacility.removeMemberFromFacilityOnDay(AMY, DayOfWeek.MONDAY);
        editedFacility.removeMemberFromFacilityOnDay(BOB, DayOfWeek.MONDAY);

        model.addMember(AMY);
        model.addMember(BOB);

        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.addFacility(editedFacility);

        model.addFacility(facility);

        EditFacilityDescriptor descriptor =
                new EditFacilityDescriptorBuilder(facility).withCapacity("1").build();
        EditFacilityCommand command = new EditFacilityCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditFacilityCommand.MESSAGE_EDIT_FACILITY_SUCCESS, editedFacility);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListCapacityEqualNumberOfAllocations_doesNotClearAllocation() {
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        Facility facility = new FacilityBuilder(TAMPINES_HUB_FIELD_SECTION_B).build();
        facility.addMemberToFacilityOnDay(AMY, DayOfWeek.MONDAY);
        facility.addMemberToFacilityOnDay(BOB, DayOfWeek.MONDAY);

        Facility editedFacility = new FacilityBuilder(facility)
                .withCapacity("2").build();

        model.addMember(AMY);
        model.addMember(BOB);

        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.addFacility(editedFacility);

        model.addFacility(facility);

        EditFacilityDescriptor descriptor =
                new EditFacilityDescriptorBuilder(facility).withCapacity("2").build();
        EditFacilityCommand command = new EditFacilityCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditFacilityCommand.MESSAGE_EDIT_FACILITY_SUCCESS, editedFacility);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateFacilityUnfilteredList_failure() {
        Facility firstFacility = model.getFilteredFacilityList().get(INDEX_FIRST.getZeroBased());
        EditFacilityDescriptor descriptor = new EditFacilityDescriptorBuilder(firstFacility).build();
        EditFacilityCommand command = new EditFacilityCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(command, model, EditFacilityCommand.MESSAGE_DUPLICATE_FACILITY);
    }

    @Test
    public void execute_duplicateFacilityFilteredList() {
        showFacilityAtIndex(model, INDEX_FIRST);

        Facility facilityInList = model.getSportsPa().getFacilityList().get(INDEX_SECOND.getZeroBased());
        EditFacilityCommand command = new EditFacilityCommand(INDEX_FIRST,
                new EditFacilityDescriptorBuilder(facilityInList).build());

        assertCommandFailure(command, model, EditFacilityCommand.MESSAGE_DUPLICATE_FACILITY);
    }

    @Test
    public void execute_invalidFacilityIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredFacilityList().size() + 1);
        EditFacilityDescriptor descriptor = new EditFacilityDescriptorBuilder()
                .withFacilityName(VALID_FACILITY_NAME_COURT).build();
        EditFacilityCommand command = new EditFacilityCommand(outOfBoundsIndex, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_FACILITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidFacilityIndexFilteredList_failure() {
        showFacilityAtIndex(model, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getSportsPa().getFacilityList().size());

        EditFacilityDescriptor descriptor = new EditFacilityDescriptorBuilder()
                .withFacilityName(VALID_FACILITY_NAME_COURT).build();
        EditFacilityCommand command = new EditFacilityCommand(outOfBoundsIndex, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_FACILITY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditFacilityDescriptor desc1 = new EditFacilityDescriptorBuilder().withFacilityName(VALID_FACILITY_NAME_COURT)
                .withLocation(VALID_LOCATION_COURT).build();
        EditFacilityDescriptor desc2 = new EditFacilityDescriptorBuilder().withFacilityName(VALID_FACILITY_NAME_FIELD)
                .withLocation(VALID_LOCATION_FIELD).build();

        final EditFacilityCommand standardCommand = new EditFacilityCommand(INDEX_FIRST, desc1);

        // same values -> returns true
        EditFacilityDescriptor copyDescriptor = new EditFacilityDescriptor(desc1);
        EditFacilityCommand commandWithSameValues = new EditFacilityCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearMembersCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditFacilityCommand(INDEX_SECOND, desc1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditFacilityCommand(INDEX_FIRST, desc2)));
    }

    @Test
    public void execute_emptyFacilityList_failure() {
        Facility editedFacility = new FacilityBuilder().build();
        EditFacilityDescriptor descriptor = new EditFacilityDescriptorBuilder(editedFacility).build();
        model.updateFilteredFacilityList(Model.PREDICATE_SHOW_NO_FACILITIES);
        EditFacilityCommand command = new EditFacilityCommand(INDEX_FIRST, descriptor);
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
    }
}
