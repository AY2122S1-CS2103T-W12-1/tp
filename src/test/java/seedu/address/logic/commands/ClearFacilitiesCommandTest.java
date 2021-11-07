package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPaEmptyFacilityList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearFacilitiesCommandTest {

    @Test
    public void execute_nonEmptySportsPA_success() {
        Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSportsPaEmptyFacilityList(), new UserPrefs());
        assertCommandSuccess(
                new ClearFacilitiesCommand(), model, ClearFacilitiesCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyFacilityList_failure() {
        Model model = new ModelManager(getTypicalSportsPaEmptyFacilityList(), new UserPrefs());

        ClearFacilitiesCommand command = new ClearFacilitiesCommand();
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
    }
}
