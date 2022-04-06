package seedu.duke.parsers;

import java.util.HashMap;
import java.util.Objects;

import seedu.duke.commands.Command;
import seedu.duke.commands.DeleteCommand;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.exceptions.MissingNumberException;
import seedu.duke.exceptions.ModHappyException;
import seedu.duke.exceptions.InvalidCompulsoryParameterException;
import seedu.duke.exceptions.MissingCompulsoryParameterException;
import seedu.duke.util.StringConstants;

/**
 * This Parser supports the "del" command.
 */
public class DeleteParser extends Parser {
    private static final String TASK_NUMBER = StringConstants.TASK_NUMBER;
    private static final String TASK_MODULE = StringConstants.TASK_MODULE;
    private static final String MODULE_CODE = StringConstants.MODULE_CODE;
    private String userInput;

    // Unescaped regex for testing:
    // (task\\s+(?<taskNumber>\\d+)(\\s+-m\\s+(?<taskModule>\\w+))?|mod\\s+(?<moduleCode>\\w+))(?<invalid>.*)
    private static final String DELETE_FORMAT = "(task\\s+(?<taskNumber>\\d+)(\\s+-m\\s+(?<taskModule>\\w+))?"
            + "|mod\\s+(?<moduleCode>\\w+))(?<invalid>.*)";
    private static final String POSITIVE_INT = StringConstants.POSITIVE_INT;
    private static final String WORD_CHAR_ONLY = StringConstants.WORD_CHAR_ONLY;

    public DeleteParser() {
        super();
        this.commandFormat = DELETE_FORMAT;
        groupNames.add(TASK_NUMBER);
        groupNames.add(TASK_MODULE);
        groupNames.add(MODULE_CODE);
        groupNames.add(INVALID);
    }

    /**
     * Determines the error that the user made in its command based on the command type.
     * @throws ModHappyException based on the command type.
     */
    @Override
    public void determineError() throws ModHappyException {
        String type = userInput.split(SPACE)[ZEROTH_INDEX];
        switch (type) {
        case TASK:
            determineErrorForTask();
            break;
        case MODULE:
            determineErrorForModule();
            break;
        default:
            throw new InvalidCompulsoryParameterException();
        }
    }

    public void determineErrorForTask() throws ModHappyException {
        String taskNumber;
        try {
            taskNumber = userInput.split(SPACE)[FIRST_INDEX];
        } catch (IndexOutOfBoundsException e) {
            throw new MissingNumberException(TASK_NUMBER_STR);
        }
        if (!taskNumber.matches(POSITIVE_INT)) {
            throw new InvalidNumberException(TASK_NUMBER_STR);
        }
    }

    public void determineErrorForModule() throws ModHappyException {
        String moduleCode;
        try {
            moduleCode = userInput.split(SPACE)[FIRST_INDEX];
        } catch (IndexOutOfBoundsException e) {
            throw new MissingCompulsoryParameterException(MODULE_CODE_STR);
        }
        if (!moduleCode.matches(WORD_CHAR_ONLY)) {
            throw new InvalidCompulsoryParameterException(MODULE_CODE_STR);
        }
    }

    @Override
    public Command parseCommand(String userInput) throws ModHappyException {
        this.userInput = userInput;
        HashMap<String, String> parsedArguments = parseString(userInput);
        String taskNumberString = parsedArguments.get(TASK_NUMBER);
        String taskModuleString = parsedArguments.get(TASK_MODULE);
        String moduleCode = parsedArguments.get(MODULE_CODE);
        if (!Objects.isNull(moduleCode)) {
            return new DeleteCommand(moduleCode);
        }
        if (!Objects.isNull(taskNumberString)) {
            int taskIndex;
            try {
                taskIndex = Integer.parseInt(taskNumberString) - 1;
            } catch (NumberFormatException e) {
                throw new InvalidNumberException(TASK_NUMBER_STR);
            }
            return new DeleteCommand(taskIndex, taskModuleString);
        }
        throw new ModHappyException();
    }
}
