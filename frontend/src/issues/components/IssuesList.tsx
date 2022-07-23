import { Spinner, Stack, Text } from '@chakra-ui/react';
import { FC } from 'react';
import { useIssues } from '@issues/hooks';
import { IssuesListParams } from '@issues/types';

interface IssuesListProps {
  params: IssuesListParams;
}
export const IssuesList: FC<IssuesListProps> = ({ params }) => {
  const handleSuccess = ({ data }: any) => {
    console.log(data);
  };

  const handleError = (error: any) => {
    console.log(error);
  };

  const {
    data: issues,
    isLoading,
    isError,
    isSuccess,
  } = useIssues(params, { onError: handleError, onSuccess: handleSuccess });

  if (isLoading) return <Spinner />;

  return (
    <Stack>
      <Text>{params.organizationId}</Text>
      <Text>
        {isSuccess && 'success'}
        {isError && 'error'}
      </Text>
    </Stack>
  );
};
