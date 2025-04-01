---
layout: page
title: User Guide
---

**TrackUp** is a **desktop application for managing contacts and events** that is optimised for a CLI-first experience while still offering the benefits of a GUI. 

Designed for startup founders, **TrackUp** streamlines relationship management, follow-ups, and event organisation — helping you work efficiently without unnecessary distractions.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. **Check if Java is installed**<br>
TrackUp needs **Java** (version 17 or higher) to run.
   * **Mac users**: Follow [this](https://se-education.org/guides/tutorials/javaInstallationMac.html) guide to install Java properly.
   * **Windows users**: You can check if Java is installed by opening a command prompt and typing: `java -version`

2. **Download TrackUp**<br>
   Click [here](https://github.com/se-edu/AY2425S2-CS2103T-F14-4/tp/releases) to download the latest version of TrackUp.
   * Download the file that ends with `.jar`.

3. **Choose where to keep TrackUp**<br>
   Move the downloaded `.jar` file to any folder you like.
   * This folder will be where TrackUp stores its data.

4. **Open TrackUp**<br>
   * Open the **Terminal** (on Mac) or **Command Prompt** (on Windows).   
   * Go to the folder where you put the .jar file. To do that, type: `cd path-to-the-folder`  
   (Replace “path-to-the-folder” with the actual folder location)  
   * Then start the app by typing: `java -jar trackup.jar`  
   * After a few seconds, the application will open with a window like this:
   ![Ui](images/Ui.png)

5. **Try a command**<br>
   * Click on the command box at the top left of the application window.
   * Type a command like this: `help` and pressing Enter will open the help window.  
   * Here are a few more example commands you can try:
     * `list` - Lists all contacts.
     * `add -n John Doe -p 98765432 -e johnd@example.com -a John street, block 123, #01-01` - Adds a contact named `John Doe` to the Address Book.
     * `delete 3` - Deletes the 3rd contact shown in the current list.
     * `clear` - Deletes all contacts.
     * `exit` - Exits the app.

6. **Learn more**<br>
   Scroll down to the [Features](#features) section below to explore all the things TrackUp can do!

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- Words in `<UPPER_CASE>` indicate the parameters to be supplied by the user.
  e.g., in `add -n <NAME>`, `<NAME>` is a parameter which can be used as `add -n John Doe`.
- Items in square brackets are optional.
  e.g., `-c <CATEGORY>` is optional.
- The order of parameters is flexible.
  e.g., `add -n John Doe -p 98765432` is equivalent to `add -p 98765432 -n John Doe`.
- Extraneous parameters for commands that do not require any (such as `exit` and `clear`) will be ignored.
- When copying commands from this PDF version, ensure that line breaks do not remove necessary spaces.
</div>

### Viewing help : `help`

Shows a message to access the help page.  
Shows the list of all commands, or full usage when a specific command is given.

![help message](images/helpMessage.png)

Format: `help [<COMMAND_WORD>]`

**Notes:**
- `COMMAND_WORD` is **optional**.

**Examples:**
- `help`
- `help add` - displays full usage for add command
- `help delete` - displays full usage for delete command

### Adding a person: `add`

Adds a contact to TrackUp.

Format: `add -n <NAME> -p <PHONE> -e <EMAIL> -a <ADDRESS> [-c <CATEGORY>] [-t <TAG>]...`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

**Notes:**
- `NAME`, `PHONE`, `EMAIL`, and `ADDRESS` are **compulsory**.
- `CATEGORY` and `TAG` are **optional**.
- `CATEGORY` should be **one** of: Client, Investor, Partner, Other and is case-insensitive.
- If any required field (name, phone, email, or address) is missing, a descriptive **error** will appear indicating the exact missing attribute.

**Examples:**
- `add -n John Doe -p 98765432 -e johnd@example.com -a John street, block 123, #01-01 -c Client -t friend`
- `add -n Betsy Crowe -p 1234567 -e betsycrowe@example.com -a Newgate Prison`

### Listing all persons : `list`

Displays all contacts in TrackUp, optionally filtering by category.

Format: `list [<CATEGORY>]`

**Notes**
- `CATEGORY` is **optional** and **case-insensitive**.
- Displays **all** contacts if no category is specified.
- If a category is provided, **only** contacts from that category are shown.

**Examples:**
- `list` — displays all contacts.
- `list Client` — displays only contacts categorised as Client.
- `list Investor` — displays only contacts categorised as Investor.

### Sorting current displayed list: `sort`

Sorts displayed contacts in TrackUp by the given attributes of Person.

Format: `sort [-n <BOOLEAN>] [-p <BOOLEAN>] [-e <BOOLEAN>] [-a <BOOLEAN>] [-c <BOOLEAN>] [-t <BOOLEAN>]`

* Displays all contacts if no attribute is specified.
* If an attribute is provided, displays the result after sorting by the specified attribute.
* `<BOOLEAN>`: `true` or `false`, case-insensitive, to indicate if the sorting is descending.
* Prefixes for attributes: `-n`NAME `-p`PHONE `-e`EMAIL `-a`ADDRESS `-c`CATEGORY `-t`TAG

**Examples:**
- `sort` — displays all contacts.
- `sort -n true` — displays result list of contacts sorted by name in descending order.
- `sort -t -n true` — displays result list of contacts first sorted by tag in ascending order,
then sorted by name in descending order

### Editing a person : `edit`

Edits an existing person in TrackUp.

Format: `edit <INDEX> [-n <NAME>] [-p <PHONE>] [-e <EMAIL>] [-a <ADDRESS>] [-c <CATEGORY>] [-t <TAG>]...`

**Notes:**
- `INDEX` refers to the contact's index in the **current** displayed person list (must be a positive integer).
- **At least one** of the optional fields must be provided.
- When editing tags, the existing tags of the person will be removed i.e. adding of tags is not **cumulative**. 
- You can **remove** all the person’s tags by typing `-t` without specifying any tags after it.

**Examples:**
- `edit 1 -p 91234567 -e johnd@example.com` — updates the phone number and email of the first contact.
- `edit 2 -n "Betsy Crower" -c Investor` — updates the name and sets the category to Investor for the second contact.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

**Notes:**
- The search is **case-insensitive**. e.g. `hans` will match `Hans`.
- The **order** of keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
- Only the **name** attribute is searched.
- Only **full word** matches are considered. e.g. `Han` will not match `Hans`.
- Persons matching **at least one** keyword will be returned (i.e., an OR search).

**Examples:**
- `find John` - returns persons with name such as `John Doe`.
- `find alex david` - returns persons with names such as  `Alex Yeoh` and `David Li`.
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified contact from TrackUp.

Format: `delete <INDEX>`

**Notes:**
- Deletes the person at the specified `INDEX`.
- `INDEX` refers to the contact's index in the **current** list (must be a positive integer).

**Examples:**
- `delete 3` — deletes the third contact in the current list.
- `list` followed by `delete 2` - deletes the 2nd person in the address book.
- `find Betsy` followed by `delete 1` - deletes the 1st person in the results of the `find` command.

### Deleting a person by attributes: `deleteby`

Deletes a contact from TrackUp using one or more attributes.

Format: `deleteby [-n <NAME>] [-p <PHONE>] [-e <EMAIL>] [-a <ADDRESS>] [-c <CATEGORY>] [-t <TAG>]`

**Notes:**
- Deletes the person that matches the provided attributes.
- **At least one** attribute must be specified.
- Attribute matching is exact and **case-sensitive**.
- If multiple contacts match the criteria, the system will display an **error** message
  stating that multiple matches were found and that no contact will be deleted.

**Examples:**
- `deleteby -n John Doe` - deletes the person named **John Doe** from the address book.
- `deleteby -p 98765432` - deletes the person with phone number **98765432**.
- `deleteby -e johnd@example.com` - deletes the person with email **johnd@example.com**.
- `deleteby -a 311, Clementi Ave 2, #02-25` - deletes the person living at **311, Clementi Ave 2, #02-25**.
- `deleteby -c Client` - deletes the person with the category **Client**.
- `deleteby -n John Doe -p 98765432` - deletes the person named **John Doe** with phone number **98765432**.

### Searching for a person: `search`

Finds persons whose name, phone, email, address, tags, or category contain the given keyword.

Format: `search <KEYWORD>`

**Notes:**
* The search is **case-insensitive**. e.g., `john` will match `John`.
* **Partial** matches are supported. e.g., `son` will match `Johnson`.
* The search applies to **all attributes** (name, phone, email, address, tags, and category).
* Persons matching the keyword will be returned.

**Examples:**
- `search John` - returns persons with names such as **John Doe** and **Johnny Smith**.
- `search 98765432` - returns persons with the phone number **98765432**.
- `search johnd@example.com` - returns persons with the email **johnd@example.com**.
- `search Clementi` - returns persons whose address contains **Clementi**.
- `search friends` - returns persons who have the tag **friends**.
- `search client` - returns persons categorised as **Client**.
- `search doe` - returns persons whose attributes contain **"doe"**, such as **John Doe** and **johndoe@example.com**.

### Adding an event : `addevent`

Adds an event to TrackUp's calendar.

Format: `addevent -t <EVENT_TITLE> -s <START_DATETIME> -e <END_DATETIME> [-c <CONTACT_INDEX>]...`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An event can have any number of contacts linked (including 0)
</div>

**Notes:**
* `EVENT_TITLE`, `START_DATETIME`, and `END_DATETIME` are **compulsory**.
* `CONTACT_INDEX` is **optional**.
* `START_DATETIME` and `END_DATETIME` must be in **YYYY-MM-DD HH:MM** format.

**Examples:**
- `addevent -t "Team Meeting" -s 2025-03-30 14:00 -e 2025-03-30 15:00 -c 1 -c 3` - adds a "Team Meeting" from 14:00 to 15:00 on March 30, 2025, linking it to contacts at index 1 and 3.
- `addevent -t "Project Deadline" -s 2025-04-01 23:59 -e 2025-04-02 00:00` - adds a "Project Deadline" event without linking any contacts.

### Deleting an event : `delevent`

Deletes events from TrackUp's calendar based on specified filters.

Format: `delevent [-t <TITLE_KEYWORD>] [-s <START_DATETIME>] [-e <END_DATETIME>] [-c <CONTACT_INDEX>]...`

**Notes:**
* At least **one** filter must be provided.
* `TITLE_KEYWORD` performs a case-insensitive partial match on event titles.
* `START_DATETIME` and `END_DATETIME` require an exact match (**YYYY-MM-DD HH:MM** format).
* `CONTACT_INDEX` matches events linked to the specified contacts.
* **All** matching events will be deleted.
* If no matching events are found, an **error** message is displayed.

**Examples:**
* `delevent -t Meeting` - deletes all events with "Meeting" in the title.
* `delevent -s 2025-03-30 14:00 -e 2025-03-30 15:00` - deletes all events exactly matching this start and end time.
* `delevent -c 2` - deletes all events linked to the contact at index 2.
* `delevent -t "Workshop" -c 1 -c 4` - deletes all events with "Workshop" in the title that are linked to contacts at index 1 or 4.

### Adding a note to a person: `addnote`

Adds a short note to the specified person in TrackUp.

Format: `addnote <PERSON_INDEX> <NOTE_TEXT>`

**Details:**
- `<PERSON_INDEX>` refers to the contact’s index in the currently displayed list (must be a positive integer).
- Each person can have **up to 3 notes**.
- Notes should be **short and descriptive**. The maximum note length is 50 characters.
- Notes are displayed beneath the person’s details in the UI.

**Examples:**
- `addnote 1 Met at tech networking event`
- `addnote 2 Follow up next week regarding proposal`

### Deleting a note from a person: `delnote`

Deletes a specific note from a person in TrackUp.

Format: `delnote <PERSON_INDEX> <NOTE_INDEX>`

**Details:**
- `<PERSON_INDEX>` refers to the person in the currently displayed list.
- `<NOTE_INDEX>` refers to the position of the note in that person’s list of notes (must be a positive integer).
- Notes are displayed in order; the first note is index 1.

**Examples:**
- `delnote 2 1` — deletes the **first** note from the **second** person in the list.
- `find John` followed by `delnote 1 2` — deletes the **second** note from the **first person** in the search results for "John".

### Toggling field visibility: `toggle`

Toggles the visibility of a specific field in the TrackUp contact list UI.  
This command is useful for customizing the information you want displayed for each contact.

Format: `toggle <FIELD>`

**Supported Fields:**
- `name`
- `phone`
- `email`
- `address`
- `tag`
- `category`
- `note`
- `datetime`

**Details:**
- Each field starts out **visible by default**.
- Executing the command will **invert the visibility** of the given field.
- Toggled fields affect the **main contact list display**.
- Repeating the command for the same field will toggle it back.

**Examples:**
- `toggle name` hides the contact's name field if it's currently shown.
- `toggle phone` shows the phone field again if it was previously hidden.
- `toggle note` hides the note field in the contact display.
- `toggle datetime` toggles the visibility of the datetime field.

### Clearing all entries : `clear`

Clears all contacts from TrackUp.

Format: `clear`

### Exiting the program : `exit`

Exits TrackUp.

Format: `exit`

## Saving and Editing Data

- **Saving the Data:**
  TrackUp automatically saves data as a JSON file in your home folder after any command that changes the data.

- **Editing the Data File:**
  The data is stored as a JSON file (e.g., `[JAR file location]/data/trackup.json`). Advanced users can edit this file directly, but be sure to back up the file first.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Editing the data file incorrectly may cause TrackUp to discard all data or behave unexpectedly.
</div>

### Archiving data files `[coming in v2.0]`



--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install TrackUp on the other computer and replace the empty data file it creates with your backup from your previous TrackUp home folder.

--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **Multi-screen Usage:**
   When using multiple screens, if you move the application between screens, the GUI might open off-screen on some setups. Delete the `preferences.json` file in your TrackUp folder to reset the display settings.

2. **Help Window Behavior:**
   If you minimize the Help Window and then issue the `help` command again, the original Help Window may remain minimized. Manually restore the window to view the help message.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

| **Action**               | **Format, Examples**                                                                                                                                                                              |
|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**                  | `add -n <NAME> -p <PHONE> -e <EMAIL> -a <ADDRESS> [-c <CATEGORY>]  [-t <TAG>]`<br>e.g., `add -n "James Ho" -p 22224444 -e jamesho@example.com -a "123, Clementi Rd, 1234665" -c Client -t friend` |
| **Clear**                | `clear`                                                                                                                                                                                           |
| **Delete**               | `delete <INDEX>`<br>e.g., `delete 3`                                                                                                                                                              |
| **Delete by Attributes** | `deleteby [-n <NAME>] [-p <PHONE>] [-e <EMAIL>] [-a <ADDRESS>] [-c <CATEGORY>] [-t <TAG>]`<br>e.g., `deleteby -n "John Doe"`                                                                      |
| **Edit**                 | `edit <INDEX> [-n <NAME>] [-p <PHONE>] [-e <EMAIL>] [-a <ADDRESS>] [-c <CATEGORY>] [-t <TAG>]`<br>e.g., `edit 2 -n "James Lee" -e jameslee@example.com`                                           |
| **Find**                 | `find <KEYWORD>`<br>e.g., `find John`                                                                                                                                                             |
| **List**                 | `list [<CATEGORY>]`<br>e.g., `list`, `list Client`                                                                                                                                                |
| **Search**               | `search <KEYWORD>`<br>e.g., `search John`, `search Client`                                                                                                                                        |
| **Help**                 | `help`                                                                                                                                                                                            |
| **Exit**                 | `exit`                                                                                                                                                                                            |
