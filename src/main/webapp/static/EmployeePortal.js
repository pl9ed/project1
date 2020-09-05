let username = sessionStorage.getItem("username");
getUser(username);

async function getUser(username) {
    let bool = true;
    try {
        let response = await fetch("http://localhost:8006/project1/login?username=" + username);
        user = await response.json();
        sessionStorage.setItem("user_obj", JSON.stringify(user));

    } catch (error) {
        alert("Error getting user data!");
        console.log(error);
    }
    console.log(typeof (user))

    console.log(user.first_NAME);
    let title = document.getElementById("title");
    title.innerHTML = "WELCOME, " + user.first_NAME + "!";

    console.log(user);
    console.log(user.user_ID);
    console.log(typeof (user.user_ID));
    getAllReimbFor(user.user_ID);

}
user = sessionStorage.getItem("user_obj");

async function goHome() {
    sessionStorage.clear();
    window.location.href = "http://localhost:8006/project1/index";
}

async function getAllReimbFor(id) {
    try {
        console.log("id: " + id)
        let response = await fetch("http://localhost:8006/project1/EmployeePortal?id=" + id, {
            withCredentials: true
        });
        let list = await response.text();
        list = list.trim().split("@@@");

        // remove last element
        // split includes empty final element since the string ends with @@@
        list.pop();

        // add row for each reimb
        for (let reimb of list) {
            reimb = JSON.parse(reimb);

            let table = document.getElementById("reimb_table");
            let tr = table.insertRow(-1);

            let id_col = tr.insertCell(0);
            let amount_col = tr.insertCell(1);
            let submit_col = tr.insertCell(2);
            let status_col = tr.insertCell(3);
            let but_col = tr.insertCell(4);

            let id = document.createTextNode(reimb.REIMB_ID);
            let amount = document.createTextNode(reimb.AMOUNT);
            let submit = document.createTextNode(reimb.SUBMITTED);
            let status = document.createTextNode(reimb.STATUS);
            let but = document.createElement("button");

            // color code status
            if (reimb.STATUS == "APPROVED") {
                status_col.style.color = "green";
            } else if (reimb.STATUS == "DENIED") {
                status_col.style.color = "red";
            }

            status_col.style.fontWeight = "bold";

            // button settings
            but.setAttribute("class", "btn btn-info");
            but.setAttribute("onclick", "viewReimb(" + `"${reimb.REIMB_ID}"` + ")");
            but.setAttribute("id", `"${reimb.REIMB_ID}"`);
            but.setAttribute("data-toggle", "modal");
            but.setAttribute("data-target", "#reimb_modal")
            but.textContent = "View Details";

            id_col.appendChild(id);
            amount_col.appendChild(amount);
            submit_col.appendChild(submit);
            status_col.appendChild(status);
            but_col.appendChild(but);
        }

    } catch (error) {
        alert("Error getting user data!");
        console.log(error);
    }

    $("td").each(function () {
        $(this).addClass("align-middle");
    })

}

async function viewReimb(reimb_ID) {
    let response = await fetch("http://localhost:8006/project1/view?reimb_ID=" + reimb_ID, {
        withCredentials: true
    });
    let r = await response.json();

    let view = document.getElementById("reimb_modal_title");
    view.innerHTML = "Reimbursement ID: " + reimb_ID;

    let v_a = document.getElementById("view_author");
    let v_amt = document.getElementById("view_amount");
    let v_sub = document.getElementById("view_submitted");
    let v_t = document.getElementById("view_type");
    let v_s = document.getElementById("view_status");
    let v_resolver = document.getElementById("view_resolver");
    let v_resolved = document.getElementById("view_resolved");
    let v_d = document.getElementById("view_description");
    let v_i = document.getElementById("view_receipt");

    v_a.innerHTML = user.first_NAME + " " + user.last_NAME;
    v_amt.innerHTML = r.amount;

    let submit_date = r.submitted;
    let submit_string = submit_date.month + " " + submit_date.dayOfMonth + ", " + submit_date.year;
    v_sub.innerHTML = submit_string;

    v_t.innerHTML = r.reimb_TYPE;
    v_s.innerHTML = r.status;


    console.log(r);
    if (r.resolver) {
        let resolve_date = r.resolved;
        let resolve_string = resolve_date.month + " " + resolve_date.dayOfMonth + ", " + resolve_date.year;
        v_resolved.innerHTML = resolve_string;
        v_resolver.innerHTML = r.resolver;
    } else {
        v_resolved.innerHTML = "";
        v_resolver.innerHTML = "";
    }

    v_d.innerHTML = r.description;

    // parse filetype
    let filetype = r.fileName.split(".");

    v_i.setAttribute("src", "data:image/" + filetype[1] + ";base64," + r.receipt);
    console.log(v_i);

}

async function newReimb() {
    window.location.href = "http://localhost:8006/project1/apply"
}
