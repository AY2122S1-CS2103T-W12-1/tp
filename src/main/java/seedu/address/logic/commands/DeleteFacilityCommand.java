package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_FACILITY;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.facility.Facility;

public class DeleteFacilityCommand extends Command {

    public static final String COMMAND_WORD = "deletef";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the facility identified by the index number used in the displayed facility list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FACILITY_SUCCESS = "Deleted Facility: %1$s";

    private final Index targetIndex;

    public DeleteFacilityCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Facility> lastShownFacilityList = model.getFilteredFacilityList();
        if (lastShownFacilityList.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_EMPTY_LIST, MESSAGE_FACILITY));
        }
        if (targetIndex.getZeroBased() >= lastShownFacilityList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FACILITY_DISPLAYED_INDEX);
        }
        Facility facilityToDelete = lastShownFacilityList.get(targetIndex.getZeroBased());
        model.deleteFacility(facilityToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_FACILITY_SUCCESS, facilityToDelete),
                false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteFacilityCommand
                && targetIndex.equals(((DeleteFacilityCommand) other).targetIndex));
    }
}
