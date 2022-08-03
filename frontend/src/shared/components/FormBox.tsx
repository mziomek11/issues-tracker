import { Flex, Text, VStack, Box } from '@chakra-ui/react';

export interface FormBoxProps {
  children: React.ReactNode;
  heading: string;
}

export const FormBox: React.FC<FormBoxProps> = ({ heading, children }) => {
  return (
    <Flex w="full" alignItems="flex-start" justifyContent="center" mt="16">
      <VStack justifyContent="center" p={8} borderRadius="lg" border="1px" borderColor="gray.200">
        <Text fontSize="4xl" mb="6">
          {heading}
        </Text>

        <Box width="30vw">{children}</Box>
      </VStack>
    </Flex>
  );
};
