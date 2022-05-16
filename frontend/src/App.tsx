import { FormEvent, useState } from "react";
import { fetchEventSource } from "@microsoft/fetch-event-source";

let jwt: string | null = null;

const createBaseHeaders = (): Record<string, string> => {
  return jwt
    ? {
        Authorization: "Bearer " + jwt,
      }
    : {};
};

const get = (url: string) => {
  return fetch(url, { headers: createBaseHeaders() });
};

const post = async (url: string, body: any) => {
  const headers: Record<string, string> = {
    ...createBaseHeaders(),
    "Content-Type": "application/json",
  };

  const fetchRes = await fetch(url, {
    headers,
    method: "POST",
    body: JSON.stringify(body),
  });

  if (
    Array.from(fetchRes.headers.entries())
      .find(([name]) => name === "content-type")![1]
      .includes("text/plain")
  ) {
    return fetchRes.text();
  } else if (
    Array.from(fetchRes.headers.entries())
      .find(([name]) => name === "content-type")![1]
      .includes("application/json")
  ) {
    return fetchRes.json();
  } else {
    return null;
  }
};

export const App: React.FC = () => {
  const [registerForm, setRegisterForm] = useState({ email: "", password: "" });
  const [organizationForm, setOrganizationForm] = useState({ name: "" });
  const [projectForm, setProjectForm] = useState({ name: "", id: "" });
  const [invitationForm, setInvitationForm] = useState({
    email: "",
    organization: "",
  });
  const [joinOrganizationForm, setJoinOrganizationForm] = useState({
    organization: "",
  });

  const register = (e: FormEvent) => {
    e.preventDefault();
    post("/api/v1/user-management/users", registerForm);
  };

  const login1 = async (e: FormEvent) => {
    e.preventDefault();
    jwt = await post("/api/v1/user-management/users/authentication", {
      email: "test@test.com",
      password: "test",
    });
  };

  const login2 = async (e: FormEvent) => {
    e.preventDefault();
    jwt = await post("/api/v1/user-management/users/authentication", {
      email: "admin17@admin.com",
      password: "password",
    });
  };

  const createOrganization = (e: FormEvent) => {
    e.preventDefault();
    post("/api/v1/organization-management/organizations", organizationForm);
  };

  const createProject = (e: FormEvent) => {
    e.preventDefault();
    post(
      `/api/v1/organization-management/organizations/${projectForm.id}/projects`,
      { name: projectForm.name }
    );
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
    const orgs = await get("/api/v1/organization-management/organizations");

    console.log(orgs);
  };

  const getInvitations = async () => {
    const orgs = await get("/api/v1/organization-management/invitations");

    console.log(orgs);
  };

  const subscribe = async (e: FormEvent) => {
    e.preventDefault();
    const abortController = new AbortController();

    const x = fetchEventSource(
      `/api/v1/notification-management/notifications/users`,
      {
        signal: abortController.signal,
        headers: {
          ...createBaseHeaders(),
        },
        onmessage: (message: any) => {
          console.log(message);
          console.log(JSON.parse(message.data));
        },
        openWhenHidden: true,
      }
    );

    // setTimeout(() => {
    //   abortController.abort();
    //   console.log('abording')
    // }, 5000);
  };

  return (
    <div>
      <button onClick={login1}>Login1</button>
      <button onClick={login2}>Login2</button>
      <button onClick={getOrganizations}>Fetch Orgs</button>
      <button onClick={getInvitations}>Fetch Invitations</button>
      <form onSubmit={register}>
        <h2>Register form</h2>
        <label>Email</label>
        <input
          value={registerForm.email}
          onChange={(e) =>
            setRegisterForm({ ...registerForm, email: e.target.value })
          }
        />

        <label>Password</label>
        <input
          value={registerForm.password}
          onChange={(e) =>
            setRegisterForm({ ...registerForm, password: e.target.value })
          }
        />
        <button>Register</button>
      </form>

      <form onSubmit={createOrganization}>
        <h2>Organization form</h2>
        <label>Name</label>
        <input
          value={organizationForm.name}
          onChange={(e) =>
            setOrganizationForm({ ...organizationForm, name: e.target.value })
          }
        />

        <button>Create organization</button>
      </form>

      <form onSubmit={createProject}>
        <h2>Project form</h2>
        <label>id</label>
        <input
          value={projectForm.id}
          onChange={(e) =>
            setProjectForm({ ...projectForm, id: e.target.value })
          }
        />
        <label>Name</label>
        <input
          value={projectForm.name}
          onChange={(e) =>
            setProjectForm({ ...projectForm, name: e.target.value })
          }
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
          onChange={(e) =>
            setInvitationForm({ ...invitationForm, email: e.target.value })
          }
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

      <form onSubmit={subscribe}>
        <button>Subscribe</button>
      </form>
    </div>
  );
};
