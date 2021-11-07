package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.sort.SortOrder;

/**
 * Sorts member list in alphabetical order.
 */
public class SortMemberCommand extends Command {
    public static final String COMMAND_WORD = "sortm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the member list, either by name or tag. "
            + "Parameters: "
            + PREFIX_SORT_ORDER + "ORDER\n"
            + "To sort by name: " + COMMAND_WORD + " "
            + PREFIX_SORT_ORDER + "name\n"
            + "To sort by tags: " + COMMAND_WORD + " "
            + PREFIX_SORT_ORDER + "tag";

    public static final String MESSAGE_SUCCESS = "Sorted all members by %s";
    private final SortOrder sortOrder;

    public SortMemberCommand(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getInternalMemberList().isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
        }
        model.sortMemberList(sortOrder);
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortOrder), false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortMemberCommand // instanceof handles nulls
                && sortOrder.equals(((SortMemberCommand) other).sortOrder)); // state check
    }
}
