package seedu.duke.parsers;

import java.util.HashMap;

import seedu.duke.commands.Command;
import seedu.duke.commands.OptionCommand;
import seedu.duke.exceptions.ModHappyException;

public class OptionParser extends Parser {

    private static final String OPTION_FORMAT = "(\\s*(?<configurationGroupWord>[A-Z_]+)($|\\b=\\s*(?<newValue>.*)\\b))?";
    private static final String CONFIGURATION_GROUP_WORD = "configurationGroupWord";
    private static final String NEW_VALUE = "newValue";

    public OptionParser() {
        super();
        this.commandFormat = OPTION_FORMAT;
        groupNames.add(CONFIGURATION_GROUP_WORD);
        groupNames.add(NEW_VALUE);
    }

    @Override
    public Command parseCommand(String userInput) throws ModHappyException {

        HashMap<String, String> parsedArguments = parseString(userInput);
        String configurationGroupWord = parsedArguments.get(CONFIGURATION_GROUP_WORD);
        String newValue = parsedArguments.get(NEW_VALUE);
        return new OptionCommand(configurationGroupWord, newValue);
    }
}
