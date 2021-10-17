package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ShowAliasesCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addAlias("lf", "listf");
        expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
    }

    @Test
    public void execute_showsCorrectList() {
        String expectedList = model.getAliases().toString();
        assertCommandSuccess(new ShowAliasesCommand(), model,
                String.format(ShowAliasesCommand.MESSAGE_SUCCESS, expectedList),
                expectedModel);
    }
}
