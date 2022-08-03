import { MemberInvitationForm } from '@organizations/components';
import { OrganizationParams } from '@organizations/types';
import { FormBox, Layout } from '@shared/components';
import { useParams } from '@shared/hooks/api';

export const MemberInvitationPage: React.FC = () => {
  const params = useParams<OrganizationParams>();
  return (
    <Layout>
      <FormBox heading="Invite member">
        <MemberInvitationForm {...params} />
      </FormBox>
    </Layout>
  );
};
