import { ApplicationErrorCode } from '../dtos/application-error.dto';

export class ApplicationErrorHandler {
  code: string | undefined;
  constructor(code: ApplicationErrorCode | undefined) {
    this.code = code;
  }
  onGenericUnnamedError(fn: () => any): this {
    if (this.code === ApplicationErrorCode.GENERIC_UNNAMED_ERROR) {
      fn();
    }
    return this;
  }
  onGenericValidationFailed(fn: () => any): this {
    if (this.code === ApplicationErrorCode.GENERIC_VALIDATION_FAILED) {
      fn();
    }
    return this;
  }
  onGenericEmailUnavailable(fn: () => any): this {
    if (this.code === ApplicationErrorCode.GENERIC_EMAIL_UNAVAILABLE) {
      fn();
    }
    return this;
  }
}
