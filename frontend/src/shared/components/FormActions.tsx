import { VStack, StackProps } from '@chakra-ui/react';

export interface FormActionsProps extends StackProps {
  children: React.ReactNode;
}

export const FormActions: React.FC<FormActionsProps> = (props) => {
  return <VStack spacing="4" mt="8" {...props} />;
};
