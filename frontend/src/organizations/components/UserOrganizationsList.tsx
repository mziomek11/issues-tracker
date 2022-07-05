import { AxiosError } from 'axios';
import { Flex, Button, Spinner } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
import { UserOrganizationsListElement } from '@organizations/components/UserOrganizationsListElement';
import { useOrganizations } from '@organizations/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { reverse } from '@shared/helpers/routing';

export const UserOrganizationsList: React.FC = () => {
  const handleError = (error: AxiosError<ApplicationErrorDto<any, any>, unknown>): void => {
    applicationErrorHandler().handleAxiosError(error);
  };
  const { isLoading, data: organizations } = useOrganizations(handleError);

  if (isLoading) return <Spinner />;

  return (
    <Flex width="80%" direction="column" gap={5}>
      {organizations?.data.map((organization) => (
        <UserOrganizationsListElement key={organization.id} {...organization} />
      ))}
      <Link to={reverse('organizations.create')}>
        <Button size="lg" variant="outline">
          Add organization
        </Button>
      </Link>
    </Flex>
  );
};
