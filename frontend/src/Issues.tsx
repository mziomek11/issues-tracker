import { FormEvent, useState } from "react";
import { get, post, patch, deletee } from "./http";

export const Issues = () => {
  const [issueForm, setIssueForm] = useState({
    name: "",
    content: "Some content",
    type: "BUG",
    organization: "",
    project: "",
  });
  const [changeContentForm, setChangeContentForm] = useState({
    content: "",
    issue: "",
    organization: "",
    project: "",
  });
  const [changeTypeForm, setChangeTypeForm] = useState({
    type: "ENHANCEMENT",
    issue: "",
    organization: "",
    project: "",
  });
  const [renameIssueForm, setRenameIssueForm] = useState({
    name: "",
    issue: "",
    organization: "",
    project: "",
  });
  const [voteIssueForm, setVoteIssueForm] = useState({
    voteType: "UP",
    issue: "",
    organization: "",
    project: "",
  });
  const [closeIssueForm, setCloseIssueForm] = useState({
    issue: "",
    organization: "",
    project: "",
  });
  const [issuesForm, setIssuesForm] = useState({
    organization: "",
    project: "",
  });

  const createIssues = (e: FormEvent) => {
    e.preventDefault();
    const { project, organization, ...dto } = issueForm;
    post(
      `/api/v1/issue-management/organizations/${organization}/projects/${project}/issues`,
      dto
    );
  };

  const renameIssues = (e: FormEvent) => {
    e.preventDefault();
    const { issue, organization, project, ...dto } = renameIssueForm;

    patch(
      `/api/v1/issue-management/organizations/${organization}/projects/${project}/issues/${issue}/name`,
      dto
    );
  };

  const changeContentIssues = (e: FormEvent) => {
    e.preventDefault();
    const { issue, organization, project, ...dto } = changeContentForm;

    patch(
      `/api/v1/issue-management/organizations/${organization}/projects/${project}/issues/${issue}/content`,
      dto
    );
  };

  const changeTypeIssues = (e: FormEvent) => {
    e.preventDefault();
    const { issue, organization, project, ...dto } = changeTypeForm;

    patch(
      `/api/v1/issue-management/organizations/${organization}/projects/${project}/issues/${issue}/type`,
      dto
    );
  };

  const voteIssue = (e: FormEvent) => {
    e.preventDefault();
    const { issue, organization, project, ...dto } = voteIssueForm;

    post(
      `/api/v1/issue-management/organizations/${organization}/projects/${project}/issues/${issue}/votes`,
      dto
    );
  };

  const closeIssue = (e: FormEvent) => {
    e.preventDefault();
    const { issue, organization, project } = closeIssueForm;

    deletee(
      `/api/v1/issue-management/organizations/${organization}/projects/${project}/issues/${issue}`
    );
  };

  const getIssues = async (e: FormEvent) => {
    e.preventDefault();
    const { organization, project } = issuesForm;
    const issues = await get(
      `/api/v1/issue-management/organizations/${organization}/projects/${project}/issues`
    );

    console.log(issues);
  };

  return (
    <div>
      <h1>Issues</h1>
      <form onSubmit={getIssues}>
        <h2>Get issues form</h2>
        <label>Organization</label>
        <input
          value={issuesForm.organization}
          onChange={(e) =>
            setIssuesForm({ ...issuesForm, organization: e.target.value })
          }
        />

        <label>Project</label>
        <input
          value={issuesForm.project}
          onChange={(e) =>
            setIssuesForm({ ...issuesForm, project: e.target.value })
          }
        />

        <button>Get Issues</button>
      </form>

      <form onSubmit={createIssues}>
        <h2>Create issue form</h2>
        <label>Organization</label>
        <input
          value={issueForm.organization}
          onChange={(e) =>
            setIssueForm({ ...issueForm, organization: e.target.value })
          }
        />

        <label>Project</label>
        <input
          value={issueForm.project}
          onChange={(e) =>
            setIssueForm({ ...issueForm, project: e.target.value })
          }
        />

        <label>Name</label>
        <input
          value={issueForm.name}
          onChange={(e) => setIssueForm({ ...issueForm, name: e.target.value })}
        />

        <button>Create Issue</button>
      </form>

      <form onSubmit={renameIssues}>
        <h2>Rename issue form</h2>
        <label>Organization</label>
        <input
          value={renameIssueForm.organization}
          onChange={(e) =>
            setRenameIssueForm({
              ...renameIssueForm,
              organization: e.target.value,
            })
          }
        />

        <label>Project</label>
        <input
          value={renameIssueForm.project}
          onChange={(e) =>
            setRenameIssueForm({ ...renameIssueForm, project: e.target.value })
          }
        />

        <label>Issue</label>
        <input
          value={renameIssueForm.issue}
          onChange={(e) =>
            setRenameIssueForm({ ...renameIssueForm, issue: e.target.value })
          }
        />

        <label>Name</label>
        <input
          value={renameIssueForm.name}
          onChange={(e) =>
            setRenameIssueForm({ ...renameIssueForm, name: e.target.value })
          }
        />

        <button>Rename Issue</button>
      </form>

      <form onSubmit={changeContentIssues}>
        <h2>Change content issue form</h2>
        <label>Organization</label>
        <input
          value={changeContentForm.organization}
          onChange={(e) =>
            setChangeContentForm({
              ...changeContentForm,
              organization: e.target.value,
            })
          }
        />

        <label>Project</label>
        <input
          value={changeContentForm.project}
          onChange={(e) =>
            setChangeContentForm({
              ...changeContentForm,
              project: e.target.value,
            })
          }
        />

        <label>Issue</label>
        <input
          value={changeContentForm.issue}
          onChange={(e) =>
            setChangeContentForm({
              ...changeContentForm,
              issue: e.target.value,
            })
          }
        />

        <label>Content</label>
        <input
          value={changeContentForm.content}
          onChange={(e) =>
            setChangeContentForm({
              ...changeContentForm,
              content: e.target.value,
            })
          }
        />

        <button>Change content Issue</button>
      </form>

      <form onSubmit={changeTypeIssues}>
        <h2>Change type issue form</h2>
        <label>Organization</label>
        <input
          value={changeTypeForm.organization}
          onChange={(e) =>
            setChangeTypeForm({
              ...changeTypeForm,
              organization: e.target.value,
            })
          }
        />

        <label>Project</label>
        <input
          value={changeTypeForm.project}
          onChange={(e) =>
            setChangeTypeForm({
              ...changeTypeForm,
              project: e.target.value,
            })
          }
        />

        <label>Issue</label>
        <input
          value={changeTypeForm.issue}
          onChange={(e) =>
            setChangeTypeForm({
              ...changeTypeForm,
              issue: e.target.value,
            })
          }
        />

        <button>Change type Issue</button>
      </form>

      <form onSubmit={voteIssue}>
        <h2>Vote issue form</h2>
        <label>Organization</label>
        <input
          value={voteIssueForm.organization}
          onChange={(e) =>
            setVoteIssueForm({
              ...voteIssueForm,
              organization: e.target.value,
            })
          }
        />

        <label>Project</label>
        <input
          value={voteIssueForm.project}
          onChange={(e) =>
            setVoteIssueForm({
              ...voteIssueForm,
              project: e.target.value,
            })
          }
        />

        <label>Issue</label>
        <input
          value={voteIssueForm.issue}
          onChange={(e) =>
            setVoteIssueForm({
              ...voteIssueForm,
              issue: e.target.value,
            })
          }
        />

        <button>Vote Issue</button>
      </form>

      <form onSubmit={closeIssue}>
        <h2>Close issue form</h2>
        <label>Organization</label>
        <input
          value={closeIssueForm.organization}
          onChange={(e) =>
            setCloseIssueForm({
              ...closeIssueForm,
              organization: e.target.value,
            })
          }
        />

        <label>Project</label>
        <input
          value={closeIssueForm.project}
          onChange={(e) =>
            setCloseIssueForm({ ...closeIssueForm, project: e.target.value })
          }
        />

        <label>Issue</label>
        <input
          value={closeIssueForm.issue}
          onChange={(e) =>
            setCloseIssueForm({ ...closeIssueForm, issue: e.target.value })
          }
        />

        <button>Close Issue</button>
      </form>
    </div>
  );
};

export default Issues;
