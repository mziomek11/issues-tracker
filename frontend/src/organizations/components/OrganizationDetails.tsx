import { AxiosError } from 'axios';
import { Button, Flex, Spacer, Spinner, Text, UnorderedList, ListItem } from '@chakra-ui/react';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useOrganizationDetails } from '@organizations/hooks/api';
import { OrganizationParams } from '@organizations/types';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { reverse } from '@shared/helpers/routing';
import { IssuesListParams } from '@issues/types';
import { useUser } from '@users/contexts';

interface OrganizationDetailsProps extends OrganizationParams {}

export const OrganizationDetails: React.FC<OrganizationDetailsProps> = ({ organizationId }) => {
  const { userId } = useUser();
  const [error, setError] = useState<string>('');
  const organizationParams: OrganizationParams = { organizationId };

  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler()
      .onAuthInvalidJwt(({ message }) => setError(message))
      .onAuthAccessDenied(({ message }) => setError(message))
      .onOrganizationNotFound(({ message }) => setError(message))
      .handleAxiosError(error);
  };

  const { data, isLoading, isSuccess, isError } = useOrganizationDetails(organizationId, {
    onError: handleError,
  });

  return (
    <>
      {isLoading && <Spinner />}
      {isSuccess && (
        <>
          <Text fontSize="4xl" mb="6">
            {data?.data.name}
          </Text>
          <Flex width="60%">
            <Flex direction="column" align="center">
              <Text fontSize="3xl" mb="4">
                Projects
              </Text>

              {data?.data.projects.length === 0 && (
                <Text>Organization has not any project yet</Text>
              )}
              <UnorderedList>
                {data?.data.projects.map((project) => {
                  const issuesListParams: IssuesListParams = {
                    organizationId,
                    projectId: project.id,
                  };

                  return (
                    <ListItem key={project.id}>
                      <Text
                        as={Link}
                        to={reverse({ path: 'issues.list', params: issuesListParams })}
                        _hover={{ textDecoration: 'underline' }}
                      >
                        {project.name}
                      </Text>
                    </ListItem>
                  );
                })}
              </UnorderedList>

              {userId === data?.data.ownerId && (
                <Button
                  as={Link}
                  to={reverse({
                    path: 'organizations.projects.create',
                    params: organizationParams,
                  })}
                  mt="4"
                >
                  Create project
                </Button>
              )}
            </Flex>
            <Spacer />
            <Flex direction="column" align="center">
              <Text fontSize="3xl" mb="4">
                Members
              </Text>
              <UnorderedList>
                {data?.data.members.map((member) => (
                  <ListItem key={member.id}>{member.email}</ListItem>
                ))}
              </UnorderedList>

              {userId === data?.data.ownerId && (
                <Button
                  as={Link}
                  to={reverse({ path: 'organizations.member.invite', params: organizationParams })}
                  mt="4"
                >
                  Invite member
                </Button>
              )}
            </Flex>
          </Flex>
        </>
      )}
      {isError && <Text>{error}</Text>}
    </>
  );
};
