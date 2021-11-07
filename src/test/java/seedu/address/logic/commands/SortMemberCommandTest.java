package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPaEmptyMemberList;
import static seedu.address.testutil.TypicalSportsPa.getUnsortedNameSportsPa;
import static seedu.address.testutil.TypicalSportsPa.getUnsortedTagSportsPa;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.sort.SortOrder;

public class SortMemberCommandTest {

    private SortOrder byName = new SortOrder("name");
    private SortOrder byTag = new SortOrder("tag");

    @Test
    public void equals() {
        SortOrder byName = new SortOrder("name");
        SortOrder byTag = new SortOrder("tag");

        SortMemberCommand sortByNameCommand = new SortMemberCommand(byName);
        SortMemberCommand sortByTagCommand = new SortMemberCommand(byTag);

        // same object -> returns true
        assertTrue(sortByNameCommand.equals(sortByNameCommand));

        // same values -> returns true
        SortMemberCommand sortByTagCommandCopy = new SortMemberCommand(byTag);
        assertTrue(sortByTagCommand.equals(sortByTagCommandCopy));

        // different types -> returns false
        assertFalse(sortByNameCommand.equals(1));

        // null -> returns false
        assertFalse(sortByNameCommand.equals(null));

        // different sort order -> returns false
        assertFalse(sortByNameCommand.equals(sortByTagCommand));
    }

    @Test
    public void execute_unsortedListIsNotFiltered_isSortedByName() {
        Model model = new ModelManager(getUnsortedNameSportsPa(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        SortMemberCommand command = new SortMemberCommand(byName);
        expectedModel.sortMemberList(byName);
        expectedModel.updateFilteredMemberList(Model.PREDICATE_SHOW_ALL_MEMBERS);
        String expectedMessage = String.format(SortMemberCommand.MESSAGE_SUCCESS, byName);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unsortedListIsNotFiltered_isSortedByTag() {
        Model model = new ModelManager(getUnsortedTagSportsPa(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        SortMemberCommand command = new SortMemberCommand(byTag);
        expectedModel.sortMemberList(byTag);
        expectedModel.updateFilteredMemberList(Model.PREDICATE_SHOW_ALL_MEMBERS);
        String expectedMessage = String.format(SortMemberCommand.MESSAGE_SUCCESS, byTag);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyMemberList_failure() {
        Model model = new ModelManager(getTypicalSportsPaEmptyMemberList(), new UserPrefs());
        SortMemberCommand command = new SortMemberCommand(byName);
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
    }
}
