const shareInputsDiv = document.getElementById("shareInputs");
const shareForm = document.getElementById("shareForm");
const shareMessage = document.getElementById("shareMessage");

const numPersonsInput = document.getElementById("numPersons");
const setPersonsBtn = document.getElementById("setPersonsBtn");

// Store persons data
let persons = [];

// Step 1: Set number of persons
setPersonsBtn.addEventListener("click", (e) => {
  e.preventDefault();
  const numPersons = parseInt(numPersonsInput.value);
  if (isNaN(numPersons) || numPersons < 1) {
    alert("Please enter a valid number of persons.");
    return;
  }

  // Clear previous inputs
  shareInputsDiv.innerHTML = "";
  persons = [];

  // Create input fields for each person
  for (let i = 1; i <= numPersons; i++) {
    const row = document.createElement("div");
    row.style.marginBottom = "10px";
    row.innerHTML = `
      <label>Person ${i} Name:</label>
      <input type="text" class="personName" placeholder="Name" required>
      <label>Paid Amount:</label>
      <input type="number" min="0" step="0.01" class="paidAmount" placeholder="Paid amount" required>
    `;
    shareInputsDiv.appendChild(row);
  }

  // Show the form
  shareForm.style.display = "block";
});

// Step 2: Handle form submission
shareForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const nameInputs = shareInputsDiv.querySelectorAll(".personName");
  const amountInputs = shareInputsDiv.querySelectorAll(".paidAmount");

  persons = [];
  for (let i = 0; i < nameInputs.length; i++) {
    const name = nameInputs[i].value.trim();
    const amount = parseFloat(amountInputs[i].value);
    if (!name || isNaN(amount)) {
      alert("Please fill in all names and paid amounts correctly.");
      return;
    }
    persons.push({ name, paidAmount: amount });
  }

  console.log("Persons data:", persons);

  // Calculate settlement locally
  calculateSettlement(persons);
});

// Step 3: Local settlement calculation
function calculateSettlement(persons) {
  let total = 0;
  persons.forEach(p => total += p.paidAmount);
  const equalShare = total / persons.length;

  const settlements = [];
  settlements.push(`Total Spent: ₹${total.toFixed(2)}`);
  settlements.push(`Each person should pay: ₹${equalShare.toFixed(2)}`);

  const balances = persons.map(p => ({
    name: p.name,
    balance: p.paidAmount - equalShare
  }));

  const creditors = balances.filter(b => b.balance > 0);
  const debtors = balances.filter(b => b.balance < 0);

  let i = 0, j = 0;
  while (i < debtors.length && j < creditors.length) {
    const debtor = debtors[i];
    const creditor = creditors[j];
    const amountToPay = Math.min(-debtor.balance, creditor.balance);

    settlements.push(`${debtor.name} pays ₹${amountToPay.toFixed(2)} to ${creditor.name}`);

    debtor.balance += amountToPay;
    creditor.balance -= amountToPay;

    if (Math.abs(debtor.balance) < 0.01) i++;
    if (Math.abs(creditor.balance) < 0.01) j++;
  }

  console.log("Settlement Summary:", settlements);

  // Store in localStorage and redirect
  localStorage.setItem("settlementSummary", JSON.stringify(settlements));
  window.location.href = "settlement.html";
}
