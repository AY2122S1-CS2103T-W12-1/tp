package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AVAILABILITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAILABILITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetMemberAvailabilityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.Availability;

public class SetMemberAvailabilityCommandParserTest {
    private SetMemberAvailabilityCommandParser parser = new SetMemberAvailabilityCommandParser();

    @Test
    public void parse_emptyArgs_exceptionThrown() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetMemberAvailabilityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyIndices_exceptionThrown() {
        assertParseFailure(parser, AVAILABILITY_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetMemberAvailabilityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleIndicesMultipleAvailability_success() throws ParseException {
        List<Index> expectedIndices = new ArrayList<>();
        String indicesString = "1 2 3";
        String[] indicesArray = indicesString.split(" ");
        for (String s : indicesArray) {
            expectedIndices.add(ParserUtil.parseIndex(s));
        }
        List<DayOfWeek> expectedAvailability = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        assertParseSuccess(parser, "1 2 3" + AVAILABILITY_DESC_BOB,
                new SetMemberAvailabilityCommand(expectedIndices, new Availability(expectedAvailability)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetMemberAvailabilityCommand.MESSAGE_USAGE);

        // missing index/indices
        assertParseFailure(parser, AVAILABILITY_DESC_BOB, expectedMessage);

        // missing availability prefix
        assertParseFailure(parser, "1 2 3" + NAME_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetMemberAvailabilityCommand.MESSAGE_USAGE);
        // invalid index/indices
        assertParseFailure(parser, "one two three" + AVAILABILITY_DESC_BOB, expectedMessage);

        // invalid availability
        assertParseFailure(parser, "1 2 3" + INVALID_AVAILABILITY_DESC, Availability.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyAvailability_success() throws ParseException {
        List<Index> expectedIndices = new ArrayList<>();
        String indicesString = "1 2 3";
        String[] indicesArray = indicesString.split(" ");
        for (String s : indicesArray) {
            expectedIndices.add(ParserUtil.parseIndex(s));
        }
        assertParseSuccess(parser, "1 2 3" + " d/",
                new SetMemberAvailabilityCommand(expectedIndices, new Availability(new ArrayList<>())));
    }
}
