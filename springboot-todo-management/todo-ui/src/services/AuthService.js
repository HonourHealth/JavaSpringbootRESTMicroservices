import axios from "axios";

const AUTH_REST_API_URL = "http://localhost:8080/api/auth";

export const registerAPICall = (registerObj) => {
    return axios.post(`${AUTH_REST_API_URL}/register`, registerObj);
};
