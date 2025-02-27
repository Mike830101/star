import {getData_t, postData_t} from "./api.js";

let accList = {}; //帳號暫存清單

init();
function init(){
    const modal = document.getElementById("accountModal");
    const openBtn = document.getElementById("addAccountBtn");
    const closeBtn = document.querySelector(".close");

    openBtn.addEventListener("click", () => {
        modal.style.display = "flex"; // 顯示模態視窗
    });

    closeBtn.addEventListener("click", () => {
        modal.style.display = "none"; // 隱藏模態視窗
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none"; // 點擊背景關閉
        }
    });

    document.getElementById("submitAccBtn").addEventListener("click", () => {
        let acc = document.getElementById("newAcc").value;
        let name = document.getElementById("newName").value;
        console.log("新增帳號:", acc, "姓名:", name);
        modal.style.display = "none";
    });
}

class AccountTable {
    constructor(containerId, titles) {
        this.container = document.getElementById(containerId);
        this.titles = titles;
        this.table = document.createElement("table");
        this.table.border = "1";
        this.initTable();
    }

    initTable() {
        // 清空容器，初始化表格結構
        this.container.innerHTML = "";
        this.container.appendChild(this.table);

        // 生成表頭
        const thead = document.createElement("thead");
        const headerRow = document.createElement("tr");

        this.titles.forEach(title => {
            let th = document.createElement("th");
            th.textContent = title;
            headerRow.appendChild(th);
        });

        let actionTh = document.createElement("th");
        actionTh.textContent = "操作";
        headerRow.appendChild(actionTh);

        thead.appendChild(headerRow);
        this.table.appendChild(thead);

        // 建立 tbody
        this.tbody = document.createElement("tbody");
        this.table.appendChild(this.tbody);
    }

    loadData(accounts) {
        this.tbody.innerHTML = ""; // 清空舊資料
        accList = accounts; //暫存

        accounts.forEach(acc => {
            let row = document.createElement("tr");

            // 根據 `title` 動態填入資料
            this.titles.forEach(title => {
                let td = document.createElement("td");
                td.textContent = acc[title] ?? ""; // 防止 undefined
                row.appendChild(td);
            });

            // 創建編輯按鈕
            let editButton = document.createElement("button");
            editButton.classList.add("edit-acc-btn");
            editButton.textContent = "編輯";
            editButton.addEventListener("click",() => editAccount(acc.uid));

            // 創建刪除按鈕
            let deleteButton = document.createElement("button");
            deleteButton.classList.add("delete-acc-btn");
            deleteButton.textContent = "刪除";
            deleteButton.addEventListener("click",() => deleteAccount(acc.uid))

            // 將按鈕添加到操作欄位
            row.appendChild(editButton);
            row.appendChild(deleteButton);

            this.tbody.appendChild(row);
        });
    }
}

// **初始化表格**
const accountTable = new AccountTable("accountList", ["account", "name", "roleName", "status", "uid"]);

// **載入帳號資料**
async function loadAccounts() {
    const response = await postData_t("api/user/v1/list");
    console.log(response);

    if (response && response.data) {
        accountTable.loadData(response.data);
    } else {
        console.error("API 回應異常", response);
    }
}

function editAccount(id) {
    let name = prompt("請輸入新帳號名稱:");
    if (name) {
        alert(`帳號 ${id} 修改為: ${name}`);
        loadAccounts();
    }
}

async function deleteAccount(_id) {
    const data = accList.filter(acc => acc.uid === _id)[0];
    if (confirm(`確定刪除帳號 ${data.account} 嗎?`)) {
        const response = await getData_t("api/user/v1/remove", {uid: data.uid});
        if (response && response.data){
            alert(`帳號 ${data.account} 已刪除`);
            loadAccounts();
        }
    }
}

export {loadAccounts,editAccount,deleteAccount};