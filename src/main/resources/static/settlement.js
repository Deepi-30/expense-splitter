const summaryDiv = document.getElementById("summaryDiv");
const settlementTableBody = document.querySelector("#settlementTable tbody");

const settlements = JSON.parse(localStorage.getItem("settlementSummary") || "[]");

if (settlements.length === 0) {
  summaryDiv.textContent = "No settlement data found. Please calculate shares first.";
} else {
  // First 2 lines: total spent & per person
  summaryDiv.innerHTML = settlements[0] + "<br>" + settlements[1];

  // Remaining lines: "X pays ₹Y to Z"
  for (let i = 2; i < settlements.length; i++) {
    const line = settlements[i];
    const match = line.match(/(.+) pays ₹([\d.]+) to (.+)/);
    if (match) {
      const from = match[1];
      const amount = match[2];
      const to = match[3];

      const row = document.createElement("tr");
      row.innerHTML = `<td>${from}</td><td>${to}</td><td>${amount}</td>`;
      settlementTableBody.appendChild(row);
    }
  }
}

// Go back to share page
function goBack() {
  window.location.href = "share.html";
}
