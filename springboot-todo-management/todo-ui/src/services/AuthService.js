import axios from "axios";

const AUTH_REST_API_URL = "http://localhost:8080/api/auth";

export const registerAPICall = (registerObj) => {
    return axios.post(`${AUTH_REST_API_URL}/register`, registerObj);
};

export const loginAPICall = (usernameOrEmail, password) =>
    axios.post(`${AUTH_REST_API_URL}/login`, {
        usernameOrEmail,
        password,
    });

export const storeToken = (token) => {
    localStorage.setItem("token", token);
};

export const getToken = () => {
    const token = localStorage.getItem("token");
    return token;
};

export const saveLoggedInUser = (username) =>
    sessionStorage.setItem("authenticatedUser", username);

export const isUserLoggedIn = () => {
    const user = sessionStorage.getItem("authenticatedUser");
    return user !== null;
};

export const getLoggedInUser = () => {
    const user = sessionStorage.getItem("authenticatedUser");
    return user;
};
