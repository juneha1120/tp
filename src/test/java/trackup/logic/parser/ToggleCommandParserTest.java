package trackup.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import trackup.logic.commands.ToggleCommand;
import trackup.logic.parser.exceptions.ParseException;

public class ToggleCommandParserTest {

    private final ToggleCommandParser parser = new ToggleCommandParser();

    @Test
    public void parse_validFieldName_returnsToggleCommand() throws Exception {
        assertEquals(new ToggleCommand("name"), parser.parse("name"));
        assertEquals(new ToggleCommand("phone"), parser.parse("phone"));
        assertEquals(new ToggleCommand("email"), parser.parse("email"));
        assertEquals(new ToggleCommand("address"), parser.parse("address"));
        assertEquals(new ToggleCommand("tag"), parser.parse("tag"));
        assertEquals(new ToggleCommand("category"), parser.parse("category"));
    }

    @Test
    public void parse_validFieldNameWithExtraWhitespace_returnsToggleCommand() throws Exception {
        assertEquals(new ToggleCommand("name"), parser.parse("  name  "));
        assertEquals(new ToggleCommand("email"), parser.parse("\temail\n"));
    }

    @Test
    public void parse_validFieldNameWithCapitalization_returnsToggleCommand() throws Exception {
        assertEquals(new ToggleCommand("name"), parser.parse("NaMe"));
        assertEquals(new ToggleCommand("tag"), parser.parse("TAG")); // input is normalized to lowercase
    }

    @Test
    public void parse_invalidFieldName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("invalidField"));
        assertThrows(ParseException.class, () -> parser.parse("123"));
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse(" "));
    }
}
