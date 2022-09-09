import axios from "axios"

export function login(loginObj) {
    return axios({
        method : "POST", 
        url : process.env.REACT_APP_API_HOST + "/api/v1/account/login",
        data : loginObj
    }).catch((err) => {
        console.log(err);
    }).then(response => {
        if (response.data.status == 100) {
            localStorage.setItem("token", (response.data.data));
        }
        return response.data;
    });
}

export function logout() {
    localStorage.removeItem("token");
}

export function authHeader() {
    const token = localStorage.getItem('token');
    if (token) {
        return { Authorization: 'Bearer ' + token };
    } else {
        return null;
    }
}

export function getCurrentInfo() {
    return JSON.parse(localStorage.getItem('user'));
}


export function getMyInfo() {
    return axios({
        method : "GET", 
        url : process.env.REACT_APP_API_HOST + "/api/v1/account/me",
        headers : authHeader()
    }).catch((err) => {
        console.log(err);
    }).then(response => {
        if (response.data.username) {
            localStorage.setItem("user", JSON.stringify(response.data));
            return response.data;
        }
    });
}