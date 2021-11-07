package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetMemberAvailabilityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.Availability;

/**
 * Parses input arguments and creates a new SetMemberAvailabilityCommand object.
 */
public class SetMemberAvailabilityCommandParser implements Parser<SetMemberAvailabilityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetMemberAvailabilityCommand
     * and returns a SetMemberAvailabilityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetMemberAvailabilityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_AVAILABILITY);

        if (!argMultimap.getValue(PREFIX_AVAILABILITY).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetMemberAvailabilityCommand.MESSAGE_USAGE));
        }

        List<Index> indices;
        try {
            indices = new ArrayList<>();
            String indicesString = argMultimap.getPreamble();
            List<String> indicesWithNoDuplicates =
                    Arrays.stream(indicesString.split(" ")).distinct().collect(Collectors.toList());
            for (String s : indicesWithNoDuplicates) {
                indices.add(ParserUtil.parseIndex(s));
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetMemberAvailabilityCommand.MESSAGE_USAGE), pe);
        }

        Availability availability = ParserUtil.parseAvailability(argMultimap.getValue(PREFIX_AVAILABILITY).get());

        return new SetMemberAvailabilityCommand(indices, availability);
    }
}
