package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Availability;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Imports a list of Persons from a given CSV file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the member data from a CSV file into SportsPA's data file.\n"
            + "There must be headers for the CSV file and they should be in this order:"
            + " NAME, PHONE, AVAILABILITY, TAGS\n"
            + "Parameters: KEYWORD CSV_FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " myFilePath.csv";
    public static final String MESSAGE_FILE_NOT_FOUND = "No CSV file called %s found.";
    public static final String MESSAGE_SUCCESS = "Successfully imported from CSV file!";

    private final String filePath;

    /**
     * Creates an ImportCommand object to add the given list of Persons.
     *
     * @param filePath the file path of the CSV file.
     */
    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ArrayList<Person> personList = parseCsv();
        for (Person person: personList) {
            if (model.hasPerson(person)) {
                Person personToReplace = model.getSamePerson(person);
                requireNonNull(personToReplace);
                Person personToAdd = new Person(person.getName(),
                        person.getPhone(),
                        person.getAvailability(),
                        person.getTodayAttendance(),
                        personToReplace.getTotalAttendance(),
                        personToReplace.getTags());

                model.setPerson(personToReplace, personToAdd);
            } else {
                model.addPerson(person);
            }
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Helper function to parse a CSV file.
     * Returns an {@code ArrayList<Person>} filled with members to be added.
     *
     * @return {@code ArrayList<Person>} object after parsing the CSV file.
     * @throws CommandException if no file is found or if the CSV file does not conform with the expected format
     */
    private ArrayList<Person> parseCsv() throws CommandException {
        String line;
        ArrayList<Person> personList = new ArrayList<>();
        try {
            FileReader importFileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(importFileReader);
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", 4);

                if (values.length < 4) {
                    throw new CommandException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
                }
                Name name = ParserUtil.parseName(values[0]);
                Phone phone = ParserUtil.parsePhone(values[1]);
                Availability availability = ParserUtil.parseAvailability(values[2]);
                Set<Tag> tags = parseTagCsv(values[3]);

                Person person = new Person(name, phone, availability, tags);
                personList.add(person);
            }
            br.close();
            return personList;
        } catch (FileNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, filePath));
        } catch (IOException e) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        } catch (ParseException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Helper function to parse the tags from a CSV file.
     * Returns a {@code Set<Tags>} object.
     *
     * @param tags the given tags from the CSV file.
     * @return the {@code Set<Tags>} object after being parsed.
     * @throws ParseException if the tags from the CSV file do not conform with the expected format.
     */
    private Set<Tag> parseTagCsv(String tags) throws ParseException {
        Set<Tag> tagSet;
        String trimmedTags;
        if (tags.isBlank()) {
            tagSet = new HashSet<>();
        } else if (tags.charAt(0) == '"') {
            trimmedTags = tags.substring(1, tags.length() - 1);
            List<String> tagList = Arrays.asList(trimmedTags.split(","));
            tagSet = ParserUtil.parseTags(tagList);
        } else {
            trimmedTags = tags;
            List<String> tagList = Arrays.asList(trimmedTags.split(","));
            tagSet = ParserUtil.parseTags(tagList);
        }
        return tagSet;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ImportCommand
                && filePath.equals(((ImportCommand) other).filePath));
    }
}
