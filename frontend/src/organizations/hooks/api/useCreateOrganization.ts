import { useMutation } from 'react-query';
import { createOrganization } from 'organizations/api';

export const useCreateOrganization = () => useMutation(createOrganization);
