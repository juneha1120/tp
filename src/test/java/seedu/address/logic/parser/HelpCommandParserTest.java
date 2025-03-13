package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_validArgs_returnsHelpCommand() throws ParseException {
        assertNotNull(parser.parse(""));
        HelpCommand expectedHelpCommand = new HelpCommand();
        assertParseSuccess(parser, " ", expectedHelpCommand);
        //can't pass, to be fixed
    }

}
