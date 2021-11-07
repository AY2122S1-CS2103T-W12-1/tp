package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    // General Messages
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_EMPTY_LIST = "%1$s list is empty!";

    // Member-specific Messages
    public static final String MESSAGE_MEMBER = "Member";
    public static final String MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX = "The member index provided is out of range of"
            + " the currently displayed list";
    public static final String MESSAGE_INVALID_MEMBER_DISPLAYED_INDICES = "One or more of the member index provided is"
            + " out of range of the currently displayed list";
    public static final String MESSAGE_MEMBERS_LISTED_OVERVIEW = "%1$d members listed!";
    public static final String MESSAGE_MEMBER_NOT_AVAILABLE = "The member is not available on that day";
    public static final String MESSAGE_MEMBER_ALREADY_ALLOCATED =
            "The member has already been allocated to that facility on that day";
    public static final String MESSAGE_MEMBER_NOT_ALLOCATED =
            "The member is not allocated to that facility on that day";

    // Facility-specific Messages
    public static final String MESSAGE_FACILITY = "Facility";
    public static final String MESSAGE_FACILITIES_LISTED_OVERVIEW = "%1$d facilities listed!";
    public static final String MESSAGE_INVALID_FACILITY_DISPLAYED_INDEX = "The facility index provided is invalid";
    public static final String MESSAGE_FACILITY_AT_MAX_CAPACITY = "The facility provided is already at max capacity";

}
