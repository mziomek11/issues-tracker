import { AxiosError } from 'axios';
import { Spinner, Stack, Text } from '@chakra-ui/react';
import { FC } from 'react';
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
  const handleIssuesSuccess = ({ data }: any) => {
    console.log(data);
  };

  const handleIssuesError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ) => {
    applicationErrorHandler().onAuthInvalidJwt(()=>{}).
  };

  const { isLoading, isError, isSuccess } = useIssues(params, {
    onError: handleIssuesError,
    onSuccess: handleIssuesSuccess,
  });

  const { data: projectData, isFetching } = useOrganizationDetails(params.organizationId, {});

  if (isLoading || isFetching) return <Spinner />;
  else {
    return (
      <Stack alignItems={'center'}>
        <Text fontSize="4xl">
          {projectData?.data.projects.find((project) => project.id === params.projectId)?.name}
        </Text>
        <Text>
          {isSuccess && 'success'}
          {isError && 'error'}
        </Text>
      </Stack>
    );
  }
};
