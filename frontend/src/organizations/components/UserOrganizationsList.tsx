import { AxiosError } from 'axios';
import { Flex, Button, Spinner, Text, UnorderedList, ListItem } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
import { useOrganizations } from '@organizations/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { reverse } from '@shared/helpers/routing';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';

export const UserOrganizationsList: React.FC = () => {
  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler().handleAxiosError(error);
  };
  const { isLoading, data: organizations } = useOrganizations(handleError);

  if (isLoading) return <Spinner />;

  return (
    <Flex width="80%" alignItems="center" direction="column" gap={5}>
      {organizations?.data.length === 0 && (
        <Text>You are not a member of any organization yet</Text>
      )}

      <UnorderedList>
        {organizations?.data.map((organization) => (
          <ListItem key={organization.id}>
            <Text
              as={Link}
              to={reverse({
                path: 'organizations.details',
                params: { organizationId: organization.id },
              })}
              fontSize="2xl"
              _hover={{ textDecoration: 'underline' }}
            >
              {organization.name}
            </Text>
          </ListItem>
        ))}
      </UnorderedList>

      <Button as={Link} to={reverse('organizations.create')} mt="2">
        Create organization
      </Button>
    </Flex>
  );
};
