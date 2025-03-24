package trackup.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("-n ");
    public static final Prefix PREFIX_PHONE = new Prefix("-p ");
    public static final Prefix PREFIX_EMAIL = new Prefix("-e ");
    public static final Prefix PREFIX_ADDRESS = new Prefix("-a ");
    public static final Prefix PREFIX_TAG = new Prefix("-t ");
    public static final Prefix PREFIX_CATEGORY = new Prefix("-c ");
    public static final Prefix PREFIX_EVENT_TITLE = new Prefix("-et ");
    public static final Prefix PREFIX_EVENT_START = new Prefix("-es ");
    public static final Prefix PREFIX_EVENT_END = new Prefix("-ee ");
    public static final Prefix PREFIX_EVENT_CONTACT = new Prefix("-ec ");
}
