import { AxiosError, AxiosResponse } from 'axios';
import {
  Button,
  Flex,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Stack,
  Textarea,
  VStack,
} from '@chakra-ui/react';
import { useFormik } from 'formik';
import { FC, useState } from 'react';
import { CommentIssueDto } from '@issues/dtos';
import { useCommentIssue } from '@issues/hooks';
import { IssuesDetailsParams } from '@issues/types';
import { sseHandler } from '@notifications/helpers/sse-handler';
import { ApplicationErrorDto } from '@shared/dtos/application-error';
import { ApplicationErrorCode } from '@shared/enums/error-code';
import { HttpStatus } from '@shared/enums/http';
import { applicationErrorHandler } from '@shared/helpers/application-error';
import { mapValidationErrors } from '@shared/mappers/application-error';
import { useSseSubscription } from '@notifications/hooks/api';
import { useUser } from '@users/contexts';
import { ObjectIdDto } from '@shared/dtos';

interface DetailsIssueCommentFormProps {
  params: IssuesDetailsParams;
}

const initialValues: CommentIssueDto = {
  content: '',
};

export const DetailsIssueCommentForm: FC<DetailsIssueCommentFormProps> = ({ params }) => {
  const { userId } = useUser();
  const [handler, setHandler] = useState(sseHandler());
  const [isWaitingForSse, setIsWaitingForSse] = useState(false);
  const [isOpened, setIsOpened] = useState(false);
  useSseSubscription(handler);

  const toggleOpen = (): void => setIsOpened(!isOpened);

  const handleSuccess = (response: AxiosResponse<ObjectIdDto, CommentIssueDto>): void => {
    setIsWaitingForSse(true);

    setHandler(
      handler.onIssueCommentedEvent(({ data }) => {
        if (data.memberId === userId && response.data.id === data.commentId) {
          setIsWaitingForSse(false);
          setIsOpened(false);
          resetForm();
        }
      })
    );
  };

  const handleError = (
    error: AxiosError<ApplicationErrorDto<ApplicationErrorCode, HttpStatus>, unknown>
  ): void => {
    applicationErrorHandler<CommentIssueDto>()
      .onGenericValidationFailed(({ details }) => setErrors(mapValidationErrors(details)))
      .onDefault(({ message }) => setFieldError('content', message))
      .handleAxiosError(error);
  };

  const { mutate: commentIssue, isLoading } = useCommentIssue(params);

  const handleSubmitForm = (values: CommentIssueDto): void => {
    commentIssue(values, { onError: handleError, onSuccess: handleSuccess });
  };

  const { handleSubmit, handleChange, setErrors, resetForm, setFieldError, errors, values } =
    useFormik<CommentIssueDto>({
      initialValues,
      onSubmit: handleSubmitForm,
    });

  return (
    <Stack>
      {!isOpened && <Button onClick={toggleOpen}>Add comment</Button>}
      {isOpened && (
        <VStack as="form" onSubmit={handleSubmit as any}>
          <FormControl isInvalid={!!errors.content}>
            <FormLabel htmlFor="content">Content</FormLabel>
            <Textarea id="content" name="content" value={values.content} onChange={handleChange} />
            <FormErrorMessage>{errors.content}</FormErrorMessage>
          </FormControl>

          <Flex>
            <Button type="button" disabled={isLoading || isWaitingForSse} onClick={toggleOpen}>
              Cancel
            </Button>

            <Button type="submit" isLoading={isLoading || isWaitingForSse}>
              Comment
            </Button>
          </Flex>
        </VStack>
      )}
    </Stack>
  );
};
