import { capitalize, mapValues, first } from 'lodash';

export const mapValidationErrors = (errors: Record<string, string[]>): Record<string, string> =>
  mapValues(errors, (errorArray) => capitalize(first(errorArray) || ''));
