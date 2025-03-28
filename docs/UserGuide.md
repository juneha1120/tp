---
layout: page
title: User Guide
---

**TrackUp** is a **desktop app for managing contacts and events** that is optimized for a CLI-first experience while still offering the benefits of a GUI. Designed for business owners and startup founders, TrackUp streamlines relationship management, follow-ups, and event organization—helping you work efficiently without unnecessary distractions.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/se-edu/AY2425S2-CS2103T-F14-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your TrackUp.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar trackup.jar` command to run the application.<br>

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add -n John Doe -p 98765432 -e johnd@example.com -a John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- Words in `UPPER_CASE` indicate the parameters to be supplied by the user.
  e.g., in `add -n <NAME>`, `<NAME>` is a parameter which can be used as `add -n "John Doe"`.
- Items in square brackets are optional.
  e.g., `-c <CATEGORY>` is optional.
- The order of parameters is flexible.
  e.g., `add -n "John Doe" -p 98765432` is equivalent to `add -p 98765432 -n "John Doe"`.
- Extraneous parameters for commands that do not require any (such as `help`, `list`, `exit`, and `clear`) will be ignored.
- When copying commands from this PDF version, ensure that line breaks do not remove necessary spaces.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help` `help COMMAND_WORD`

**Examples:**
- `help`
- `help add`
- `help delete`

### Adding a person: `add`

Adds a contact to TrackUp.

Format: `add -n <NAME> -p <PHONE> -e <EMAIL> -a <ADDRESS> [-c <CATEGORY>] [-t <TAG>]`

**Notes:**
- **NAME**, **PHONE**, **EMAIL**, and **ADDRESS** are compulsory.
- **CATEGORY** and **TAG** are optional.
- **CATEGORY** should be one of: Client, Investor, Partner, Other.


**Examples:**
- `add -n "John Doe" -p 98765432 -e johnd@example.com -a "John street, block 123, #01-01" -c Client -t friend`
- `add -n "Betsy Crowe" -p 1234567 -e betsycrowe@example.com -a "Newgate Prison`

### Listing all persons : `list`

Displays all contacts in TrackUp, optionally filtering by category.
Format: `list [<CATEGORY>]`

* Displays all contacts if no category is specified.
* If a category is provided, only contacts from that category are shown.
* Categories are case-insensitive.

**Examples:**
- `list` — displays all contacts.
- `list Client` — displays only contacts categorized as Client.
- `list Investor` — displays only contacts categorized as Investor.

### Editing a person : `edit`

Edits an existing person in TrackUp.

Format: `edit <INDEX> [-n <NAME>] [-p <PHONE>] [-e <EMAIL>] [-a <ADDRESS>] [-c <CATEGORY>] [-t <TAG>]`

**Notes:**
- `<INDEX>` refers to the contact's index in the current list (must be a positive integer).
- At least one of the optional fields must be provided.
- **CATEGORY** and **TAG** are optional.

**Examples:**
- `edit 1 -p 91234567 -e johnd@example.com` — updates the phone number and email of the first contact.
- `edit 2 -n "Betsy Crower" -c Investor` — updates the name and sets the category to Investor for the second contact.

---

### Locating persons by name: `find`

Finds and lists all contacts in TrackUp whose names contain any of the specified keywords. Keyword matching is performed in a case-insensitive manner, and the search is based solely on the names of contacts.

Format: `find KEYWORD [MORE_KEYWORDS]`

**Details:**
- The search is **case-insensitive**. For example, `hans` will match `Hans`.
- The order of keywords does not matter. For example, `Hans Bo` will match contacts with names like `Bo Hans`.
- Only the **name** attribute is searched.
- Only **full word** matches are considered. For instance, searching for `Han` will not match a name containing `Hans`.
- The command returns contacts that match **at least one** of the keywords (i.e., an OR search).

**Examples:**
- `find John` may return contacts such as `John Doe`.
- `find alex david` may return contacts like `Alex Yeoh` and `David Li`.
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified contact from TrackUp.

Format: `delete <INDEX>`

**Notes:**
- Deletes the person at the specified `INDEX`.
- `<INDEX>` refers to the contact's index in the current list (must be a positive integer).

**Examples:**
- `delete 3` — deletes the third contact in the current list.
- `list` followed by `delete 2` deletes the 2nd person in the address book.
- `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Deleting a person by attributes: `deleteby`

Deletes a contact from TrackUp using one or more attributes.

Format: `deleteby [-n <NAME>] [-p <PHONE>] [-e <EMAIL>] [-a <ADDRESS>] [-c <CATEGORY>] [-t <TAG>]`

**Notes:**
- Deletes the person that matches the provided attributes.
- At least one attribute must be specified.
- Attribute matching is exact and case-sensitive.
- If multiple contacts match the criteria, the system will display a message instead of deleting any contact.

**Examples:**
- `deleteby -n John Doe` deletes the person named **John Doe** from the address book.
- `deleteby -p 98765432` deletes the person with phone number **98765432**.
- `deleteby -e johnd@example.com` deletes the person with email **johnd@example.com**.
- `deleteby -a 311, Clementi Ave 2, #02-25` deletes the person living at **311, Clementi Ave 2, #02-25**.
- `deleteby -c Client` deletes the person with the category **Client**.
- `deleteby -n John Doe -p 98765432` deletes the person named **John Doe** with phone number **98765432**.

