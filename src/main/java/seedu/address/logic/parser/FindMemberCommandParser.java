package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODAY_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_ATTENDANCE;
import static seedu.address.model.member.MemberMatchesKeywordsPredicate.Builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.member.Availability;
import seedu.address.model.member.AvailabilityContainsKeywordsPredicate;
import seedu.address.model.member.Member;
import seedu.address.model.member.Name;
import seedu.address.model.member.NameContainsKeywordsPredicate;
import seedu.address.model.member.Phone;
import seedu.address.model.member.PhoneContainsKeywordsPredicate;
import seedu.address.model.member.TodayAttendance;
import seedu.address.model.member.TodayAttendanceContainsKeywordsPredicate;
import seedu.address.model.member.TotalAttendance;
import seedu.address.model.member.TotalAttendanceContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagsContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMemberCommand object
 */
public class FindMemberCommandParser implements Parser<FindMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMemberCommand
     * and returns a FindMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMemberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMemberCommand.MESSAGE_USAGE));
        }
        return new FindMemberCommand(generatePredicate(args));
    }

    /**
     * Generates the final predicate to be used for FindMemberCommand
     * @throws ParseException if the user input does not conform the expected format.
     */
    private Predicate<Member> generatePredicate(String args) throws ParseException {
        Builder builder = new Builder();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_AVAILABILITY, PREFIX_TAG, PREFIX_TODAY_ATTENDANCE, PREFIX_TOTAL_ATTENDANCE);
        if (!argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMemberCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            generateNamePredicate(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()), builder);
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            generatePhonePredicate(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()), builder);
        }
        if (argMultimap.getValue(PREFIX_AVAILABILITY).isPresent()) {
            generateAvailabilityPredicate(ParserUtil.parseAvailability(argMultimap
                    .getValue(PREFIX_AVAILABILITY).get()), builder);
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            generateTagPredicate(parseTags(argMultimap.getAllValues(PREFIX_TAG)).get(), builder);
        }
        if (argMultimap.getValue(PREFIX_TODAY_ATTENDANCE).isPresent()) {
            generateTodayAttendancePredicate(ParserUtil.parseTodayAttendance(argMultimap
                    .getValue(PREFIX_TODAY_ATTENDANCE).get()), builder);
        }
        if (argMultimap.getValue(PREFIX_TOTAL_ATTENDANCE).isPresent()) {
            generateTotalAttendancePredicate(ParserUtil.parseTotalAttendance(argMultimap
                    .getValue(PREFIX_TOTAL_ATTENDANCE).get()), builder);
        }
        return builder.build();
    }

    private void generateNamePredicate(Name name, Builder builder) {
        String nameWithNoSpaces = name.toString().toLowerCase().replace("\\s+", "");
        List<String> nameList = new ArrayList<>(Arrays.asList(nameWithNoSpaces));
        builder.withName(name);
        builder.withPredicate(new NameContainsKeywordsPredicate(nameList));
    }

    private void generatePhonePredicate(Phone phone, Builder builder) {
        List<Phone> phoneList = new ArrayList<>(Arrays.asList(phone));
        builder.withPhone(phone);
        builder.withPredicate(new PhoneContainsKeywordsPredicate(phoneList));
    }

    private void generateTagPredicate(List<Tag> tags, Builder builder) {
        builder.withTags(tags);
        builder.withPredicate(new TagsContainKeywordsPredicate(tags));
    }

    private void generateAvailabilityPredicate(Availability availability, Builder builder) {
        List<Availability> availabilityList = new ArrayList<>(Arrays.asList(availability));
        builder.withAvailability(availability);
        builder.withPredicate(new AvailabilityContainsKeywordsPredicate(availabilityList));
    }

    private void generateTodayAttendancePredicate(TodayAttendance todayAttendance, Builder builder) {
        List<TodayAttendance> todayAttendanceList = new ArrayList<>(Arrays.asList(todayAttendance));
        builder.withTodayAttendance(todayAttendance);
        builder.withPredicate(new TodayAttendanceContainsKeywordsPredicate(todayAttendanceList));
    }

    private void generateTotalAttendancePredicate(TotalAttendance totalAttendance, Builder builder) {
        List<TotalAttendance> totalAttendanceList = new ArrayList<>(Arrays.asList(totalAttendance));
        builder.withTotalAttendance(totalAttendance);
        builder.withPredicate(new TotalAttendanceContainsKeywordsPredicate(totalAttendanceList));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Optional<List<Tag>>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<List<Tag>>} containing zero tags.
     */
    private Optional<List<Tag>> parseTags(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("\\s+") ? Collections.emptySet() : tags;
        return Optional.of(List.copyOf(ParserUtil.parseTags(tagSet)));
    }
}
