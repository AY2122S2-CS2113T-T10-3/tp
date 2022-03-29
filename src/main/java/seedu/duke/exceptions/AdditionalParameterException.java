package seedu.duke.exceptions;

import seedu.duke.util.StringConstants;

public class AdditionalParameterException extends GeneralParseException {
    private static final String ERROR_MESSAGE = StringConstants.ERROR_ADDITIONAL_PARAMETER;

    public AdditionalParameterException() {
        super(ERROR_MESSAGE);
    }

}
