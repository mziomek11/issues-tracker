import {
  Container as ChakraContainer,
  ContainerProps as ChakraContainerProps,
} from '@chakra-ui/react';

export interface ContainerProps extends ChakraContainerProps {}

export const Container: React.FC<ContainerProps> = (props) => {
  return <ChakraContainer maxW="80vw" {...props} />;
};
