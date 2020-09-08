// run on page load -> default to 

let username = sessionStorage.getItem("username");
loadPage(username);

async function loadPage(username) {
    var user;
    try {
        let response = await fetch("http://localhost:8006/project1/login?username=" + username);
        user = await response.json();
        sessionStorage.setItem("user_obj", JSON.stringify(user));

        if (sessionStorage.getItem("user_obj").role_ID < 0) {
            window.location.href = "./401";
        }

        let list = await getAllReimb(user.user_ID);
        sessionStorage.setItem("allReimb", JSON.stringify(list));

        generateTable();

    } catch (error) {
        alert("Error getting user data!");
        console.log(error);
    }
}

async function refreshPage() {
    let user = JSON.parse(sessionStorage.getItem("user_obj"));
    console.log(user);
    console.log(user.user_ID);
    let list = await getAllReimb(user.user_ID);
    sessionStorage.setItem("allReimb", JSON.stringify(list));

    generateTable();
}

async function getAllReimb(id) {
    try {
        let response = await fetch("http://localhost:8006/project1/FMPortal?id=" + id, {
            withCredentials: true
        });
        let list = await response.text();
        list = list.trim().split("@@@");

        // remove last element, if multiple
        // split includes empty final element since the string ends with @@@
        if (list.length > 1) {
            list.pop();
        }

        return list;

    } catch (error) {
        alert("Error getting user data!");
        console.log(error);
    }
}

// ---------------------- Filter Methods -----------------------------------

function searchByTerm() {
    let searchBy = document.getElementById("dropdown_selection").value;
    let searchTerm = document.getElementById("search_term").value;

    if (searchBy != "default") {
        console.log(searchBy);
        console.log(searchTerm);

        let list = JSON.parse(sessionStorage.getItem("allReimb"));

        list = list.filter(function (item) {
            let temp = JSON.parse(item)[searchBy].toString();
            temp = temp.toLowerCase();
            searchTerm = searchTerm.toLowerCase();

            return temp.includes(searchTerm);
        })

        console.log(list);
        generateTable(list);
    }
}

// -------------------    Logout Button     ------------------------------

function goHomeFM() {
    sessionStorage.clear();
    window.location.href = "http://localhost:8006/project1/FMLogin";
}

// -------------------   View Methods  ----------------------------------
async function generateTable(list) {
    if (list == null) {
        list = JSON.parse(sessionStorage.getItem("allReimb"));
    }

    let pending = document.getElementById("check_pending").checked;
    let approved = document.getElementById("check_approved").checked;
    let denied = document.getElementById("check_denied").checked;

    // remove unchecked
    if (!pending) {
        list = list.filter(function (item) {
            return JSON.parse(item).STATUS != "PENDING";
        })
    }
    if (!approved) {
        list = list.filter(function (item) {
            return JSON.parse(item).STATUS != "APPROVED";
        })
    }
    if (!denied) {
        list = list.filter(function (item) {
            return JSON.parse(item).STATUS != "DENIED";
        })
    }

    // wipe old table
    let new_body = document.createElement('tbody');
    let old_body = document.getElementById("main_table");
    old_body.parentNode.replaceChild(new_body, old_body);
    new_body.setAttribute("id", "main_table");

    for (let reimb of list) {
        reimb = JSON.parse(reimb);

        let table = document.getElementById("main_table");
        let tr = table.insertRow(-1);

        let id_col = tr.insertCell(0);
        let auth_col = tr.insertCell(1);
        let amount_col = tr.insertCell(2);
        let submit_col = tr.insertCell(3);
        let status_col = tr.insertCell(4);
        let but_col = tr.insertCell(5);

        let id = document.createTextNode(reimb.REIMB_ID);
        let auth = document.createTextNode(reimb.AUTHOR);
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
        auth_col.appendChild(auth);
        amount_col.appendChild(amount);
        submit_col.appendChild(submit);
        status_col.appendChild(status);
        but_col.appendChild(but);
    }

    $("td").each(function () {
        $(this).addClass("align-middle");
    })
}

let view = document.getElementById("reimb_modal_title");
let v_a = document.getElementById("view_author");
let v_amt = document.getElementById("view_amount");
let v_sub = document.getElementById("view_submitted");
let v_t = document.getElementById("view_type");
let v_s = document.getElementById("view_status");
let v_resolver = document.getElementById("view_resolver");
let v_resolved = document.getElementById("view_resolved");
let v_d = document.getElementById("view_description");
let v_i = document.getElementById("view_receipt");

async function viewReimb(reimb_ID) {
    let response = await fetch("http://localhost:8006/project1/view?reimb_ID=" + reimb_ID, {
        withCredentials: true
    });
    let r = await response.json();
    sessionStorage.setItem("currentlyViewing", reimb_ID);

    view.innerHTML = "Reimbursement ID: " + reimb_ID;
    v_a.innerHTML = await r.author;
    v_amt.innerHTML = await r.amount;

    let submit_date = r.submitted;
    let submit_string = submit_date.month + " " + submit_date.dayOfMonth + ", " + submit_date.year;
    v_sub.innerHTML = submit_string;

    v_t.innerHTML = r.reimb_TYPE;
    v_s.innerHTML = r.status;

    let footer = document.getElementById("modal_footer");
    if (r.resolver) {
        let resolve_date = r.resolved;
        let resolve_string = resolve_date.month + " " + resolve_date.dayOfMonth + ", " + resolve_date.year;
        v_resolved.innerHTML = resolve_string;
        v_resolver.innerHTML = r.resolver;
        footer.setAttribute("style", "visibility:hidden");
    } else {
        v_resolved.innerHTML = "";
        v_resolver.innerHTML = "";
        footer.setAttribute("style", "visibility:visible");

    }

    v_d.innerHTML = r.description;

    // parse filetype
    let filetype = r.fileName.split(".");

    v_i.setAttribute("src", "data:image/" + filetype[1] + ";base64," + r.receipt);
}

async function processReimb(bool) {
    let id = JSON.parse(sessionStorage.getItem("user_obj")).user_ID;
    console.log(id);
    let reimb_id = parseInt(sessionStorage.getItem("currentlyViewing"));
    bool = parseInt(bool) ? 1 : -1;

    let app = {
        reimb_ID: reimb_id,
        isApproved: bool,
        resolver: id
    }

    let response = await fetch("http://localhost:8006/project1/FMPortal", {
        method: 'PUT',
        body: JSON.stringify(app)
    });

    if (!response) {
        alert("There was an error processing that reimbursement!");
    }
}

$("#reimb_modal").on('hidden.bs.modal', function () {
    refreshPage();
    clearModal();
});

function clearModal() {
    v_a.innerHTML = "";
    v_amt.innerHTML = "";
    v_sub.innerHTML = "";
    v_t.innerHTML = "";
    v_s.innerHTML = "";
    v_resolver.innerHTML = "";
    v_resolved.innerHTML = "";
    v_d.innerHTML = "";
    v_i.innerHTML = "";
}

async function applySearch() {
    // window.location.href = "http://localhost:8006/project1/apply"
}

