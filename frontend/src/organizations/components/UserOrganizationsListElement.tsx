import { Box, Button, Heading, List, ListItem } from '@chakra-ui/react';
import { UserOrganizationDto } from '@organizations/dtos';

interface UserOrganizationsListElementProps extends UserOrganizationDto {}
export const UserOrganizationsListElement: React.FC<UserOrganizationsListElementProps> = ({
  name,
  projects,
}) => {
  return (
    <Box>
      <Heading size="md">{name}</Heading>
      <List>
        {projects.map(({ id, name }) => (
          <ListItem key={id}>{name}</ListItem>
        ))}
        <Button variant="outline">Add project</Button>
      </List>
    </Box>
  );
};
