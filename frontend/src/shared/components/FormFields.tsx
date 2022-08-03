import { VStack, StackProps } from '@chakra-ui/react';

export interface FormFieldsProps extends StackProps {
  children: React.ReactNode;
}

export const FormFields: React.FC<FormFieldsProps> = (props) => {
  return <VStack {...props} />;
};
