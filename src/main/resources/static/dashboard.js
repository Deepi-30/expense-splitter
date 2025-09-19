const API_BASE = "http://localhost:8080"; // Adjust if backend runs elsewhere

const expenseForm = document.getElementById("expenseForm");

if (expenseForm) {
  expenseForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const description = document.getElementById("expenseDescription").value;
    const totalAmount = parseFloat(document.getElementById("expenseAmount").value);

    const user = JSON.parse(localStorage.getItem("user"));
    if (!user) {
      alert("Please login first");
      window.location.href = "index.html";
      return;
    }

    try {
      const res = await fetch(`${API_BASE}/expenses`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          description,
          totalAmount,
          user: { id: user.id }
        }),
      });

      if (!res.ok) throw new Error("Failed to add expense");

      const expense = await res.json();

      document.getElementById("expenseMessage").textContent =
        "✅ Expense added! Redirecting to add shares...";

      // Save expense ID for the next page
      localStorage.setItem("currentExpenseId", expense.id);

      // Redirect to share.html after 1 second
      setTimeout(() => (window.location.href = "share.html"), 1000);

    } catch (err) {
      document.getElementById("expenseMessage").textContent =
        "❌ Failed to add expense. Try again.";
      document.getElementById("expenseMessage").style.color = "red";
    }
  });
}
