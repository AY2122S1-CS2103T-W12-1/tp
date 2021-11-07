package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FACILITIES;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.facility.AllocationMap;
import seedu.address.model.facility.Capacity;
import seedu.address.model.facility.Facility;
import seedu.address.model.facility.FacilityName;
import seedu.address.model.facility.Location;
import seedu.address.model.facility.Time;

public class EditFacilityCommand extends Command {

    public static final String COMMAND_WORD = "editf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the facility identified "
            + "by the index number used in the displayed facility list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_CAPACITY + "CAPACITY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LOCATION + "Kent Ridge Sports Hall 5";

    public static final String MESSAGE_EDIT_FACILITY_SUCCESS = "Edited Facility: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FACILITY = "This facility already exists in SportsPA";

    private final Index index;
    private final EditFacilityDescriptor editFacilityDescriptor;

    /**
     * Creates EditFacilityCommand object.
     *
     * @param index of the facility in the filtered facility list to edit.
     * @param editFacilityDescriptor details to edit the facility with.
     */
    public EditFacilityCommand(Index index, EditFacilityDescriptor editFacilityDescriptor) {
        requireNonNull(index);
        requireNonNull(editFacilityDescriptor);

        this.index = index;
        this.editFacilityDescriptor = new EditFacilityDescriptor(editFacilityDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Facility> lastShownList = model.getFilteredFacilityList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
        }
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FACILITY_DISPLAYED_INDEX);
        }

        Facility facilityToEdit = lastShownList.get(index.getZeroBased());
        Facility editedFacility = createEditedFacility(facilityToEdit, editFacilityDescriptor);

        if (!facilityToEdit.isSameFacility(editedFacility) && model.hasFacility(editedFacility)) {
            throw new CommandException(MESSAGE_DUPLICATE_FACILITY);
        }

        for (DayOfWeek day : DayOfWeek.values()) {
            if (facilityToEdit.getCapacityAsOnDay(day) > editedFacility.getMaxCapacityOnDay(day)) {
                facilityToEdit.clearAllocationMapOnDay(day);
            }
        }

        model.setFacility(facilityToEdit, editedFacility);
        model.updateFilteredFacilityList(PREDICATE_SHOW_ALL_FACILITIES);
        return new CommandResult(String.format(MESSAGE_EDIT_FACILITY_SUCCESS, editedFacility),
                false, true, false);
    }

    /**
     * Creates and returns a {@code Facility} with the details of {@code facilityToEdit}
     * edited with {@code editFacilityDescriptor}.
     */
    private static Facility createEditedFacility(Facility facilityToEdit,
                                                 EditFacilityDescriptor editFacilityDescriptor) {
        assert facilityToEdit != null;

        FacilityName updatedName = editFacilityDescriptor.getFacilityName().orElse(facilityToEdit.getName());
        Location updatedLocation = editFacilityDescriptor.getLocation().orElse(facilityToEdit.getLocation());
        Time updatedTime = editFacilityDescriptor.getTime().orElse(facilityToEdit.getTime());
        Capacity updatedCapacity = editFacilityDescriptor.getCapacity().orElse(facilityToEdit.getCapacity());

        // edit command does not allow editing allocations
        AllocationMap allocationMap = facilityToEdit.getAllocationMap();

        return new Facility(updatedName, updatedLocation, updatedTime, updatedCapacity, allocationMap);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EditFacilityCommand
                && index.equals(((EditFacilityCommand) other).index)
                && editFacilityDescriptor.equals(((EditFacilityCommand) other).editFacilityDescriptor));
    }

    /**
     * Stores the detials to edit the facility with. Each non-empty field will replace the
     * corresponding field value of the facility.
     */
    public static class EditFacilityDescriptor {
        private FacilityName facilityName;
        private Location location;
        private Time time;
        private Capacity capacity;

        public EditFacilityDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditFacilityDescriptor(EditFacilityDescriptor toCopy) {
            setFacilityName(toCopy.facilityName);
            setLocation(toCopy.location);
            setTime(toCopy.time);
            setCapacity(toCopy.capacity);
        }

        /**
         * Checks if at least one field is edited.
         *
         * @return true if at least one field is edited, false is otherwise.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(facilityName, location, time, capacity);
        }

        public void setFacilityName(FacilityName facilityName) {
            this.facilityName = facilityName;
        }

        public Optional<FacilityName> getFacilityName() {
            return Optional.ofNullable(facilityName);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setCapacity(Capacity capacity) {
            this.capacity = capacity;
        }

        public Optional<Capacity> getCapacity() {
            return Optional.ofNullable(capacity);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditFacilityDescriptor)) {
                return false;
            }

            EditFacilityDescriptor e = (EditFacilityDescriptor) other;

            return getFacilityName().equals(e.getFacilityName())
                    && getLocation().equals(e.getLocation())
                    && getTime().equals(e.getTime())
                    && getCapacity().equals(e.getCapacity());
        }
    }

}
