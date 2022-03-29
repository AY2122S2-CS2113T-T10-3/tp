package seedu.duke.commands;

import java.util.Objects;

import seedu.duke.exceptions.ModHappyException;
import seedu.duke.exceptions.UnknownConfigurationGroupWord;
import seedu.duke.data.ModuleList;
import seedu.duke.util.Configuration;
import seedu.duke.util.StringConstants;

public class OptionCommand extends Command {
    private static final String OPTION_CHECK_CONFIGURATIONS = StringConstants.OPTION_CHECK_CONFIGURATIONS;
    private static final String OPTION_SET_SUCCESS = StringConstants.OPTION_SET_SUCCESS;

    private Configuration.ConfigurationGroup configurationGroup = null;
    private String newValue = null;

    public OptionCommand(String configurationGroupWord, String newValue) throws ModHappyException {
        if (Objects.isNull(configurationGroupWord)) {
            return;
        }
        try {
            configurationGroup = Configuration.ConfigurationGroup.valueOf(configurationGroupWord);
            // Checks whether the configurationGroupWord and newValue are legal.
            if (Objects.isNull(newValue)) {
                return;
            }
            if (Configuration.LEGAL_VALUES.containsKey(configurationGroup)
                    && Configuration.LEGAL_VALUES.get(configurationGroup).contains(newValue)) {
                this.newValue = newValue;
            } else {
                throw new UnknownConfigurationGroupWord(configurationGroupWord + " " + newValue);
            }
        } catch (Exception e) {
            throw new UnknownConfigurationGroupWord(configurationGroupWord);
        }
    }

    @Override
    public CommandResult execute(ModuleList moduleList, Configuration configuration) throws ModHappyException {
        // enter "option" to check the list of configuration setting
        if (Objects.isNull(configurationGroup)) {
            return new CommandResult(OPTION_CHECK_CONFIGURATIONS + StringConstants.LS
                    + configuration.getConfigurationsReport());
        }

        // enter "option <CONFIGURATION_GROUP>" to check the legal values of a configuration group
        if (Objects.isNull(newValue)) {
            return new CommandResult(configurationGroup + StringConstants.LS
                    + configuration.getValueExplain(configurationGroup));
        }

        // enter "option <CONFIGURATION_GROUP>=<NEW_VALUE>" to set legal value for a configuration group
        configuration.configurationGroupHashMap.put(configurationGroup, newValue);
        return new CommandResult(OPTION_SET_SUCCESS + configurationGroup + "=" + newValue);
    }
}
