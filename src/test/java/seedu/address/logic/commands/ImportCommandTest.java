package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.ALICE_DIFFERENT_NAME;
import static seedu.address.testutil.TypicalMembers.ALICE_DIFFERENT_PHONE;
import static seedu.address.testutil.TypicalMembers.HOON;
import static seedu.address.testutil.TypicalMembers.IDA;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.member.Availability;
import seedu.address.model.member.Name;
import seedu.address.model.member.Phone;
import seedu.address.model.tag.Tag;

public class ImportCommandTest {
    private final Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());

    @Test
    public void execute_personNotInSportsPa_listOfPersonAddedWithNoReplacementNoSkip() {
        String testFilePath = "src/test/data/ImportCommandTest/SameMemberNotInSportsPa.csv";
        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.addMember(HOON);
        expectedModel.addMember(IDA);
        assertCommandSuccess(new ImportCommand(testFilePath), model,
                ImportCommand.MESSAGE_SUCCESS_NO_SKIP, expectedModel);
    }

    @Test
    public void execute_personWithSameNameInSportsPa_listOfPersonAddedWithReplacementNoSkip() {
        String testFilePath = "src/test/data/ImportCommandTest/PersonWithSameNameInSportsPa.csv";
        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.addMember(HOON);
        expectedModel.setMember(ALICE, ALICE_DIFFERENT_PHONE);
        assertCommandSuccess(new ImportCommand(testFilePath), model,
                ImportCommand.MESSAGE_SUCCESS_NO_SKIP, expectedModel);
    }

    @Test
    public void execute_personWithSamePhoneInSportsPa_listOfPersonAddedWithReplacementNoSkip() {
        String testFilePath = "src/test/data/ImportCommandTest/PersonWithSamePhoneInSportsPa.csv";
        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.addMember(HOON);
        expectedModel.setMember(ALICE, ALICE_DIFFERENT_NAME);
        assertCommandSuccess(new ImportCommand(testFilePath), model,
                ImportCommand.MESSAGE_SUCCESS_NO_SKIP, expectedModel);
    }

    @Test
    //When a member being imported has the same name as an existing member in the list AND same name as another
    //existing member in the list.
    public void execute_invalidMemberImport_listOfPersonAddedWithReplacementWithSkip() {
        String testFilePath = "src/test/data/ImportCommandTest/InvalidMemberImport.csv";
        String skippedMembers = "[Alice Pauline; Phone: 95352563]";
        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.addMember(HOON);
        assertCommandSuccess(new ImportCommand(testFilePath), model,
                String.format(ImportCommand.MESSAGE_SUCCESS_WITH_SKIP, skippedMembers), expectedModel);
    }

    @Test
    public void execute_invalidNameImportFile_throwsCommandException() {
        String testFilePath = "src/test/data/ImportCommandTest/InvalidNameImportFile.csv";
        String expectedMessage = Name.MESSAGE_CONSTRAINTS + "\nPlease fix the error in the CSV file and try again";
        ImportCommand importCommand = new ImportCommand(testFilePath);
        assertThrows(CommandException.class, expectedMessage, () -> importCommand.execute(model));
    }

    @Test
    public void execute_invalidPhoneImportFile_throwsCommandException() {
        String testFilePath = "src/test/data/ImportCommandTest/InvalidPhoneImportFile.csv";
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS + "\nPlease fix the error in the CSV file and try again";
        ImportCommand importCommand = new ImportCommand(testFilePath);
        assertThrows(CommandException.class, expectedMessage, () -> importCommand.execute(model));
    }

    @Test
    public void execute_invalidAvailabilityImportFile_throwsCommandException() {
        String testFilePath = "src/test/data/ImportCommandTest/InvalidAvailabilityImportFile.csv";
        String expectedMessage = Availability.MESSAGE_CONSTRAINTS
                + "\nPlease fix the error in the CSV file and try again";
        ImportCommand importCommand = new ImportCommand(testFilePath);
        assertThrows(CommandException.class, expectedMessage, () -> importCommand.execute(model));
    }

    @Test
    public void execute_invalidTagsImportFile_throwsCommandException() {
        String testFilePath = "src/test/data/ImportCommandTest/InvalidTagsImportFile.csv";
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS + "\nPlease fix the error in the CSV file and try again";
        ImportCommand importCommand = new ImportCommand(testFilePath);
        assertThrows(CommandException.class, expectedMessage, () -> importCommand.execute(model));
    }

    @Test
    public void execute_invalidFormatImportFile_throwsCommandException() {
        String testFilePath = "src/test/data/ImportCommandTest/InvalidFormatImportFile.csv";
        ImportCommand importCommand = new ImportCommand(testFilePath);
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE), () -> importCommand.execute(model));
    }

    @Test
    public void execute_fileNotFound_throwsCommandException() {
        String testFilePath = "src/test/data/ImportCommandParserTest/FileNotFound.csv";
        ImportCommand importCommand = new ImportCommand(testFilePath);
        assertThrows(CommandException.class,
                String.format(ImportCommand.MESSAGE_FILE_NOT_FOUND, testFilePath), () -> importCommand.execute(model));
    }
}
