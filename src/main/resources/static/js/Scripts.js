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

function openPdfModal() {
    document.getElementById("pdfModal").style.display = "block";
}



function submitForm(type) {
    const form = document.getElementById("paymentForm");

    // Primero limpiamos los inputs ocultos anteriores
    Array.from(form.querySelectorAll('input[type="hidden"]')).forEach(input => {
        if (input.name !== "recipient") {
            form.removeChild(input);
        }
    });

    let recipient = "";
    let subject = "";
    let body = "";
    let priority = "";

    if (type === "email") {
        recipient = document.getElementById("emailRecipient").value.trim();
        subject = document.getElementById("emailSubject").value.trim();
        body = document.getElementById("emailBody").value.trim();
        priority = document.getElementById("emailPriority").value;
    } else if (type === "sms") {
        recipient = document.getElementById("smsRecipient").value.trim();
        body = document.getElementById("smsBody").value.trim();
        priority = document.getElementById("smsPriority").value;
        subject = ""; // SMS no usa asunto
    } else if (type === "whatsapp") {
        recipient = document.getElementById("whatsappRecipient").value.trim();
        body = document.getElementById("whatsappBody").value.trim();
        priority = document.getElementById("whatsappPriority").value;
        subject = ""; // WhatsApp no usa asunto
    }

    if (!recipient) {
        alert("Por favor llena los datos requeridos.");
        return;
    }

    // Asignar el recipient al input oculto
    document.getElementById("recipientHidden").value = recipient;

    document.getElementById("notificationType").value= type;
    // Agregar otros datos ocultos
    addHiddenInput(form, "subject", subject);
    addHiddenInput(form, "body", body);
    addHiddenInput(form, "priority", priority);

    closeModal(type + "Modal");

    setTimeout(() => {
        form.submit();
    }, 300);
}

function addHiddenInput(form, name, value) {
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = name;
    input.value = value;
    form.appendChild(input);
}
document.getElementById("pdfForm").addEventListener("submit", function (e) {
    e.preventDefault(); // Evita el envío tradicional

    const form = e.target;
    const formData = new FormData(form);

    fetch("/generate-pdf", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) throw new Error("Error al generar el PDF");
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
            closeModal("pdfModal"); // Cierra el modal
        })
        .catch(err => {
            alert("Error al generar el PDF: " + err.message);
        });
});

function toggleLogoUpload() {
    const checkbox = document.getElementById("includeLogo");
    const logoSection = document.getElementById("logoUploadSection");
    logoSection.style.display = checkbox.checked ? "block" : "none";
}