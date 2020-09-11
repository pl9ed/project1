let user = JSON.parse(sessionStorage.getItem("user_obj"));
console.log(JSON.stringify(user));
let user_id = user.user_ID;
let today = new Date().getTime();

async function createReimbursement() {
    let amount = document.getElementById("amount").value;
    let type_id = document.getElementById("type_id").value;
    let description = document.getElementById("description").value;
    let receipt = document.getElementById("receipt").files[0];

    let r_file = new FormData();
    r_file.append("receipt", receipt);

    if (amount < 0) {
        alert("Cannot have negative reimbursements!");
    }
    // null check
    else if (amount && type_id && receipt) {

        let r = {
            amount: amount,
            type_ID: type_id,
            description: description,
            author: user_id
        };

        try {
            let response = await fetch(process.env.SERVER_URL + "project1/apply", {
                method: 'PUT',
                body: JSON.stringify(r),
                withCredentials: true
            });

            // Image
            response = await fetch(process.env.SERVER_URL + "project1/apply", {
                method: 'POST',
                body: r_file,
                withCredentials: true
            });

            window.location.href = process.env.SERVER_URL + "project1/EmployeeHome";

        } catch (error) {
            alert("Error submitting your reimbursement. Please try again.")
            console.log(error);
        }
    } else {
        alert("Missing required fields!");
    }

}

async function goHome() {
    sessionStorage.clear();
    window.location.href = process.env.SERVER_URL + "project1/index";
}

async function goBack() {
    window.location.href = process.env.SERVER_URL + "project1/EmployeeHome";
}
