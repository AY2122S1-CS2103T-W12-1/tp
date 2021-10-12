package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SplitCommand;

public class SplitCommandParserTest {
    private SplitCommandParser parser = new SplitCommandParser();

    @Test
    public void parse_emptyArgs_exceptionThrown() {
        assertParseFailure(parser, "      ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSplitCommand() {
        SplitCommand expectedSplitCommand = new SplitCommand("MON");
        assertParseSuccess(parser, "Mon", expectedSplitCommand);
        assertParseSuccess(parser, "mon", expectedSplitCommand);
        assertParseSuccess(parser, "mOn", expectedSplitCommand);
        assertParseSuccess(parser, "\n  \t MON  \t ", expectedSplitCommand);
    }
}
