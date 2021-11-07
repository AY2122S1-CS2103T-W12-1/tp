package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Member;

/**
 * Unmarks attendance of specified members as absent.
 */
public class UnmarkAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "unmark";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks attendance of members identified by the indices used\n"
            + " in the members list"
            + "Parameters: INDEX [MORE_INDICES]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + "1 2 3";
    public static final String MESSAGE_SUCCESS = "Unmarked attendance of members for today";

    private final List<Index> indices;

    /**
     * Creates UnmarkAttendanceCommand object to unmark attendance
     * of members at specified indices.
     *
     * @param indices Indices of members in list to be unmarked.
     */
    public UnmarkAttendanceCommand(List<Index> indices) {
        requireNonNull(indices);
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Member> lastShownList = model.getFilteredMemberList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
        }
        if (!model.isWithinListIndex(indices)) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }

        model.unmarkMembersAttendance(indices);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof UnmarkAttendanceCommand
                && indices.equals(((UnmarkAttendanceCommand) obj).indices));
    }
}
