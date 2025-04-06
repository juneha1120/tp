package trackup.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import trackup.logic.commands.ListCommand;
import trackup.logic.parser.exceptions.ParseException;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validCategory_success() throws Exception {
        assertTrue(parser.parse("Client") instanceof ListCommand);
    }

    @Test
    public void parse_invalidCategory_throwsParseException() {
        String invalidCategory = "Aliens";
        assertThrows(ParseException.class, () -> parser.parse(invalidCategory));
    }

    @Test
    public void parse_emptyArgs_success() throws Exception {
        assertTrue(parser.parse("") instanceof ListCommand);
    }
}
