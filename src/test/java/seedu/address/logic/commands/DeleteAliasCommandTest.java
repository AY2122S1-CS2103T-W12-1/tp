package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteAliasCommandTest {

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new DeleteAliasCommand(null));
    }

    @Test
    public void execute_deleteAlias_success() {
        ModelManager model = new ModelManager();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        model.addAlias("lf", "listf");
        String expectedMessage = String.format(DeleteAliasCommand.MESSAGE_SUCCESS, "listf", "lf");
        assertCommandSuccess(new DeleteAliasCommand("lf"), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_aliasDoesNotExist_throwsCommandException() {
        ModelManager model = new ModelManager();
        assertCommandFailure(new DeleteAliasCommand("lf"), model,
                String.format(DeleteAliasCommand.MESSAGE_ALIAS_DOES_NOT_EXIST, "lf"));
    }
}
