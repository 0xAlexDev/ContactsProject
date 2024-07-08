export class ValidationException extends Error {
    errorCode : Number;
    validationErrors : string[] = [];
    constructor(
        msg: string,
        validationsErrors : string[],
        errorCode : Number
    ) {
        super(msg);
        this.validationErrors = validationsErrors;
        this.errorCode = errorCode;
        Object.setPrototypeOf(this, ValidationException.prototype);
    }
}