package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalMembers.AMY;
import static seedu.address.testutil.TypicalMembers.BOB;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.member.Availability;
import seedu.address.testutil.MemberBuilder;

public class SetMemberAvailabilityCommandTest {
    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new SetMemberAvailabilityCommand(null, null));
    }

    @Test
    public void execute_validIndicesProvided_success() {
        Model model = new ModelManager();
        model.addMember(AMY);
        model.addMember(BOB);

        Model expectedModel = new ModelManager();
        expectedModel.addMember(new MemberBuilder(AMY).withAvailability("6 7").build());
        expectedModel.addMember(new MemberBuilder(BOB).withAvailability("6 7").build());
        Availability expectedAvailability = new Availability(List.of(DayOfWeek.of(6), DayOfWeek.of(7)));
        SetMemberAvailabilityCommand command = new SetMemberAvailabilityCommand(List.of(INDEX_FIRST, INDEX_SECOND),
                expectedAvailability);
        String expectedNames = AMY.getName() + ", " + BOB.getName() + ", ";
        String expectedMessage = String.format(SetMemberAvailabilityCommand.MESSAGE_SET_AVAILABILITY_SUCCESS,
                expectedNames, expectedAvailability);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndicesAndInvalidIndicesProvided_failure() {
        Model model = new ModelManager();
        model.addMember(AMY);

        Availability expectedAvailability = new Availability(List.of(DayOfWeek.of(6), DayOfWeek.of(7)));
        SetMemberAvailabilityCommand command = new SetMemberAvailabilityCommand(List.of(INDEX_FIRST, INDEX_SECOND),
                expectedAvailability);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDICES);
    }

    @Test
    public void execute_emptyMemberList_failure() {
        Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
        model.updateFilteredMemberList(Model.PREDICATE_SHOW_NO_MEMBERS);
        Availability expectedAvailability = new Availability(List.of(DayOfWeek.of(6), DayOfWeek.of(7)));
        SetMemberAvailabilityCommand command = new SetMemberAvailabilityCommand(List.of(INDEX_FIRST, INDEX_SECOND),
                expectedAvailability);
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
    }

    @Test
    public void equals() throws ParseException {
        List<Index> firstIndices = new ArrayList<>();
        String firstIndicesString = "1 2 3";
        String[] firstIndicesArray = firstIndicesString.split(" ");
        for (String s : firstIndicesArray) {
            firstIndices.add(ParserUtil.parseIndex(s));
        }
        List<Index> secondIndices = new ArrayList<>();
        String secondIndicesString = "4 5 6";
        String[] secondIndicesArray = secondIndicesString.split(" ");
        for (String s : secondIndicesArray) {
            secondIndices.add(ParserUtil.parseIndex(s));
        }
        List<DayOfWeek> firstAvailability = Arrays.asList(DayOfWeek.MONDAY);
        List<DayOfWeek> secondAvailability = Arrays.asList(DayOfWeek.TUESDAY);
        SetMemberAvailabilityCommand firstCommand =
                new SetMemberAvailabilityCommand(firstIndices, new Availability(firstAvailability));
        SetMemberAvailabilityCommand secondCommand =
                new SetMemberAvailabilityCommand(secondIndices, new Availability(secondAvailability));

        assertTrue(firstCommand.equals(firstCommand));
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
    }
}
