import {postData} from "/js/api.js";

document.getElementById("loginForm").addEventListener("submit", async ev => {
    ev.preventDefault(); //防止頁面重新載入

    let acc = document.getElementById("acc").value
    let pwd = document.getElementById("pwd").value

    const response = await postData("api/auth/v1/login", {acc, pwd})
    if (response.data && response.data.token) {
        localStorage.setItem("token", response.data.token)
        window.location.href = "home.html";
    }
    console.log('data:', JSON.stringify(response))
})