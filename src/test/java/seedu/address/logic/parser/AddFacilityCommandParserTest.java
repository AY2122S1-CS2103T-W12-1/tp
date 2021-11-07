package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CAPACITY_DESC_COURT;
import static seedu.address.logic.commands.CommandTestUtil.CAPACITY_DESC_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_COURT;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_COURT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_COURT;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLOCATION_MAP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAPACITY_COURT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACILITY_NAME_COURT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_COURT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_COURT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.DayOfWeek;
import java.util.EnumMap;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddFacilityCommand;
import seedu.address.model.facility.AllocationMap;
import seedu.address.model.facility.Capacity;
import seedu.address.model.facility.Facility;
import seedu.address.model.facility.FacilityName;
import seedu.address.model.facility.Location;
import seedu.address.model.facility.Time;

class AddFacilityCommandParserTest {
    private AddFacilityCommandParser parser = new AddFacilityCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        Facility facility = new Facility(new FacilityName("Court 1"), new Location("University Sports Hall"),
                new Time("1130"), new Capacity("5"), new AllocationMap(VALID_ALLOCATION_MAP));

        assertParseSuccess(parser, NAME_DESC_COURT + LOCATION_DESC_COURT + TIME_DESC_COURT + CAPACITY_DESC_COURT,
                new AddFacilityCommand(facility));

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_COURT + LOCATION_DESC_COURT + TIME_DESC_COURT
                        + CAPACITY_DESC_COURT, new AddFacilityCommand(facility));

        assertParseSuccess(parser, NAME_DESC_FIELD + NAME_DESC_COURT + LOCATION_DESC_COURT + TIME_DESC_COURT
                        + CAPACITY_DESC_COURT, new AddFacilityCommand(facility));

        assertParseSuccess(parser, NAME_DESC_COURT + LOCATION_DESC_FIELD + LOCATION_DESC_COURT + TIME_DESC_COURT
                + CAPACITY_DESC_COURT, new AddFacilityCommand(facility));

        assertParseSuccess(parser, NAME_DESC_COURT + LOCATION_DESC_COURT + TIME_DESC_FIELD + TIME_DESC_COURT
                + CAPACITY_DESC_COURT, new AddFacilityCommand(facility));

        assertParseSuccess(parser, NAME_DESC_COURT + LOCATION_DESC_COURT + TIME_DESC_COURT
                + CAPACITY_DESC_FIELD + CAPACITY_DESC_COURT, new AddFacilityCommand(facility));


    }

    @Test
    public void parse_fieldsMissing_exceptionThrown() {
        Facility facility = new Facility(new FacilityName("Court 1"), new Location("University Sports Hall"),
                new Time("1130"), new Capacity("5"), new AllocationMap(new EnumMap<>(DayOfWeek.class)));

        //missing name
        assertParseFailure(parser, LOCATION_DESC_COURT + TIME_DESC_COURT + CAPACITY_DESC_COURT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacilityCommand.MESSAGE_USAGE));

        //missing location
        assertParseFailure(parser, NAME_DESC_COURT + TIME_DESC_COURT + CAPACITY_DESC_COURT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacilityCommand.MESSAGE_USAGE));

        //missing time
        assertParseFailure(parser, NAME_DESC_COURT + LOCATION_DESC_COURT + CAPACITY_DESC_COURT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacilityCommand.MESSAGE_USAGE));

        //missing capacity
        assertParseFailure(parser, NAME_DESC_COURT + LOCATION_DESC_COURT + TIME_DESC_COURT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacilityCommand.MESSAGE_USAGE));

        //missing name prefix
        assertParseFailure(parser, VALID_FACILITY_NAME_COURT + LOCATION_DESC_COURT + TIME_DESC_COURT
                + CAPACITY_DESC_COURT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacilityCommand.MESSAGE_USAGE));

        //missing location prefix
        assertParseFailure(parser, NAME_DESC_COURT + VALID_LOCATION_COURT + TIME_DESC_COURT
                + CAPACITY_DESC_COURT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacilityCommand.MESSAGE_USAGE));

        //missing time prefix
        assertParseFailure(parser, NAME_DESC_COURT + LOCATION_DESC_COURT + VALID_TIME_COURT
                + CAPACITY_DESC_COURT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacilityCommand.MESSAGE_USAGE));

        //missing capacity prefix
        assertParseFailure(parser, NAME_DESC_COURT + LOCATION_DESC_COURT + TIME_DESC_COURT + VALID_CAPACITY_COURT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFacilityCommand.MESSAGE_USAGE));

        //missing all prefixes
        assertParseFailure(parser, VALID_FACILITY_NAME_COURT + VALID_LOCATION_COURT + VALID_TIME_COURT
                + VALID_CAPACITY_COURT, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddFacilityCommand.MESSAGE_USAGE));

    }
}
