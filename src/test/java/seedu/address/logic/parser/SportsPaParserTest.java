package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.model.member.MemberMatchesKeywordsPredicate.Builder;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAliasCommand;
import seedu.address.logic.commands.AddFacilityCommand;
import seedu.address.logic.commands.AddMemberCommand;
import seedu.address.logic.commands.ClearAttendanceCommand;
import seedu.address.logic.commands.ClearFacilitiesCommand;
import seedu.address.logic.commands.ClearMembersCommand;
import seedu.address.logic.commands.DeleteAliasCommand;
import seedu.address.logic.commands.DeleteFacilityCommand;
import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.commands.EditFacilityCommand;
import seedu.address.logic.commands.EditMemberCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindFacilityCommand;
import seedu.address.logic.commands.FindMemberCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListFacilityCommand;
import seedu.address.logic.commands.ListMemberCommand;
import seedu.address.logic.commands.ShowAliasesCommand;
import seedu.address.logic.commands.SortMemberCommand;
import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasMap;
import seedu.address.model.alias.CommandWord;
import seedu.address.model.alias.Shortcut;
import seedu.address.model.facility.Facility;
import seedu.address.model.facility.LocationContainsKeywordsPredicate;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberMatchesKeywordsPredicate;
import seedu.address.model.member.Name;
import seedu.address.model.member.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditFacilityDescriptorBuilder;
import seedu.address.testutil.EditMemberDescriptorBuilder;
import seedu.address.testutil.FacilityBuilder;
import seedu.address.testutil.FacilityUtil;
import seedu.address.testutil.MemberBuilder;
import seedu.address.testutil.MemberUtil;

public class SportsPaParserTest {

    private final SportsPaParser parser = new SportsPaParser();
    private final AliasMap aliases = new AliasMap();

    @Test
    public void parseCommand_addMember() throws Exception {
        Member member = new MemberBuilder().build();
        AddMemberCommand command =
                (AddMemberCommand) parser.parseCommand(MemberUtil.getAddMemberCommand(member), aliases);
        assertEquals(new AddMemberCommand(member), command);
    }

