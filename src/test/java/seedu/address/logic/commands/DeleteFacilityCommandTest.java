package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFacilityAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facility.Facility;

/**
 * Contains integration tests (interaction with the Mode) and unit tests for {@code DeleteFacilityCommand}.
 */
public class DeleteFacilityCommandTest {

    private Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Facility facilityToDelete = model.getFilteredFacilityList().get(INDEX_FIRST.getZeroBased());
        DeleteFacilityCommand command = new DeleteFacilityCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteFacilityCommand.MESSAGE_DELETE_FACILITY_SUCCESS, facilityToDelete);

        ModelManager expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.deleteFacility(facilityToDelete);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFacilityList().size() + 1);
        DeleteFacilityCommand command = new DeleteFacilityCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_FACILITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFacilityAtIndex(model, INDEX_FIRST);

        Facility facilityToDelete = model.getFilteredFacilityList().get(INDEX_FIRST.getZeroBased());
        DeleteFacilityCommand command = new DeleteFacilityCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteFacilityCommand.MESSAGE_DELETE_FACILITY_SUCCESS, facilityToDelete);

        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.deleteFacility(facilityToDelete);
        showNoFacility(expectedModel);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFacilityAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex still in bounds of SportsPA facility list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSportsPa().getFacilityList().size());

        DeleteFacilityCommand command = new DeleteFacilityCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_FACILITY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteFacilityCommand deleteFacilFirstCommand = new DeleteFacilityCommand(INDEX_FIRST);
        DeleteFacilityCommand deleteFacilSecondCommand = new DeleteFacilityCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFacilFirstCommand.equals(deleteFacilFirstCommand));

        // same values -> returns true
        DeleteFacilityCommand deleteFacilFirstCommandCopy = new DeleteFacilityCommand(INDEX_FIRST);
        assertTrue(deleteFacilFirstCommand.equals(deleteFacilFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFacilFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFacilFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(deleteFacilFirstCommand.equals(deleteFacilSecondCommand));
    }

    @Test
    public void execute_emptyFacilityList_failure() {
        showNoFacility(model);
        DeleteFacilityCommand command = new DeleteFacilityCommand(INDEX_FIRST);
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
    }

    /**
     * Updates {@code model}'s filtered facility list to show no one.
     */
    private void showNoFacility(Model model) {
        model.updateFilteredFacilityList(f -> false);

        assertTrue(model.getFilteredFacilityList().isEmpty());
    }
}
