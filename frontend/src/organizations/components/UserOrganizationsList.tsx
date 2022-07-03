import { useGetUserOrganizations } from '@organizations/hooks/api';

export const UserOrganizationsList = () => {
  const { data } = useGetUserOrganizations();
  console.log(data);
  return <></>;
};
