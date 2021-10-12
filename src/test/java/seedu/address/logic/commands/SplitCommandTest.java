package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DAY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonAvailableOnDayPredicate;
import seedu.address.testutil.PersonBuilder;

public class SplitCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new SplitCommand(null));
    }

    @Test
    public void execute_validDay_success() {
        SplitCommand command = new SplitCommand("THU");
        String expectedMessage = String.format(SplitCommand.MESSAGE_SUCCESS, "THU");
        Person person = new PersonBuilder().withAvailability("MON TUE THU").build();
        model.setPerson(ALICE, person);
        expectedModel.setPerson(ALICE, person);
        PersonAvailableOnDayPredicate predicate = new PersonAvailableOnDayPredicate("THU");
        expectedModel.split(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDay_exceptionThrown() {
        List<String> invalidDays = Arrays.asList("Monday", "mond", "m");
        for (String invalidDay : invalidDays) {
            SplitCommand command = new SplitCommand(invalidDay);
            assertThrows(CommandException.class,
                    String.format(MESSAGE_INVALID_DAY, SplitCommand.MESSAGE_USAGE), () -> command.execute(model));
        }
    }

    @Test
    public void equals() {
        SplitCommand firstCommand = new SplitCommand("MON");
        SplitCommand secondCommand = new SplitCommand("TUE");

        assertTrue(firstCommand.equals(firstCommand));

        SplitCommand firstCommandCopy = new SplitCommand("MON");
        assertTrue(firstCommand.equals(firstCommandCopy));

        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
    }


}
