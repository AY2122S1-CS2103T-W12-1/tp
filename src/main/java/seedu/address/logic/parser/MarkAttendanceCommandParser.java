package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object.
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    @Override
    public MarkAttendanceCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSGE_USAGE));
        }

        String[] numbers = trimmedArgs.split("\\s+");
        List<Index> indices = new ArrayList<>();

        for (String number : numbers) {
            Index index = ParserUtil.parseIndex(number);
            indices.add(index);
        }

        assert indices.size() != 0 : "indices list should contain some indices";
        return new MarkAttendanceCommand(indices);
    }
}
