const tokenKey = "token";
const baseUrl = "http://localhost:8082/star/"

//localStorage 寫入token
export const setToken = (token) => {
    localStorage.setItem(tokenKey,token);
}
//api請求
export const fetchUtil = async (url, { params = {}, headers = {}, body = null, method = "GET" } = {}) => {
    try {
        // **處理 URL 查詢參數**
        const queryString = new URLSearchParams(params).toString();
        const fullUrl = queryString ? `${url}?${queryString}` : url;

        console.log("Request URL:", fullUrl);


        // **發送請求**
        const response = await fetch(`${baseUrl}${fullUrl}`, {
            method,
            headers: {
                "Content-Type": "application/json",
                ...headers,
            },
            body: body ? JSON.stringify(body) : null,
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error("Fetch error:", error);
        throw error;
    }
};

// 封裝 GET 請求 (支援查詢參數)
export const getData = async (url, params = {}, headers = {}) => {
    return await fetchUtil(url, { params, headers, method: "GET" });
};

// 封裝 POST 請求 (支援 body)
export const postData = async (url, body = {}, headers = {}) => {
    return await fetchUtil(url, { body, headers, method: "POST" });
};

const getAuthHeaders = () => {
    const token = localStorage.getItem("token");
    return token ? { Authorization: `Bearer ${token}` } : {};
};

// **GET（自動帶 Token）**
export const getData_t = async (url, params = {}) => {
    return await fetchUtil(url, { params, headers: getAuthHeaders(), method: "GET" });
};

// **POST（自動帶 Token）**
export const postData_t = async (url, body = {}) => {
    return await fetchUtil(url,{ body, headers: getAuthHeaders(), method: "POST" });
};
