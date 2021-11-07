package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Clears today's attendance for all members in list.
 */
public class ClearAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "cleara";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears today's attendance for all members\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Cleared today's attendance";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getInternalMemberList().isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
        }
        model.resetTodayAttendance();
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
