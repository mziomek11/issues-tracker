import { Box, Link } from '@chakra-ui/react';
import { Container } from './Container';

export const Footer: React.FC = () => {
  return (
    <Box py="4" borderTop="1px" borderColor="gray.200" mt="8">
      <Container display="flex" justifyContent="center">
        Source code available at&nbsp;
        <Link textDecor="underline" href="https://github.com/hommat/issues-tracker">
          github
        </Link>
      </Container>
    </Box>
  );
};
