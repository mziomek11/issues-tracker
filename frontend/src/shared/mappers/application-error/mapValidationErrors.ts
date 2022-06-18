import { capitalize, mapValues } from 'lodash';

export const mapValidationErrors = (errors: Record<string, string[]>): Record<string, string> =>
  mapValues(errors, (errorArray) => capitalize(errorArray[0]));
