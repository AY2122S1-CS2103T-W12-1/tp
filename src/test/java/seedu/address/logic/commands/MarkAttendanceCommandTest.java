package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.member.Member;
import seedu.address.testutil.MemberBuilder;

public class MarkAttendanceCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_validIndices_success() {

        //add 2 members with index 1 and 2 respectively in the list
        Member firstMember = new MemberBuilder().withName("John").withPhone("83452732").build();
        Member secondMember = new MemberBuilder().withName("Mat").build();
        model.addMember(firstMember);
        model.addMember(secondMember);

        MarkAttendanceCommand command = new MarkAttendanceCommand(Arrays.asList(INDEX_FIRST, INDEX_SECOND));
        String expectedMessage = MarkAttendanceCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.markMembersAttendance(Arrays.asList(INDEX_FIRST, INDEX_SECOND));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndices_throwsCommandException() {
        Member firstMember = new MemberBuilder().withName("John").build();
        model.addMember(firstMember);
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        MarkAttendanceCommand command = new MarkAttendanceCommand(Arrays.asList(outOfBoundsIndex));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyMemberList_failure() {
        Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        model.updateFilteredMemberList(Model.PREDICATE_SHOW_NO_MEMBERS);
        MarkAttendanceCommand command = new MarkAttendanceCommand(Arrays.asList(INDEX_FIRST));
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
    }

    @Test
    public void equals() {
        MarkAttendanceCommand markFirstCommand = new MarkAttendanceCommand(Arrays.asList(INDEX_FIRST, INDEX_THIRD));
        MarkAttendanceCommand markSecondCommand = new MarkAttendanceCommand(Arrays.asList(INDEX_SECOND));

        //same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        //same value -> returns true
        MarkAttendanceCommand markFirstCommandCopy = new MarkAttendanceCommand(Arrays.asList(INDEX_FIRST,
                INDEX_THIRD));
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        //different values -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));

        //null -> returns false
        assertFalse(markFirstCommand.equals(null));

        //different types -> returns false
        assertFalse(markFirstCommand.equals("1"));
    }
}
