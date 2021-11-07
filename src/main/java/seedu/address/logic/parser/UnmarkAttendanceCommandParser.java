package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkAttendanceCommand object.
 */
public class UnmarkAttendanceCommandParser implements Parser<UnmarkAttendanceCommand> {

    @Override
    public UnmarkAttendanceCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkAttendanceCommand.MESSAGE_USAGE));
        }

        String[] numbers = trimmedArgs.split("\\s+");
        List<Index> indices = new ArrayList<>();

        for (String number : numbers) {
            Index index = ParserUtil.parseIndex(number);
            indices.add(index);
        }

        assert indices.size() != 0 : "indices list should contain some indices";
        return new UnmarkAttendanceCommand(indices);
    }
}
