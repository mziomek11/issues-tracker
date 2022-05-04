export const App: React.FC = () => {
  const handleRegisterClick = () => {
    fetch("/api/v1/user-management/users", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify({ a: 1, b: 2 }),
    }).then((res) => {
      console.log(res);
    });
  };

  return (
    <h1>
      <button onClick={handleRegisterClick}>Register</button>
    </h1>
  );
};
