function getUsername() {
    let u = document.getElementById("user").value.toUpperCase();
    sessionStorage.setItem("username", u);
    console.log(sessionStorage.getItem("username"));
}

function fmLogin() {
    window.location.href = "./FMLogin";
}
