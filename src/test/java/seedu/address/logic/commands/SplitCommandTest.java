package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMembers.AMY;
import static seedu.address.testutil.TypicalMembers.BOB;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SportsPa;
import seedu.address.model.UserPrefs;
import seedu.address.model.facility.Facility;
import seedu.address.model.member.MemberAvailableOnDayPredicate;
import seedu.address.testutil.FacilityBuilder;

public class SplitCommandTest {

    @Test
    public void execute_validDay_success() {
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        SplitCommand command = new SplitCommand(1);
        model.addMember(AMY);
        Facility f = new FacilityBuilder().withCapacity("1").build();
        model.addFacility(f);
        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        MemberAvailableOnDayPredicate predicate = new MemberAvailableOnDayPredicate(1);
        expectedModel.split(predicate, 1);
        assertCommandSuccess(command, model, String.format(SplitCommand.MESSAGE_SUCCESS,
                DayOfWeek.of(1).getDisplayName(TextStyle.FULL, Locale.getDefault())), expectedModel);
    }

    @Test
    public void execute_noMembersAvailable_throwsCommandException() {
        Model model = new ModelManager(new SportsPa(getTypicalSportsPa()), new UserPrefs());
        SplitCommand command = new SplitCommand(1);
        assertCommandFailure(command, model, String.format(
                SplitCommand.MESSAGE_NO_MEMBERS_AVAILABLE,
                DayOfWeek.of(1).getDisplayName(TextStyle.FULL, Locale.getDefault())));
    }

    @Test
    public void execute_emptyMemberList_throwsCommandException() {
        SplitCommand command = new SplitCommand(1);
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
    }

    @Test
    public void execute_insufficientFacilities_throwsCommandException() {
        SplitCommand command = new SplitCommand(1);
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        Facility f = new FacilityBuilder().withCapacity("1").build();
        model.addFacility(f);
        model.addMember(AMY);
        model.addMember(BOB);
        String expectedMessage = String.format(SplitCommand.MESSAGE_INSUFFICIENT_FACILITIES,
                DayOfWeek.of(1).getDisplayName(TextStyle.FULL, Locale.getDefault()), 1);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_emptyFacilityList_throwsCommandException() {
        SplitCommand command = new SplitCommand(1);
        Model model = new ModelManager(new SportsPa(), new UserPrefs());
        model.addMember(AMY);
        model.addMember(BOB);
        String expectedMessage = String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        SplitCommand firstCommand = new SplitCommand(1);
        SplitCommand secondCommand = new SplitCommand(2);

        assertTrue(firstCommand.equals(firstCommand));

        SplitCommand firstCommandCopy = new SplitCommand(1);
        assertTrue(firstCommand.equals(firstCommandCopy));

        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
    }
}
