package seedu.duke.commands;

import seedu.duke.tasks.Module;
import seedu.duke.tasks.ModuleList;
import seedu.duke.util.Configuration;
import seedu.duke.util.StringConstants;

public class ListCommand extends Command {
    private static final String LIST_MESSAGE = StringConstants.LIST_MESSAGE_TOP + LS + "%s";

    /**
     * Lists all tasks.
     */
    @Override
    public CommandResult execute(ModuleList moduleList, Configuration configuration) {
        String res = "";
        for (Module m : moduleList.getModuleList()) {
            res += m.printModuleTaskList() + LS;
        }
        res += moduleList.getGeneralTasks().printModuleTaskList();
        return new CommandResult(String.format(LIST_MESSAGE, res));
    }
}
