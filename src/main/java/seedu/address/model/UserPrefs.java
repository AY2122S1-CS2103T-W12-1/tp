package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasMap;
import seedu.address.model.alias.CommandWord;
import seedu.address.model.alias.Shortcut;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path sportsPaFilePath = Paths.get("data", "sportspa.json");
    private AliasMap aliases = new AliasMap();

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setSportsPaFilePath(newUserPrefs.getSportsPaFilePath());
        setAliases(newUserPrefs.getAliases());
    }

    @Override
    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    @Override
    public Path getSportsPaFilePath() {
        return sportsPaFilePath;
    }

    @Override
    public AliasMap getAliases() {
        return aliases;
    }

    public void setAliases(AliasMap aliases) {
        requireNonNull(aliases);
        this.aliases = aliases;
    }

    public void addAlias(Alias alias) {
        aliases.add(alias);
    }

    public CommandWord removeAlias(Shortcut shortcut) {
        return aliases.remove(shortcut);
    }

    public void setSportsPaFilePath(Path sportsPaFilePath) {
        requireNonNull(sportsPaFilePath);
        this.sportsPaFilePath = sportsPaFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && sportsPaFilePath.equals(o.sportsPaFilePath)
                && aliases.equals(o.aliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, sportsPaFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + sportsPaFilePath);
        return sb.toString();
    }

}
