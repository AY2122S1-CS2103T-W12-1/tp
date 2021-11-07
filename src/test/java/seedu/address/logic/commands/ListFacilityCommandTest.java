package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFacilityAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPaEmptyFacilityList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListFacilityCommand.
 */
public class ListFacilityCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
    }

    @Test
    public void execute_facilityListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListFacilityCommand(), model, ListFacilityCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_facilityListIsFiltered_showsEverything() {
        showFacilityAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListFacilityCommand(), model, ListFacilityCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyFacilityList_failure() {
        Model model = new ModelManager(getTypicalSportsPaEmptyFacilityList(), new UserPrefs());
        ListFacilityCommand command = new ListFacilityCommand();
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
    }
}
