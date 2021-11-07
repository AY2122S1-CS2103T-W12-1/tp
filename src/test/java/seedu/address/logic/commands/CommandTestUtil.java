package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.SportsPa;
import seedu.address.model.facility.Facility;
import seedu.address.model.facility.LocationContainsKeywordsPredicate;
import seedu.address.model.member.Member;
import seedu.address.model.member.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditMemberDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CHARLIE = "Charlie";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_CHARLIE = "33333333";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_AVAILABILITY_AMY = "1 2 3";
    public static final String VALID_AVAILABILITY_BOB = "1 2 3";
    public static final String VALID_AVAILABILITY_CHARLIE = "1 2 3";
    public static final String VALID_TAG_EXCO = "exco";
    public static final String VALID_TAG_Y2 = "y2";

    public static final String VALID_FACILITY_NAME_COURT = "Court 1";
    public static final String VALID_LOCATION_COURT = "University Sports Hall";
    public static final String VALID_TIME_COURT = "1130";
    public static final String VALID_CAPACITY_COURT = "5";
    public static final String VALID_FACILITY_NAME_FIELD = "NUS Field 2";
    public static final String VALID_LOCATION_FIELD = "Opp University Hall";
    public static final String VALID_TIME_FIELD = "1330";
    public static final String VALID_CAPACITY_FIELD = "8";
    public static final Map<DayOfWeek, List<Member>> VALID_ALLOCATION_MAP = new EnumMap<>(
            Map.ofEntries(Map.entry(DayOfWeek.MONDAY, new ArrayList<>()),
                    Map.entry(DayOfWeek.TUESDAY, new ArrayList<>()),
                    Map.entry(DayOfWeek.WEDNESDAY, new ArrayList<>()),
                    Map.entry(DayOfWeek.THURSDAY, new ArrayList<>()),
                    Map.entry(DayOfWeek.FRIDAY, new ArrayList<>()),
                    Map.entry(DayOfWeek.SATURDAY, new ArrayList<>()),
                    Map.entry(DayOfWeek.SUNDAY, new ArrayList<>())));

    public static final String VALID_SORT_ORDER_NAME = "name";
    public static final String VALID_SORT_ORDER_TAG = "tag";

    public static final String NAME_DESC_COURT = " " + PREFIX_NAME + VALID_FACILITY_NAME_COURT;
    public static final String LOCATION_DESC_COURT = " " + PREFIX_LOCATION + VALID_LOCATION_COURT;
    public static final String TIME_DESC_COURT = " " + PREFIX_TIME + VALID_TIME_COURT;
    public static final String CAPACITY_DESC_COURT = " " + PREFIX_CAPACITY + VALID_CAPACITY_COURT;
    public static final String NAME_DESC_FIELD = " " + PREFIX_NAME + VALID_FACILITY_NAME_FIELD;
    public static final String LOCATION_DESC_FIELD = " " + PREFIX_LOCATION + VALID_LOCATION_FIELD;
    public static final String TIME_DESC_FIELD = " " + PREFIX_TIME + VALID_TIME_FIELD;
    public static final String CAPACITY_DESC_FIELD = " " + PREFIX_CAPACITY + VALID_CAPACITY_FIELD;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String TAG_DESC_Y2 = " " + PREFIX_TAG + VALID_TAG_Y2;
    public static final String TAG_DESC_EXCO = " " + PREFIX_TAG + VALID_TAG_EXCO;
    public static final String AVAILABILITY_DESC_AMY = " " + PREFIX_AVAILABILITY + VALID_AVAILABILITY_AMY;
    public static final String AVAILABILITY_DESC_BOB = " " + PREFIX_AVAILABILITY + VALID_AVAILABILITY_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    // must be integers
    public static final String INVALID_AVAILABILITY_DESC = " " + PREFIX_AVAILABILITY + "one two three";
    public static final String INVALID_SORT_ORDER_DESC = " " + PREFIX_SORT_ORDER + "aa bb cc";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String INVALID_PREAMBLE = "NonEmptyPreamble";

    public static final String SORT_ORDER_DESC_TAG = " " + PREFIX_SORT_ORDER + VALID_SORT_ORDER_TAG;
    public static final String SORT_ORDER_DESC_NAME = " " + PREFIX_SORT_ORDER + VALID_SORT_ORDER_NAME;

    public static final EditMemberCommand.EditMemberDescriptor DESC_AMY;
    public static final EditMemberCommand.EditMemberDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditMemberDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withTags(VALID_TAG_Y2).build();
        DESC_BOB = new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_EXCO, VALID_TAG_Y2).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult;

        if (isFacilityCommand(command)) {
            expectedCommandResult = new CommandResult(expectedMessage, false,
                    true, false);
        } else if (isMemberCommand(command)) {
            expectedCommandResult = new CommandResult(expectedMessage, false,
                    false, true);
        } else {
            expectedCommandResult = new CommandResult(expectedMessage);
        }

        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Checks if the given command should select the Facility Tab.
     */
    private static boolean isFacilityCommand(Command command) {
        return command instanceof AddFacilityCommand
                || command instanceof ClearFacilitiesCommand
                || command instanceof DeleteFacilityCommand
                || command instanceof EditFacilityCommand
                || command instanceof FindFacilityCommand
                || command instanceof ListFacilityCommand
                || command instanceof SplitCommand;
    }

    /**
     * Checks if the given command should select the Member Tab.
     */
    private static boolean isMemberCommand(Command command) {
        return command instanceof AddMemberCommand
                || command instanceof ClearMembersCommand
                || command instanceof DeleteMemberCommand
                || command instanceof EditMemberCommand
                || command instanceof FindMemberCommand
                || command instanceof ListMemberCommand
                || command instanceof SetMemberAvailabilityCommand
                || command instanceof SortMemberCommand
                || command instanceof ClearAttendanceCommand
                || command instanceof MarkAttendanceCommand
                || command instanceof UnmarkAttendanceCommand;
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered member list and selected member in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        SportsPa expectedSportsPa = new SportsPa(actualModel.getSportsPa());
        List<Member> expectedFilteredList = new ArrayList<>(actualModel.getFilteredMemberList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedSportsPa, actualModel.getSportsPa());
        assertEquals(expectedFilteredList, actualModel.getFilteredMemberList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the member at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMemberList().size());

        Member member = model.getFilteredMemberList().get(targetIndex.getZeroBased());
        final String[] splitName = member.getName().fullName.split("\\s+");
        model.updateFilteredMemberList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0].toLowerCase())));

        assertEquals(1, model.getFilteredMemberList().size());
    }

    /**
     * Updates {@code model}'s filtered facility list to show only the facility at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showFacilityAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFacilityList().size());
        Facility facility = model.getFilteredFacilityList().get(targetIndex.getZeroBased());
        final String[] splitLocation = facility.getLocation().location.split("\\s+");
        model.updateFilteredFacilityList(new LocationContainsKeywordsPredicate(Arrays.asList(splitLocation[0])));

        assertEquals(1, model.getFilteredFacilityList().size());
    }

}
