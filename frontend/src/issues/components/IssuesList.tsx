import { AxiosError } from 'axios';
import { Button, Spinner, Stack, Tab, TabList, TabPanel, TabPanels, Tabs, Text } from '@chakra-ui/react';
import { FC, useState } from 'react';
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

  const handleIssuesSuccess = ({ data }: any) => {
    console.log(data);
  };

  const handleIssuesError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => {
    applicationErrorHandler()
      .onAuthInvalidJwt(({ message }) => setError(message))
      .onOrganizationAccessDenied(({ message }) => setError(message))
      .onOrganizationNotFound(({ message }) => setError(message))
      .onOrganizationProjectNotFound(({ message }) => setError(message))
      .handleAxiosError(error);
  };

  const { isLoading, isSuccess, data: issues } = useIssues(params, {
    onError: handleIssuesError,
    onSuccess: handleIssuesSuccess,
  });

  const { data: projectData, isFetching } = useOrganizationDetails(params.organizationId, {});

  if (isLoading || isFetching) return <Spinner />;
  else if (isSuccess) {
    return (
      <Stack width={'55%'}>
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
            <TabPanel>{issues.data}</TabPanel>
          </TabPanels>
        </Tabs>
      </Stack>
    );
  } else {
    return <Text variant={'subtitle2'}>{error}</Text>;
  }
};
