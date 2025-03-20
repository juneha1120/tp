package trackup.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import trackup.commons.exceptions.DataLoadingException;
import trackup.model.ReadOnlyAddressBook;
import trackup.model.ReadOnlyUserPrefs;
import trackup.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
