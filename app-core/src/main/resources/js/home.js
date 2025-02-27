import {getData, postData, postData_t} from "/js/api.js";
import * as account from "/js/account.js";

document.addEventListener("DOMContentLoaded", function () {
    showSection('accounts'); // 預設顯示帳號清單
    account.loadAccounts();
    loadRoles();

    document.getElementById("logoutBtn").addEventListener("click",logout)
});

function showSection(section) {
    document.getElementById("accountsSection").classList.add("hidden");
    document.getElementById("rolesSection").classList.add("hidden");

    document.getElementById(section + "Section").classList.remove("hidden");

    // 更新選單按鈕的 active 樣式
    document.getElementById("menu-accounts").classList.remove("active");
    document.getElementById("menu-roles").classList.remove("active");
    document.getElementById("menu-" + section).classList.add("active");
}

function loadRoles() {
    let roleList = document.getElementById("roleList");
    roleList.innerHTML = "";

    let roles = ["Admin", "User", "Guest"];

    roles.forEach((role, index) => {
        let li = document.createElement("li");
        li.innerHTML = `${role}
                    <button onclick="editRole(${index})">編輯</button>
                    <button onclick="deleteRole(${index})">刪除</button>`;
        roleList.appendChild(li);
    });
}

function addRole() {
    let role = prompt("請輸入新角色名稱:");
    if (role) {
        alert(`新增角色: ${role}`);
        loadRoles();
    }
}

function editRole(index) {
    let role = prompt("請輸入新角色名稱:");
    if (role) {
        alert(`角色 ${index} 修改為: ${role}`);
        loadRoles();
    }
}

function deleteRole(index) {
    if (confirm(`確定刪除角色 ${index} 嗎?`)) {
        alert(`角色 ${index} 已刪除`);
        loadRoles();
    }
}

async function logout() {
    localStorage.removeItem("token"); // 清除 Token

    const response = await getData("api/auth/v1/logout");
    console.log('logout: ',response)
    window.location.href = "login.html";
}