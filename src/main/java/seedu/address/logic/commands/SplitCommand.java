package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.MemberAvailableOnDayPredicate;

/**
 * Splits members available on particular day to different Facilities.
 */
public class SplitCommand extends Command {
    public static final String COMMAND_WORD = "split";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Splits members into locations based on availability.\n"
            + "Parameters: " + "DAY\n"
            + "DAY must be an integer from 1 to 7\n"
            + "where 1 represents Monday, 2 represents Tuesday ... and 7 represents Sunday\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Members have been split for %1$s";
    public static final String MESSAGE_INSUFFICIENT_FACILITIES =
            "There are not enough facilities to accommodate all members for %1$s.\n"
                    + "%2$d member(s) cannot be allocated.";
    public static final String MESSAGE_NO_MEMBERS_AVAILABLE =
            "There are no members available on %1$s.";

    private final int dayNumber;

    /**
     * Creates a SplitCommand object to split the members.
     *
     * @param dayNumber Day to split members for.
     */
    public SplitCommand(int dayNumber) {
        assert dayNumber >= 1 && dayNumber <= 7 : "dayNumber should be between 1 and 7";
        this.dayNumber = dayNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getInternalMemberList().isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
        }
        MemberAvailableOnDayPredicate predicate = new MemberAvailableOnDayPredicate(dayNumber);
        int result = model.split(predicate, dayNumber);
        if (result == -1) {
            // No members available
            throw new CommandException(String.format(MESSAGE_NO_MEMBERS_AVAILABLE,
                    DayOfWeek.of(dayNumber).getDisplayName(TextStyle.FULL, Locale.getDefault())));
        } else if (result != 0) {
            // Insufficient facilities
            throw new CommandException(String.format(MESSAGE_INSUFFICIENT_FACILITIES,
                    DayOfWeek.of(dayNumber).getDisplayName(TextStyle.FULL, Locale.getDefault()), result));
        } else {
            // Split successful
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    DayOfWeek.of(dayNumber).getDisplayName(TextStyle.FULL, Locale.getDefault())),
                    false, true, false);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == this)
                || (obj instanceof SplitCommand
                && dayNumber == ((SplitCommand) obj).dayNumber);
    }
}
