package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Clears the facility list of SportsPA.
 */
public class ClearFacilitiesCommand extends Command {

    public static final String COMMAND_WORD = "clearf";
    public static final String MESSAGE_SUCCESS = "Facility list has been cleared!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getInternalFacilityList().isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
        }
        model.resetFacilityList();
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }
}
