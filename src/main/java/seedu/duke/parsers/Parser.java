package seedu.duke.parsers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.duke.commands.Command;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.exceptions.InvalidFlagException;
import seedu.duke.exceptions.InvalidModuleGradeException;
import seedu.duke.exceptions.InvalidCompulsoryParameterException;
import seedu.duke.exceptions.ExcessArgumentException;

import seedu.duke.exceptions.ModHappyException;
import seedu.duke.util.StringConstants;

/**
 * Represents a Parser that parse a {@code Command}.
 */
public abstract class Parser {
    protected static final String EXIT_COMMAND_WORD = StringConstants.EXIT_COMMAND_WORD;
    protected static final String ADD_COMMAND_WORD = StringConstants.ADD_COMMAND_WORD;
    protected static final String DELETE_COMMAND_WORD = StringConstants.DELETE_COMMAND_WORD;
    protected static final String GPA_COMMAND_WORD = StringConstants.GPA_COMMAND_WORD;
    protected static final String GRADE_COMMAND_WORD = StringConstants.GRADE_COMMAND_WORD;
    protected static final String LIST_COMMAND_WORD = StringConstants.LIST_COMMAND_WORD;
    protected static final String MARK_COMMAND_WORD = StringConstants.MARK_COMMAND_WORD;
    protected static final String EDIT_COMMAND_WORD = StringConstants.EDIT_COMMAND_WORD;
    protected static final String RESET_COMMAND_WORD = StringConstants.RESET_COMMAND_WORD;
    protected static final String HELP_COMMAND_WORD = StringConstants.HELP_COMMAND_WORD;
    protected static final String SAVE_COMMAND_WORD = StringConstants.SAVE_COMMAND_WORD;
    protected static final String TAG_COMMAND_WORD = StringConstants.TAG_COMMAND_WORD;
    protected static final String OPTION_COMMAND_WORD = StringConstants.OPTION_COMMAND_WORD;

    protected static final String INVALID = StringConstants.INVALID;
    protected static final String INVALID_FLAG = StringConstants.INVALID_FLAG;
    protected static final String INVALID_MODULE_CODE = StringConstants.INVALID_MODULE_CODE;
    protected static final String INVALID_MODULE_GRADE = StringConstants.INVALID_MODULE_GRADE;
    protected static final String INVALID_NUMBER = StringConstants.INVALID_NUMBER;

    protected String commandFormat;
    protected HashMap<String, String> parsedCommand;
    protected HashSet<String> groupNames;

    public Parser() {
        groupNames = new HashSet<String>();
        parsedCommand = new HashMap<>();
    }

    /**
     * Parses the provided user input and returns the relevant Command object.
     */
    public abstract Command parseCommand(String userInput) throws ModHappyException;

    /**
     * Parses string into groups based on commandFormat.
     * @throws ModHappyException if the provided string does not match the pattern
     */
    public HashMap<String, String> parseString(String userInput) throws ModHappyException {
        final Pattern commandPattern = Pattern.compile(commandFormat);
        final Matcher matcher = commandPattern.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new InvalidCompulsoryParameterException();
        }
        for (Object groupName : groupNames) {
            try {
                parsedCommand.put(groupName.toString(), matcher.group(groupName.toString()).trim());
            } catch (Exception e) {
                parsedCommand.put(groupName.toString(), null);
            }
        }
        if (groupNames.contains(INVALID)) {
            String invalidInput = parsedCommand.get(INVALID);
            if (!Objects.isNull(invalidInput) && !invalidInput.isBlank()) {
                throw new ExcessArgumentException(invalidInput);
            }
        }
        if (groupNames.contains(INVALID_FLAG)) {
            String invalidInput = parsedCommand.get(INVALID_FLAG);
            if (!Objects.isNull(invalidInput) && !invalidInput.isBlank()) {
                throw new InvalidFlagException(invalidInput);
            }
        }
        if (groupNames.contains(INVALID_MODULE_GRADE)) {
            String invalidInput = parsedCommand.get(INVALID_MODULE_GRADE);
            if (!Objects.isNull(invalidInput) && !invalidInput.isBlank()) {
                throw new InvalidModuleGradeException(invalidInput);
            }
        }
        if (groupNames.contains(INVALID_NUMBER)) {
            String invalidInput = parsedCommand.get(INVALID_NUMBER);
            if (!Objects.isNull(invalidInput) && !invalidInput.isBlank()) {
                throw new InvalidNumberException(invalidInput);
            }
        }

        return parsedCommand;
    }

}