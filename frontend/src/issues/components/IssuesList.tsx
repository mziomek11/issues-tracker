import { Spinner, Stack, Text } from '@chakra-ui/react';
import { FC, useState } from 'react';
import { useIssues } from '@issues/hooks';
import { IssuesListParams } from '@issues/types';
import { useOrganizationDetails } from '@organizations/hooks/api';
import { AxiosResponse } from 'axios';
import { ProjectDto, UserOrganizationDto } from '@organizations/dtos';

interface IssuesListProps {
  params: IssuesListParams;
}

export const IssuesList: FC<IssuesListProps> = ({ params }) => {
  const [project, setProject] = useState<ProjectDto[]>([]);

  const handleIssuesSuccess = ({ data }: any) => {
    console.log(data);
  };

  const handleIssuesError = (error: any) => {
    console.log(error);
  };

  const handleOrganizationSuccess = ({ data }: AxiosResponse<UserOrganizationDto, unknown>) => {
    setProject(data.projects.filter((project) => project.id === params.projectId));
  };
  const handleOrganizationError = (error: any) => {
    console.log(error);
  };

  const { isLoading, isError, isSuccess } = useIssues(params, {
    onError: handleIssuesError,
    onSuccess: handleIssuesSuccess,
  });

  const { isLoading: isOrganizationLoading, isSuccess: isOrganizationSuccess } =
    useOrganizationDetails(params.organizationId, {
      onError: handleOrganizationError,
      onSuccess: handleOrganizationSuccess,
    });

  if (isLoading || isOrganizationLoading) return <Spinner />;

  return (
    <Stack>
      <Text>{isOrganizationSuccess && project[0].name}</Text>
      <Text>
        {isSuccess && 'success'}
        {isError && 'error'}
      </Text>
    </Stack>
  );
};
