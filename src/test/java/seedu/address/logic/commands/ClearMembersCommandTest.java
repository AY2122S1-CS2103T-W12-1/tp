package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPaEmptyMemberList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearMembersCommandTest {

    @Test
    public void execute_nonEmptySportsPa_success() {
        Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSportsPaEmptyMemberList(), new UserPrefs());

        assertCommandSuccess(new ClearMembersCommand(), model, ClearMembersCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyMemberList_failure() {
        Model model = new ModelManager(getTypicalSportsPaEmptyMemberList(), new UserPrefs());

        ClearAttendanceCommand command = new ClearAttendanceCommand();
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
    }

}
