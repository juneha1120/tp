package trackup.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackup.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackup.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static trackup.logic.parser.CliSyntax.PREFIX_EVENT_CONTACT;
import static trackup.logic.parser.CliSyntax.PREFIX_NAME;
import static trackup.testutil.Assert.assertThrows;
import static trackup.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static trackup.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import trackup.commons.core.index.Index;
import trackup.logic.commands.AddCommand;
import trackup.logic.commands.AddEventCommand;
import trackup.logic.commands.AddNoteCommand;
import trackup.logic.commands.ClearCommand;
import trackup.logic.commands.DeleteByCommand;
import trackup.logic.commands.DeleteCommand;
import trackup.logic.commands.DeleteEventCommand;
import trackup.logic.commands.DeleteNoteCommand;
import trackup.logic.commands.EditCommand;
import trackup.logic.commands.EditCommand.EditPersonDescriptor;
import trackup.logic.commands.ExitCommand;
import trackup.logic.commands.FindCommand;
import trackup.logic.commands.HelpCommand;
import trackup.logic.commands.ListCommand;
import trackup.logic.commands.SearchCommand;
import trackup.logic.commands.ToggleCommand;
import trackup.logic.parser.exceptions.ParseException;
import trackup.model.event.Event;
import trackup.model.person.Name;
import trackup.model.person.NameContainsKeywordsPredicate;
import trackup.model.person.Person;
import trackup.testutil.EditPersonDescriptorBuilder;
import trackup.testutil.EventBuilder;
import trackup.testutil.EventUtil;
import trackup.testutil.PersonBuilder;
import trackup.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().withCategory("Other").build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteby() throws Exception {
        Name name = new Name("John");
        DeleteByCommand command = (DeleteByCommand) parser.parseCommand(
                DeleteByCommand.COMMAND_WORD + " " + PREFIX_NAME + name.fullName);
        assertEquals(new DeleteByCommand(Optional.of(name), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " Client") instanceof ListCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SearchCommand.MESSAGE_USAGE), () -> parser.parseCommand(SearchCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(SearchCommand.COMMAND_WORD + " alice") instanceof SearchCommand);
    }

    @Test
    public void parseCommand_toggle() throws Exception {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ToggleCommand.MESSAGE_USAGE), () -> parser.parseCommand(ToggleCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(ToggleCommand.COMMAND_WORD + " " + ToggleCommand.NAME_FIELD_STRING)
                instanceof ToggleCommand);
    }

    @Test
    public void parseCommand_addevent() throws Exception {
        Event event = new EventBuilder()
                .withTitle("Team Meeting")
                .withStart(LocalDateTime.of(2025, 4, 1, 14, 0))
                .withEnd(LocalDateTime.of(2025, 4, 1, 15, 0))
                .withContacts(new HashSet<>()) // use indexes for linking below
                .build();

        Set<Index> contactIndexes = Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        AddEventCommand command = (AddEventCommand) parser.parseCommand(
                EventUtil.getAddEventCommand(event, contactIndexes));
        AddEventCommand expectedCommand = new AddEventCommand(
                event.getTitle(),
                event.getStartDateTime(),
                event.getEndDateTime(),
                contactIndexes
        );
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_delevent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " "
                        + PREFIX_EVENT_CONTACT + "1 " + PREFIX_EVENT_CONTACT + "2");
        DeleteEventCommand expected = new DeleteEventCommand(
                null, null, null,
                Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        assertEquals(expected, command);
    }


    @Test
    public void parseCommand_addnote() throws Exception {
        AddNoteCommand command = (AddNoteCommand) parser.parseCommand(
                AddNoteCommand.COMMAND_WORD + " 1 Met at conference, follow up next week");

        assertEquals(new AddNoteCommand(Index.fromOneBased(1),
                "Met at conference, follow up next week"), command);
    }

    @Test
    public void parseCommand_delnote() throws Exception {
        DeleteNoteCommand command = (DeleteNoteCommand) parser.parseCommand(
                DeleteNoteCommand.COMMAND_WORD + " 1 2");

        assertEquals(new DeleteNoteCommand(Index.fromOneBased(1), Index.fromOneBased(2)), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
