package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getUnsortedNameAddressBook;
import static seedu.address.testutil.TypicalAddressBook.getUnsortedTagAddressBook;

import org.junit.jupiter.api.Test;

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
        Model model = new ModelManager(getUnsortedNameAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        SortMemberCommand command = new SortMemberCommand(byName);
        expectedModel.sortMemberList(byName);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        String expectedMessage = String.format(SortMemberCommand.MESSAGE_SUCCESS, byName);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unsortedListIsNotFiltered_isSortedByTag() {
        Model model = new ModelManager(getUnsortedTagAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        SortMemberCommand command = new SortMemberCommand(byTag);
        expectedModel.sortMemberList(byTag);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        String expectedMessage = String.format(SortMemberCommand.MESSAGE_SUCCESS, byTag);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
