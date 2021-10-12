package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SplitCommandParser implements Parser<SplitCommand> {

    @Override
    public SplitCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SplitCommand.MESSAGE_USAGE));
        }

        return new SplitCommand(trimmedArgs.toUpperCase());
    }
}
