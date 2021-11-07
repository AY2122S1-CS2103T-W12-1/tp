package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FACILITIES;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all facilities in SportsPA to the user.
 */
public class ListFacilityCommand extends Command {

    public static final String COMMAND_WORD = "listf";

    public static final String MESSAGE_SUCCESS = "Listed all facilities";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getInternalFacilityList().isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
        }
        model.updateFilteredFacilityList(PREDICATE_SHOW_ALL_FACILITIES);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }
}
