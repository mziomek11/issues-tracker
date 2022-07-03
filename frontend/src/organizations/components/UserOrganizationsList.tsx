import { AxiosError } from 'axios';
import { Flex, Button, Spinner, Text, Badge } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
import { UserOrganizationsListElement } from '@organizations/components/UserOrganizationsListElement';
import { useGetUserOrganizations } from '@organizations/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { reverse } from '@shared/helpers/routing';
import { useUser } from '@users/contexts';

export const UserOrganizationsList: React.FC = () => {
  const { isLoggedIn } = useUser();

  const handleError = (error: AxiosError<ApplicationErrorDto<any, any>, unknown>): void => {
    applicationErrorHandler().handleAxiosError(error);
  };
  const { isLoading, data } = useGetUserOrganizations(handleError);

  if (!isLoggedIn) {
    return (
      <>
        <Badge colorScheme="yellow">Warning</Badge>
        <Text fontSize="md">No user data to get</Text>
      </>
    );
  }

  if (isLoading) return <Spinner />;

  return (
    <Flex width="80%" direction="column" gap={5}>
      {data?.data.map((organization) => (
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
