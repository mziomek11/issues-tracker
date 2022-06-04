/* eslint-disable */

import { FormEvent, useState } from 'react';
import { post, get } from './http';

export const Organizations = () => {
  const [organizationForm, setOrganizationForm] = useState({ name: '' });
  const [projectForm, setProjectForm] = useState({ name: '', id: '' });
  const [invitationForm, setInvitationForm] = useState({
    email: '',
    organization: '',
  });
  const [joinOrganizationForm, setJoinOrganizationForm] = useState({
    organization: '',
  });

  const createOrganization = (e: FormEvent) => {
    e.preventDefault();
    post('/api/v1/organization-management/organizations', organizationForm);
  };

  const createProject = (e: FormEvent) => {
    e.preventDefault();
    post(`/api/v1/organization-management/organizations/${projectForm.id}/projects`, {
      name: projectForm.name,
    });
  };

  const inviteMember = (e: FormEvent) => {
    e.preventDefault();
    post(
      `/api/v1/organization-management/organizations/${invitationForm.organization}/invitations`,
      {
        email: invitationForm.email,
      }
    );
  };

  const joinOrganization = (e: FormEvent) => {
    e.preventDefault();
    post(
      `/api/v1/organization-management/organizations/${joinOrganizationForm.organization}/members`,
      {}
    );
  };

  const getOrganizations = async () => {
    const orgs = await get('/api/v1/organization-management/organizations');

    console.log(orgs);
  };

  const getInvitations = async () => {
    const orgs = await get('/api/v1/organization-management/invitations');

    console.log(orgs);
  };

  return (
    <div>
      <h1>Organizations</h1>
      <button onClick={getOrganizations}>Fetch Orgs</button>
      <button onClick={getInvitations}>Fetch Invitations</button>

      <form onSubmit={createOrganization}>
        <h2>Organization form</h2>
        <label>Name</label>
        <input
          value={organizationForm.name}
          onChange={(e) => setOrganizationForm({ ...organizationForm, name: e.target.value })}
        />

        <button>Create organization</button>
      </form>

      <form onSubmit={createProject}>
        <h2>Project form</h2>
        <label>id</label>
        <input
          value={projectForm.id}
          onChange={(e) => setProjectForm({ ...projectForm, id: e.target.value })}
        />
        <label>Name</label>
        <input
          value={projectForm.name}
          onChange={(e) => setProjectForm({ ...projectForm, name: e.target.value })}
        />

        <button>Create project</button>
      </form>

      <form onSubmit={inviteMember}>
        <h2>Invitation form</h2>

        <label>Organization id</label>
        <input
          value={invitationForm.organization}
          onChange={(e) =>
            setInvitationForm({
              ...invitationForm,
              organization: e.target.value,
            })
          }
        />

        <label>Email</label>
        <input
          value={invitationForm.email}
          onChange={(e) => setInvitationForm({ ...invitationForm, email: e.target.value })}
        />

        <button>Invite member</button>
      </form>

      <form onSubmit={joinOrganization}>
        <h2>Join organizations form</h2>

        <label>Organization id</label>
        <input
          value={joinOrganizationForm.organization}
          onChange={(e) =>
            setJoinOrganizationForm({
              ...joinOrganizationForm,
              organization: e.target.value,
            })
          }
        />

        <button>Join organization</button>
      </form>
    </div>
  );
};
