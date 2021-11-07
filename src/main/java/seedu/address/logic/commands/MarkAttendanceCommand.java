package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Member;

/**
 * Marks attendance of specified members as present.
 */
public class MarkAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "mark";
    public static final String MESSGE_USAGE = COMMAND_WORD
            + ": Marks the members identified by the indices used"
            + "in the members list as present.\n"
            + "Parameters: INDEX [MORE_INDICES]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + "1 2 3";
    public static final String MESSAGE_SUCCESS = "Marked attendance of members for today";

    private final List<Index> indices;

    /**
     * Creates MarkAttendanceCommand object to mark attendance of
     * members at specified indices.
     *
     * @param indices Indices of members in list to be marked present.
     */
    public MarkAttendanceCommand(List<Index> indices) {
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

        assert indices != null : "indices should be initialised";
        model.markMembersAttendance(indices);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof MarkAttendanceCommand
                && indices.equals(((MarkAttendanceCommand) obj).indices));
    }

}
