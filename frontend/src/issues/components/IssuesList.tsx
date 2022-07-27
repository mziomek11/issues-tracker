import { AxiosError } from 'axios';
import {
  Button,
  Spinner,
  Stack,
  Tab,
  TabList,
  TabPanel,
  TabPanels,
  Tabs,
  Text,
} from '@chakra-ui/react';
import { FC, useState } from 'react';
import { IssuesListElement } from '@issues/components';
import { useIssues } from '@issues/hooks';
import { IssuesListParams } from '@issues/types';
import { useOrganizationDetails } from '@organizations/hooks/api';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';

interface IssuesListProps {
  params: IssuesListParams;
}

export const IssuesList: FC<IssuesListProps> = ({ params }) => {
  const [error, setError] = useState<string>();

  const handleIssuesError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler()
      .onAuthInvalidJwt(({ message }) => setError(message))
      .onOrganizationAccessDenied(({ message }) => setError(message))
      .onOrganizationNotFound(({ message }) => setError(message))
      .onOrganizationProjectNotFound(({ message }) => setError(message))
      .handleAxiosError(error);
  };

  const {
    isLoading,
    isSuccess,
    data: issues,
  } = useIssues(params, {
    onError: handleIssuesError,
  });

  const { data: projectData, isFetching } = useOrganizationDetails(params.organizationId, {});

  if (isLoading || isFetching) return <Spinner />;
  else if (isSuccess) {
    return (
      <Stack width={'65%'}>
        <Stack
          width={'100%'}
          direction={'row'}
          alignItems="center"
          justifyContent={'space-between'}
        >
          <Text fontSize="6xl">
            {projectData?.data.projects.find((project) => project.id === params.projectId)?.name}
          </Text>
          <Button variant={'outline'}>Add issue</Button>
        </Stack>
        <Tabs
          variant={'unstyled'}
          width="100%"
          padding={0}
          sx={{
            "[aria-selected='true']": {
              fontWeight: 'bold',
            },
          }}
        >
          <TabList>
            <Tab>Opened</Tab>
            <Tab>Closed</Tab>
          </TabList>
          <TabPanels>
            <TabPanel width={'100%'}>
              {issues.data
                .filter((issue) => issue.status === 'OPENED')
                .map((issue, index) => (
                  <IssuesListElement key={index} {...issue} />
                ))}
            </TabPanel>
            <TabPanel width={'100%'}>
              {issues.data
                .filter((issue) => issue.status === 'CLOSED')
                .map((issue, index) => (
                  <IssuesListElement key={index} {...issue} />
                ))}
            </TabPanel>
          </TabPanels>
        </Tabs>
      </Stack>
    );
  } else {
    return <Text variant={'subtitle2'}>{error}</Text>;
  }
};
