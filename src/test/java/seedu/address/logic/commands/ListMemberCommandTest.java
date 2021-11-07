package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPaEmptyMemberList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListMemberCommand.
 */
public class ListMemberCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListMemberCommand(), model, ListMemberCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListMemberCommand(), model, ListMemberCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyMemberList_failure() {
        Model model = new ModelManager(getTypicalSportsPaEmptyMemberList(), new UserPrefs());
        ListMemberCommand command = new ListMemberCommand();
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
    }
}
