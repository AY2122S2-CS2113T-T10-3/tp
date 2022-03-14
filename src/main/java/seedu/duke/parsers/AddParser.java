package seedu.duke.parsers;

import java.util.HashMap;
import java.util.Objects;

import seedu.duke.commands.AddCommand;
import seedu.duke.commands.Command;
import seedu.duke.exceptions.ModHappyException;
import seedu.duke.exceptions.ParseException;
import seedu.duke.util.StringConstants;

/**
 * This Parser supports the "add" command.
 */
public class AddParser extends Parser {

    private static final String TASK_NAME = StringConstants.TASK_NAME;
    private static final String TASK_DESCRIPTION = StringConstants.TASK_DESCRIPTION;
    private static final String TASK_WORKING_TIME = StringConstants.TASK_WORKING_TIME;
    private static final String MODULE_CODE = StringConstants.MODULE_CODE;
    private static final String MODULE_DESCRIPTION = StringConstants.MODULE_DESCRIPTION;
    private static final String FLAG = StringConstants.FLAG;
    private static final String TASK_DESCRIPTION_TWO = StringConstants.TASK_DESCRIPTION_TWO;
    private static final String INVALID = StringConstants.INVALID;

    // Unescaped regex for testing (split into two lines):
    // \s*((\/t\s+(?<taskName>.+?(?=\s+-d\s+|\s+-t\s+|$))(\s+(-d\s+\"(?<taskDescription>([^\"]*))\")(?=(\s+-t\s+)|$))?
    // (\s+(-t\s+\"(?<estimatedWorkingTime>([^\"]*))\")(?=(\s+-d\s+)|$))?(\s+(-d\s+\"(?<taskDescription2>([^\"]*))\"))?
    // |\/m\s+(?<moduleCode>\w+?(?=(\s+-d\s+)|\s+|$))(\s+(-d\s+\"(?<moduleDescription>([^\"]*))\"))?))(?<invalid>.*)

    /*
    * Basic explanation for Add format regex
    * \s*((\/t\s+(?<taskName>.+?) ... )) -- captures taskName (no constraint)
    * (?=\s+-d\s+|\s+-t\s+|$) -- asserts that -d or -t might follow
    * (\s+(-d\s+\"(?<taskDescription>([^\"]*))\") ... )? -- captures taskDescription (cannot have ")
    *                                                       which must be enclosed with "". Optional
    * (?=(\s+-t\s+)|$) -- asserts -t might follow
    * (\s+(-t\s+\"(?<estimatedWorkingTime>([^\"]*))\") ... )? -- captures estimatedWorkingTime (cannot have ")
    *                                                            which must be enclosed with "". Optional
    * (?=(\s+-d\s+)|$) -- asserts -d might follow
    * (\s+(-d\s+\"(?<taskDescription2>([^\"]*))\"))? -- captures taskDescription2 with same constraints 
    *                                                   if -t precedes -d
    * \s*(( ... |\/m\s+(?<moduleCode>\w+? ... ) -- alternatively captures moduleCode
    *                                              (no whitespaces or special characters)
    * (?=(\s+-d\s+)|\s+|$) -- asserts -d or whitespaces (if command is invalid) might follow
    * (\s+(-d\s+\"(?<moduleDescription>([^\"]*))\"))? -- captures moduleDescription (cannot have ")
    *                                                    which must be enclosed with "". Optional
    * \s+( Task | Module )(?<invalid>.*) -- any input at any part of the command
    *                                       that does not fit the pattern will be captured as invalid
    * */
    // TODO: Add support for -mod argument when integrating Task and Module classes with one another
    private static final String ADD_FORMAT = "\\s*((\\/t\\s+(?<taskName>.+?(?=\\s+-d\\s+|\\s+-t\\s+|$))"
            + "(\\s+(-d\\s+\\\"(?<taskDescription>([^\\\"]*))\\\")(?=(\\s+-t\\s+)|$))?(\\s+(-t\\s+\\\""
            + "(?<estimatedWorkingTime>([^\\\"]*))\\\")(?=(\\s+-d\\s+)|$))?(\\s+(-d\\s+\\\""
            + "(?<taskDescription2>([^\\\"]*))\\\"))?|\\/m\\s+(?<moduleCode>\\w+?(?=(\\s+-d\\s+)|\\s+|$))(\\s+(-d\\s+"
            + "\\\"(?<moduleDescription>([^\\\"]*))\\\"))?))(?<invalid>.*)";

    public AddParser() {
        super();
        // See also https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
        commandFormat = ADD_FORMAT;
        groupNames.add(TASK_NAME);
        groupNames.add(TASK_DESCRIPTION);
        groupNames.add(TASK_WORKING_TIME);
        groupNames.add(MODULE_CODE);
        groupNames.add(MODULE_DESCRIPTION);
        groupNames.add(TASK_DESCRIPTION_TWO);
        groupNames.add(INVALID);
    }

    @Override
    public Command parseCommand(String userInput) throws ModHappyException {
        HashMap<String, String> parsedArguments = parseString(userInput);
        final String taskName = parsedArguments.get(TASK_NAME);
        final String estimatedWorkingTime = parsedArguments.get(TASK_WORKING_TIME);
        final String moduleCode = parsedArguments.get(MODULE_CODE);
        final String moduleDescription = parsedArguments.get(MODULE_DESCRIPTION);
        final String taskDescriptionTwo = parsedArguments.get(TASK_DESCRIPTION_TWO);
        String invalid = parsedArguments.get(INVALID);
        String taskDescription = parsedArguments.get(TASK_DESCRIPTION);
        if (invalid.isEmpty()) {
            invalid = null;
        }
        boolean isInvalid = ((!Objects.isNull(invalid))
                || ((!Objects.isNull(taskDescription)) && (!Objects.isNull(taskDescriptionTwo))));
        if (isInvalid) {
            throw new ParseException();
        } else if (Objects.isNull(taskDescription) && !Objects.isNull(taskDescriptionTwo)) {
            taskDescription = taskDescriptionTwo;
        }
        if (!Objects.isNull(taskName)) {
            return new AddCommand(taskName, taskDescription, true, estimatedWorkingTime);
        }
        if (!Objects.isNull(moduleCode)) {
            return new AddCommand(moduleCode, moduleDescription, false, null);
        }
        throw new ParseException();
    }
}
