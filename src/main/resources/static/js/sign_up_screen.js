const signupForm = document.getElementById("signupForm");

signupForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return;
    }

    const user = {
        name: name,
        email: email,
        password: password
    };

    try {
        const response = await fetch("/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        });

        const message = await response.text();

        if (response.ok) {
            alert(message);
            signupForm.reset();
        } else {
            alert("Error: " + message);
        }
    } catch (error) {
        alert("Could not connect to the server.");
        console.error(error);
    }
});