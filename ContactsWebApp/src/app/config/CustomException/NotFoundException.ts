export class NotFoundException extends Error {
    errorCode : Number;
    constructor(
        msg: string,
        errorCode : Number
    ) {
        super(msg);
        this.errorCode = errorCode;
        Object.setPrototypeOf(this, NotFoundException.prototype);
    }
}