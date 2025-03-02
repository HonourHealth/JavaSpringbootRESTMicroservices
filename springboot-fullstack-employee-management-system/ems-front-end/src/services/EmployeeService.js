import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/api/employees";

export const listEmployees = async () => {
    try {
        const response = await axios.get(REST_API_BASE_URL);
        return response;
    } catch (error) {
        return error;
    }
};

export const getEmployee = async (employeeId) => {
    try {
        const response = await axios.get(`${REST_API_BASE_URL}/${employeeId}`);
        return response;
    } catch (error) {
        return error;
    }
};

export const createEmployee = async (employee) => {
    try {
        const response = await axios.post(REST_API_BASE_URL, employee);
        return response;
    } catch (error) {
        return error;
    }
};

export const updateEmployee = async (employeeId, employee) => {
    try {
        const response = await axios.put(`${REST_API_BASE_URL}/${employeeId}`, employee);
        return response;
    } catch (error) {
        return error;
    }
};

export const deleteEmployee = async (id) => {
    try {
        const response = await axios.delete(`${REST_API_BASE_URL}/${id}`);
        return response;
    } catch (error) {
        return error;
    }
};