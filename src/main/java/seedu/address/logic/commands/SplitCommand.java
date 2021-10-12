package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DAY;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonAvailableOnDayPredicate;

/**
 * Splits members available on particular day to different Facilities.
 */
public class SplitCommand extends Command {
    public static final String COMMAND_WORD = "split";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": splits members into locations based on availability.\n"
            + "Parameters: " + "DAY\n"
            + "DAY must be one of Mon, Tue, Wed, Thu, Fri, Sat or Sun (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " mon";
    public static final String MESSAGE_SUCCESS = "Members have been split for %1$s";

    private static final List<String> VALID_DAYS = Arrays.asList("MON", "TUE", "WED",
            "THU", "FRI", "SAT", "SUN");
    private final String day;

    /**
     * Creates a SplitCommand object to split the members.
     *
     * @param day Day to split members for.
     */
    public SplitCommand(String day) {
        requireNonNull(day);
        this.day = day;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!VALID_DAYS.contains(day)) {
            throw new CommandException(String.format(MESSAGE_INVALID_DAY, MESSAGE_USAGE));
        }
        PersonAvailableOnDayPredicate predicate = new PersonAvailableOnDayPredicate(day);
        model.split(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, day));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == this)
                || (obj instanceof SplitCommand
                && day.equals(((SplitCommand) obj).day));
    }
}