### Searching for a person: `search`

Finds persons whose **name, phone, email, address, tags, or category** contain the specified keyword.

Format: `search <KEYWORD>`

**Notes:**
* The search is **case-insensitive**. e.g., `john` will match `John`.
* Partial matches are supported. e.g., `son` will match `Johnson`.
* The search applies to **all attributes** (name, phone, email, address, tags, and category).
* Persons matching the keyword will be returned.

**Examples:**
- `search John` returns persons with names like **John Doe** and **Johnny Smith**.
- `search 98765432` returns persons with the phone number **98765432**.
- `search johnd@example.com` returns persons with the email **johnd@example.com**.
- `search Clementi` returns persons whose address contains **Clementi**.
- `search friends` returns persons who have the tag **friends**.
- `search client` returns persons categorized as **Client**.
- `search doe` returns persons whose attributes contain **"doe"**, such as **John Doe** and **johnd@example.com**.

### Clearing all entries : `clear`

Clears all contacts from TrackUp.

Format: `clear`

### Adding an event : `addevent`

Adds an event to TrackUp's calendar.

Format: `addevent -t <EVENT_TITLE> -s <START_DATETIME> -e <END_DATETIME> [-c <CONTACT_INDEX>...]`

**Notes:**
* EVENT_TITLE, START_DATETIME, and END_DATETIME are **required**.
* CONTACT_INDEX is optional. You can link **multiple** contacts by specifying multiple indexes.
* START_DATETIME and END_DATETIME must be in **YYYY-MM-DD HH:MM** format.
* If the event already exists, it will not be added again.

**Examples:**
- `addevent -t "Team Meeting" -s 2025-03-30 14:00 -e 2025-03-30 15:00 -c 1 -c 3` adds a "Team Meeting" from 14:00 to 15:00 on March 30, 2025, linking it to contacts at index 1 and 3.
- `addevent -t "Project Deadline" -s 2025-04-01 23:59 -e 2025-04-02 00:00` adds a "Project Deadline" event without linking any contacts.

### Deleting an event : `delevent`

Deletes events from TrackUp's calendar based on specified filters.

Format: `delevent [-t <TITLE_KEYWORD>] [-s <START_DATETIME>] [-e <END_DATETIME>] [-c <CONTACT_INDEX>...]`

**Notes:**
* At least **one** filter must be provided.
* **TITLE_KEYWORD** performs a case-insensitive partial match on event titles.
* **START_DATETIME** and **END_DATETIME** require an exact match (YYYY-MM-DD HH:MM format).
* **CONTACT_INDEX** matches events linked to the specified contacts.
* **All** matching events will be deleted.
* If no matching events are found, an error message is displayed.

**Examples:**
* `delevent -t Meeting` deletes all events with "Meeting" in the title.
* `delevent -s 2025-03-30 14:00 -e 2025-03-30 15:00` deletes all events exactly matching this start and end time.
* `delevent -c 2` deletes all events linked to the contact at index 2.
* `delevent -t "Workshop" -c 1 -c 4` deletes all events with "Workshop" in the title that are linked to contacts at index 1 or 4.

### Exiting the program : `exit`

Exits TrackUp

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

| **Action**                | **Format, Examples**                                                                                                                                                                              |
|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**                   | `add -n <NAME> -p <PHONE> -e <EMAIL> -a <ADDRESS> [-c <CATEGORY>]  [-t <TAG>]`<br>e.g., `add -n "James Ho" -p 22224444 -e jamesho@example.com -a "123, Clementi Rd, 1234665" -c Client -t friend` |
| **Clear**                 | `clear`                                                                                                                                                                                           |
| **Delete**                | `delete <INDEX>`<br>e.g., `delete 3`                                                                                                                                                              |
| **Delete by Attributes**  | `deleteby [-n <NAME>] [-p <PHONE>] [-e <EMAIL>] [-a <ADDRESS>] [-c <CATEGORY>] [-t <TAG>]`<br>e.g., `deleteby -n "John Doe"`                                                                      |
| **Edit**                  | `edit <INDEX> [-n <NAME>] [-p <PHONE>] [-e <EMAIL>] [-a <ADDRESS>] [-c <CATEGORY>] [-t <TAG>]`<br>e.g., `edit 2 -n "James Lee" -e jameslee@example.com`                                                     |
| **Find**                  | `find <KEYWORD>`<br>e.g., `find John`                                                                                                                                                             |
| **List**                  | `list [<CATEGORY>]`<br>e.g., `list`, `list Client`                                                                                                                                                |
| **Search**                | `search <KEYWORD>`<br>e.g., `search John`, `search Client`                                                                                                                                        |
| **Help**                  | `help`                                                                                                                                                                                            |
| **Exit**                  | `exit`                                                                                                                                                                                            |