    @Test
    public void parseCommand_addFacility() throws ParseException {
        Facility facility = new FacilityBuilder()
                .withFacilityName("Court 1")
                .withLocation("University Sports Hall")
                .withCapacity("5")
                .withTime("1130").build();
        AddFacilityCommand command = (AddFacilityCommand) parser.parseCommand("addf "
                + "n/Court 1 l/University Sports Hall t/1130 c/5", aliases);
        assertEquals(new AddFacilityCommand(facility), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearMembersCommand.COMMAND_WORD, aliases) instanceof ClearMembersCommand);
        assertTrue(parser.parseCommand(ClearMembersCommand.COMMAND_WORD + " 3", aliases)
                instanceof ClearMembersCommand);
    }

    @Test
    public void parseCommand_cleara() throws ParseException {
        assertTrue(parser.parseCommand(ClearAttendanceCommand.COMMAND_WORD, aliases) instanceof ClearAttendanceCommand);
        assertTrue(parser.parseCommand(ClearAttendanceCommand.COMMAND_WORD
                + " 1", aliases) instanceof ClearAttendanceCommand);
    }

    @Test
    public void parseCommand_clearf() throws Exception {
        assertTrue(parser.parseCommand(ClearFacilitiesCommand.COMMAND_WORD, aliases) instanceof ClearFacilitiesCommand);
        assertTrue(parser.parseCommand(ClearFacilitiesCommand.COMMAND_WORD + " 3", aliases)
                instanceof ClearFacilitiesCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteMemberCommand command = (DeleteMemberCommand) parser.parseCommand(
                DeleteMemberCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), aliases);
        assertEquals(new DeleteMemberCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deletef() throws Exception {
        DeleteFacilityCommand command = (DeleteFacilityCommand) parser.parseCommand(
                DeleteFacilityCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), aliases);
        assertEquals(new DeleteFacilityCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Member member = new MemberBuilder().build();
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(member).build();
        EditMemberCommand command = (EditMemberCommand) parser.parseCommand(EditMemberCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + MemberUtil.getEditPersonDescriptorDetails(descriptor), aliases);
        assertEquals(new EditMemberCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editf() throws Exception {
        Facility facility = new FacilityBuilder().build();
        EditFacilityCommand.EditFacilityDescriptor descriptor = new EditFacilityDescriptorBuilder(facility).build();
        EditFacilityCommand command = (EditFacilityCommand) parser.parseCommand(
                EditFacilityCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                        + FacilityUtil.getEditFacilityDescriptorDetails(descriptor), aliases);
        assertEquals(new EditFacilityCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, aliases) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", aliases) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findMember() throws Exception {
        Name name = new Name("baz");
        FindMemberCommand command = (FindMemberCommand) parser.parseCommand(
                FindMemberCommand.COMMAND_WORD + " n/baz", aliases);
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("baz"));
        MemberMatchesKeywordsPredicate testPredicate =
                new Builder().withName(name).withPredicate(predicate).build();
        assertEquals(new FindMemberCommand(testPredicate), command);
    }

    @Test
    public void parseCommand_findFacility() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindFacilityCommand command = (FindFacilityCommand) parser.parseCommand(
                FindFacilityCommand.COMMAND_WORD
                        + " " + keywords.stream().collect(Collectors.joining(" ")), aliases);
        assertEquals(new FindFacilityCommand(new LocationContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, aliases) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", aliases) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listm() throws Exception {
        assertTrue(parser.parseCommand(ListMemberCommand.COMMAND_WORD, aliases) instanceof ListMemberCommand);
        assertTrue(parser.parseCommand(ListMemberCommand.COMMAND_WORD + " 3", aliases) instanceof ListMemberCommand);
    }

    @Test
    public void parseCommand_listf() throws Exception {
        assertTrue(parser.parseCommand(ListFacilityCommand.COMMAND_WORD, aliases) instanceof ListFacilityCommand);
        assertTrue(parser.parseCommand(
                ListFacilityCommand.COMMAND_WORD + " 3", aliases) instanceof ListFacilityCommand);
    }

    @Test
    public void parseCommand_split() throws Exception {
        assertEquals(new SplitCommand(1),
                parser.parseCommand(SplitCommand.COMMAND_WORD + " 1", aliases));
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        AddAliasCommand command = (AddAliasCommand) parser
                .parseCommand(AddAliasCommand.COMMAND_WORD + " s/lf cw/listf", aliases);
        assertEquals(new AddAliasCommand(new Alias(new Shortcut("lf"), new CommandWord("listf"))), command);
    }

    @Test
    public void parseCommand_listfInputIsAnAlias_success() throws Exception {
        String alias = "lf";
        AliasMap aliases = new AliasMap();
        aliases.add(new Alias(new Shortcut(alias), new CommandWord("listf")));
        ListFacilityCommand command = (ListFacilityCommand) parser.parseCommand(alias, aliases);
        assertTrue(command instanceof ListFacilityCommand);
    }

    @Test
    public void parseCommand_addmInputIsAnAlias_success() throws Exception {
        String alias = "af";
        AliasMap aliases = new AliasMap();
        aliases.add(new Alias(new Shortcut(alias), new CommandWord("addf")));
        Facility facility = new FacilityBuilder()
                .withFacilityName("Court 1")
                .withLocation("University Sports Hall")
                .withCapacity("5")
                .withTime("1130").build();
        AddFacilityCommand command = (AddFacilityCommand) parser.parseCommand("af "
                + "n/Court 1 l/University Sports Hall t/1130 c/5", aliases);
        assertEquals(new AddFacilityCommand(facility), command);
    }

    @Test
    public void parseCommand_deleteAlias_success() throws Exception {
        DeleteAliasCommand command = (DeleteAliasCommand) parser
                .parseCommand(DeleteAliasCommand.COMMAND_WORD + " lf", aliases);
        assertEquals(new DeleteAliasCommand(new Shortcut("lf")), command);
    }

    @Test
    public void parseCommand_showAlias_success() throws Exception {
        assertTrue(parser.parseCommand(ShowAliasesCommand.COMMAND_WORD, aliases) instanceof ShowAliasesCommand);
        assertTrue(parser.parseCommand(
                ShowAliasesCommand.COMMAND_WORD + " 3", aliases) instanceof ShowAliasesCommand);
    }

    @Test
    public void parseCommand_importCommand() throws Exception {
        String filePath = " src/test/data/ImportCommandParserTest/typicalImportFile.csv";
        assertTrue(parser
                .parseCommand(ImportCommand.COMMAND_WORD + filePath, aliases) instanceof ImportCommand);
    }

    @Test
    public void parseCommand_exportCommand() throws Exception {
        assertTrue(parser.parseCommand(ExportCommand.COMMAND_WORD, aliases) instanceof ExportCommand);
        assertTrue(parser.parseCommand(
                ExportCommand.COMMAND_WORD + " 3", aliases) instanceof ExportCommand);
    }

    @Test
    public void parseCommand_sortm() throws Exception {
        assertTrue(parser.parseCommand(SortMemberCommand.COMMAND_WORD + " by/name", aliases)
                instanceof SortMemberCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", aliases));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand", aliases));
    }
}
