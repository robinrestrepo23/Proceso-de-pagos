function changeTheme() {
    const theme = document.getElementById("theme").value;
    applyTheme(theme);
    localStorage.setItem("theme", theme);
}

function applyTheme(theme) {
    const body = document.getElementById("body");
    const button = document.getElementById("submitButton");
    const container = document.querySelector(".container");

    if (theme === "dark") {
        body.style.backgroundColor = "#121212";
        body.style.color = "#ffffff";
        container.style.backgroundColor = "rgba(30,30,30,0.95)";
        button.classList.remove("btn-light");
        button.classList.add("btn-dark");
    } else {
        body.style.backgroundColor = "#ffffff";
        body.style.color = "#000000";
        container.style.backgroundColor = "rgba(255,255,255,0.95)";
        button.classList.remove("btn-dark");
        button.classList.add("btn-light");
    }

    document.getElementById("theme").value = theme;
}

window.onload = function () {
    const savedTheme = localStorage.getItem("theme") || "light";
    applyTheme(savedTheme);
};

function openCorrectModal() {
    const notificationType = document.getElementById("notificationType").value;

    if (notificationType === "email") {
        document.getElementById("emailModal").style.display = "block";
    } else if (notificationType === "sms") {
        document.getElementById("smsModal").style.display = "block";
    } else if (notificationType === "whatsapp") {
        document.getElementById("whatsappModal").style.display = "block";
    }
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

// function openPdfModal() {
//     document.getElementById("pdfModal").style.display = "block";
// }

function submitForm(type) {
    const paymentMethod = document.getElementById("paymentMethod").value;
    const amount = parseFloat(document.getElementById("amount").value);
    const theme = document.getElementById("theme").value;

    let recipient = "";
    let subject = "";
    let body = "";
    let priority = 1;

    if (type === "email") {
        recipient = document.getElementById("emailRecipient").value.trim();
        subject = document.getElementById("emailSubject").value.trim();
        body = document.getElementById("emailBody").value.trim();
        priority = parseInt(document.getElementById("emailPriority").value);
    } else if (type === "sms") {
        recipient = document.getElementById("smsRecipient").value.trim();
        body = document.getElementById("smsBody").value.trim();
        priority = parseInt(document.getElementById("smsPriority").value);
    } else if (type === "whatsapp") {
        recipient = document.getElementById("whatsappRecipient").value.trim();
        body = document.getElementById("whatsappBody").value.trim();
        priority = parseInt(document.getElementById("whatsappPriority").value);
    }

    if (!recipient || !paymentMethod || isNaN(amount)) {
        alert("Por favor llena todos los campos requeridos.");
        return;
    }

    // Primero procesar el pago con el tipo y monto
    fetch("/api/payments/process?paymentType=" + encodeURIComponent(paymentMethod) + "&amount=" + amount, {
        method: "POST"
    })
        .then(res => res.json())
        .then(result => {
            // Luego guardar el pago
            const payment = {
                paymentMethod: paymentMethod,
                amount: amount,
                amountFinal: result,
                subject: subject,
                body: body,
                notification: type,
                username: recipient,
                status: "Exitoso",
            };

            return fetch("/api/payments", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payment)
            });
        })
        .then(res => {
            if (!res.ok) throw new Error("Error al guardar el pago en la API.");
            return res.json();
        })
        .then(savedPayment => {
            //Mostrar mensaje de éxito
            document.getElementById("paymentMessage").innerText = "Pago procesado correctamente y notificación enviada a "+type+".";
            document.getElementById("paymentMessage").style.display = "block";
            document.getElementById('paymentIdField').value = savedPayment.transactionId;

            // Mostrar el botón para generar el PDF
            document.getElementById("generatePdfButton").style.display = "block";

            alert("✅ Pago procesado correctamente. ID: " + savedPayment.transactionId);
        })
        .catch(err => {
            alert("❌ Error al procesar el pago: " + err.message);
        });

    closeModal(type + "Modal");
    setTimeout(() => {
        form.submit();
    }, 300);
}

function deletePayment(id) {
    fetch(`/api/payments/${id}`, {
        method: "DELETE"
    })
        .then(res => {
            if (res.ok) {
                alert("✅ Pago eliminado correctamente.");
                // Aquí puedes actualizar la lista o tabla de pagos si tienes una
            } else {
                alert("❌ No se pudo eliminar el pago.");
            }
        });
}

document.getElementById("pdfForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const form = e.target;
    const formData = new FormData(form);

    fetch("/generate-pdf", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error al generar el PDF.");
            }
            return response.blob();
        })
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = "reporte_pago.pdf";
            document.body.appendChild(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);
        })
        .catch(err => {
            alert(err.message);
        });
});

function fetchPaymentHistory() {
    fetch("/api/payments")
        .then(response => {
            if (!response.ok) {
                throw new Error("Error al cargar el historial");
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.getElementById("paymentHistoryBody");
            tbody.innerHTML = ""; // Limpiar anteriores

            data.forEach(p => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${p.paymentMethod}</td>
                    <td>$${p.amount.toFixed(2)}</td>
                    <td>$${p.amountFinal.toFixed(2)}</td>
                    <td>${p.notification}</td>
                    <td>${p.username}</td>
                    <td>${p.status}</td>
                    <td>${p.timestamp}</td>
                `;
                tbody.appendChild(row);
            });

            document.getElementById("paymentHistory").style.display = "block";
        })
        .catch(error => {
            alert("❌ No se pudo cargar el historial de pagos.");
            console.error(error);
        });
}


// Función para mostrar el modal de PDF
function openPdfModal() {
    // Establece el paymentId en el campo oculto
    document.getElementById("pdfModal").style.display = "block";  // Mostrar el modal
}

// Función para cerrar el modal
function closePdfModal() {
    document.getElementById("pdfModal").style.display = "none";  // Ocultar el modal
}

// Cuando el usuario hace clic en el área fuera del modal, este se cierra
window.onclick = function(event) {
    const modal = document.getElementById('pdfModal');
    if (event.target === modal) {
        modal.style.display = "none";  // Cerrar el modal si se hace clic fuera de él
    }
}



function toggleLogoUpload() {
    const checkbox = document.getElementById("includeLogo");
    const logoSection = document.getElementById("logoUploadSection");
    logoSection.style.display = checkbox.checked ? "block" : "none";
}

