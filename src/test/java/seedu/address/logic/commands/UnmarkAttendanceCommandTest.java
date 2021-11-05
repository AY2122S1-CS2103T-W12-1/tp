package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.member.Member;
import seedu.address.testutil.MemberBuilder;

class UnmarkAttendanceCommandTest {
    private Model model = new ModelManager();

    @Test
    void execute_validIndices_success() {
        Member firstMember = new MemberBuilder().withName("John").withPhone("83452732").build();
        Member secondMember = new MemberBuilder().withName("Mat").build();
        model.addMember(firstMember);
        model.addMember(secondMember);

        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(Arrays.asList(INDEX_FIRST, INDEX_SECOND));

        String expectedMessage = UnmarkAttendanceCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.unmarkMembersAttendance(Arrays.asList(INDEX_FIRST, INDEX_SECOND));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndices_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(Arrays.asList(outOfBoundsIndex));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnmarkAttendanceCommand firstCommand = new UnmarkAttendanceCommand(Arrays.asList(INDEX_FIRST, INDEX_SECOND));
        UnmarkAttendanceCommand secondCommand = new UnmarkAttendanceCommand(Arrays.asList(INDEX_THIRD));

        assertTrue(firstCommand.equals(firstCommand));

        UnmarkAttendanceCommand firstCommandCopy = new UnmarkAttendanceCommand(Arrays.asList(INDEX_FIRST,
                INDEX_SECOND));
        assertTrue(firstCommand.equals(firstCommandCopy));

        assertFalse(firstCommand.equals(secondCommand));

        assertFalse(firstCommand.equals(null));

        assertFalse(firstCommand.equals("1"));
    }
}
