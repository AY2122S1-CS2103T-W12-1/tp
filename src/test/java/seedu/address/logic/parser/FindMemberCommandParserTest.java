package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AVAILABILITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAILABILITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREAMBLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_EXCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EXCO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.member.MemberMatchesKeywordsPredicate.Builder;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindMemberCommand;
import seedu.address.model.member.Availability;
import seedu.address.model.member.AvailabilityContainsKeywordsPredicate;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberMatchesKeywordsPredicate;
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

public class FindMemberCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMemberCommand.MESSAGE_USAGE);

    private FindMemberCommandParser parser = new FindMemberCommandParser();

    // valid name keywords
    private final Name amyName = new Name(VALID_NAME_AMY);
    private final Name bobName = new Name(VALID_NAME_BOB);

    // valid phone
    private final Phone phone = new Phone(VALID_PHONE_AMY);

    // valid tags
    private final Tag excoTag = new Tag(VALID_TAG_EXCO);

    // valid availability
    private final Availability availability =
            new Availability(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));

    // valid today attendance
    private final TodayAttendance todayAttendance = new TodayAttendance(true);

    // valid total attendance
    private final TotalAttendance totalAttendance = new TotalAttendance(3);

    // predicates
    private final Predicate<Member> namePredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList(VALID_NAME_AMY));
    private final Predicate<Member> phonePredicate =
            new PhoneContainsKeywordsPredicate(Collections.singletonList(phone));
    private final Predicate<Member> singleTagPredicate =
            new TagsContainKeywordsPredicate(Collections.singletonList(excoTag));
    private final Predicate<Member> availabilityPredicate =
            new AvailabilityContainsKeywordsPredicate(Collections.singletonList(availability));
    private final Predicate<Member> todayAttendancePredicate =
            new TodayAttendanceContainsKeywordsPredicate(Collections.singletonList(todayAttendance));
    private final Predicate<Member> totalAttendancePredicate =
            new TotalAttendanceContainsKeywordsPredicate(Collections.singletonList(totalAttendance));

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMemberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // valid name
        MemberMatchesKeywordsPredicate testPredicate =
                new Builder().withName(amyName).withPredicate(namePredicate).build();
        FindMemberCommand expectedCommand = new FindMemberCommand(testPredicate);
        assertParseSuccess(parser, NAME_DESC_AMY, expectedCommand);

        // valid phone
        testPredicate = new Builder().withPhone(phone).withPredicate(phonePredicate).build();
        expectedCommand = new FindMemberCommand(testPredicate);
        assertParseSuccess(parser, PHONE_DESC_AMY, expectedCommand);

        // valid tag
        testPredicate = new Builder().withTags(Collections.singletonList(excoTag))
                .withPredicate(singleTagPredicate).build();
        expectedCommand = new FindMemberCommand(testPredicate);
        assertParseSuccess(parser, TAG_DESC_EXCO, expectedCommand);

        // valid availability
        testPredicate = new Builder()
                .withAvailability(availability).withPredicate(availabilityPredicate).build();
        expectedCommand = new FindMemberCommand(testPredicate);
        assertParseSuccess(parser, AVAILABILITY_DESC_AMY, expectedCommand);

        // valid today attendance
        testPredicate = new MemberMatchesKeywordsPredicate.Builder()
                .withTodayAttendance(todayAttendance).withPredicate(todayAttendancePredicate).build();
        expectedCommand = new FindMemberCommand(testPredicate);
        assertParseSuccess(parser, " tda/true", expectedCommand);

        // valid total attendance
        testPredicate = new Builder().withTotalAttendance(totalAttendance)
                .withPredicate(totalAttendancePredicate).build();
        expectedCommand = new FindMemberCommand(testPredicate);
        assertParseSuccess(parser, " tta/3", expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        MemberMatchesKeywordsPredicate testPredicate = new MemberMatchesKeywordsPredicate.Builder().withName(amyName)
                        .withAvailability(availability).withPredicate(namePredicate)
                .withPredicate(availabilityPredicate).build();
        FindMemberCommand expectedCommand = new FindMemberCommand(testPredicate);

        assertParseSuccess(parser, NAME_DESC_AMY + AVAILABILITY_DESC_AMY, expectedCommand);
        // switch order
        assertParseSuccess(parser, AVAILABILITY_DESC_AMY + NAME_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        MemberMatchesKeywordsPredicate testPredicate =
                new MemberMatchesKeywordsPredicate.Builder().withName(amyName).withPredicate(namePredicate).build();
        FindMemberCommand expectedCommand = new FindMemberCommand(testPredicate);

        assertParseSuccess(parser, INVALID_NAME_DESC + NAME_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        MemberMatchesKeywordsPredicate testPredicate =
                new Builder().withName(amyName).withPredicate(namePredicate).build();
        FindMemberCommand expectedCommand = new FindMemberCommand(testPredicate);

        assertParseSuccess(parser, NAME_DESC_BOB + NAME_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // invalid tag argument ahead of valid tag
        assertParseFailure(parser, INVALID_TAG_DESC + TAG_DESC_EXCO, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, NAME_DESC_AMY + INVALID_AVAILABILITY_DESC + INVALID_TAG_DESC,
                Availability.MESSAGE_CONSTRAINTS);

        // empty field
        assertParseFailure(parser, " n/  ", Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, INVALID_PREAMBLE, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_INVALID_FORMAT);
    }
}
