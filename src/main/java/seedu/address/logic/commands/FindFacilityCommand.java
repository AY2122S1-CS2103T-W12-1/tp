package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.facility.LocationContainsKeywordsPredicate;

/**
 * Finds and lists all facilities in application whose locations contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindFacilityCommand extends Command {

    public static final String COMMAND_WORD = "findf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all facilities whose locations contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " utown clementi";

    private final LocationContainsKeywordsPredicate predicate;

    public FindFacilityCommand(LocationContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getInternalFacilityList().isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
        }
        model.updateFilteredFacilityList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FACILITIES_LISTED_OVERVIEW, model.getFilteredFacilityList().size()),
                false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindFacilityCommand
                && predicate.equals(((FindFacilityCommand) other).predicate));
    }
}
