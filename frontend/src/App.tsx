import { FormEvent, useState } from "react";

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

  const register = (e: FormEvent) => {
    e.preventDefault();
    post("/api/v1/user-management/users", registerForm);
  };

  const login = async (e: FormEvent) => {
    e.preventDefault();
    jwt = await post("/api/v1/user-management/users/authentication", {
      email: "test@test.com",
      password: "test",
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

  const getOrganizations = async () => {
    const orgs = await get("/api/v1/organization-management/organizations");

    console.log(orgs);
  };

  return (
    <div>
      <button onClick={login}>Login</button>
      <button onClick={getOrganizations}>Fetch Orgs</button>
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
    </div>
  );
};
