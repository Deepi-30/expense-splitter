const API_BASE = "http://localhost:8080"; // Adjust if backend runs elsewhere

// ========== LOGIN ==========
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;

    try {
      const res = await fetch(`${API_BASE}/users/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (!res.ok) throw new Error("Login failed");
      const user = await res.json();

      document.getElementById("loginMessage").textContent =
        "✅ Login successful!";
      localStorage.setItem("user", JSON.stringify(user));

      // Redirect to dashboard
      setTimeout(() => (window.location.href = "dashboard.html"), 1000);
    } catch (err) {
      document.getElementById("loginMessage").textContent =
        "❌ Invalid credentials";
      document.getElementById("loginMessage").style.color = "red";
    }
  });
}

// ========== REGISTER ==========
const registerForm = document.getElementById("registerForm");
if (registerForm) {
  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("registerName").value;
    const email = document.getElementById("registerEmail").value;
    const password = document.getElementById("registerPassword").value;

    try {
      const res = await fetch(`${API_BASE}/users/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password }),
      });

      if (!res.ok) throw new Error("Registration failed");
      await res.json();

      document.getElementById("registerMessage").textContent =
        " Registration successful! Redirecting to login...";
      document.getElementById("registerMessage").style.color = "green";

      // Redirect to login page after success
      setTimeout(() => (window.location.href = "index.html"), 1500);
    } catch (err) {
      document.getElementById("registerMessage").textContent =
        " Registration failed. Try again.";
      document.getElementById("registerMessage").style.color = "red";
    }
  });
}
