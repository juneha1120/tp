package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpCommandParser implements Parser<HelpCommand> {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public HelpCommand parse(String args) throws ParseException {

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new HelpCommand();
        }

        final String commandWord = matcher.group("commandWord");

        switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                return new HelpCommand(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE,
                        "Adds a new contact to the database with optional details.");

            case DeleteCommand.COMMAND_WORD:
                return new HelpCommand(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE,
                        "Deletes a existing contact from the database with optional details.");

            case ListCommand.COMMAND_WORD:
                return new HelpCommand(ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
                        "Lists out the contact in the database with optional category.");

            default:
                return new HelpCommand();
        }
    }

}
