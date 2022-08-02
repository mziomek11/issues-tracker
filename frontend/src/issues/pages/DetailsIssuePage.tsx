import { Box, Spinner, VStack } from '@chakra-ui/react';
import { DetailsIssueHeader } from '@issues/components/details/header';
import { DetailsIssueComment } from '@issues/components/details/comment';
import { DetailsIssueCommentForm } from '@issues/components/details/comment-form';
import { useIssue } from '@issues/hooks';
import { IssuesDetailsParams } from '@issues/types';
import { Layout } from '@shared/components';
import { useParams } from '@shared/hooks/api';
import { FC } from 'react';
import { IssueStatus } from '@issues/enums';

export const DetailsIssuePage: FC = () => {
  const params = useParams<IssuesDetailsParams>();
  const { isLoading, isSuccess, data: issueResponse } = useIssue(params);

  return (
    <Layout>
      <VStack>
        {isLoading && <Spinner />}
        {isSuccess && (
          <Box>
            <DetailsIssueHeader params={params} issue={issueResponse.data} />
            {issueResponse.data.comments.map((comment) => (
              <DetailsIssueComment
                key={comment.id}
                params={params}
                comment={comment}
                issue={issueResponse.data}
              />
            ))}
            {issueResponse.data.status === IssueStatus.OPENED && (
              <DetailsIssueCommentForm params={params} />
            )}
          </Box>
        )}
      </VStack>
    </Layout>
  );
};
