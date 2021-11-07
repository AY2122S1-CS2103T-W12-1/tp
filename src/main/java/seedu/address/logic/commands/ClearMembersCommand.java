package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Clears the members list of SportsPA.
 */
public class ClearMembersCommand extends Command {

    public static final String COMMAND_WORD = "clearm";
    public static final String MESSAGE_SUCCESS = "Member list has been cleared!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getInternalMemberList().isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
        }
        model.resetMemberList();
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
