import axios from "axios";

const DEPARTMENT_REST_API_BASE_URL = "http://localhost:8080/api/departments";

export const getAllDepartments = async () => {
    try {
        const response = await axios.get(DEPARTMENT_REST_API_BASE_URL);
        return response;
    } catch (error) {
        console.error("Error fetching departments:", error);
        throw error;
    }
};

export const createDepartment = async (department) => {
    try {
        const response = await axios.post(
            DEPARTMENT_REST_API_BASE_URL,
            department
        );
        return response;
    } catch (error) {
        console.error("Error creating department:", error);
        throw error;
    }
};

export const getDepartmentById = async (departmentId) => {
    try {
        const response = await axios.get(
            `${DEPARTMENT_REST_API_BASE_URL}/${departmentId}`
        );
        return response;
    } catch (error) {
        console.error("Error fetching department:", error);
        throw error;
    }
};

export const updateDepartment = async (departmentId, department) => {
    try {
        const response = await axios.put(
            `${DEPARTMENT_REST_API_BASE_URL}/${departmentId}`,
            department
        );
        return response;
    } catch (error) {
        console.error("Error updating department:", error);
        throw error;
    }
};

export const deleteDepartment = async (departmentId) => {
    try {
        const response = await axios.delete(
            `${DEPARTMENT_REST_API_BASE_URL}/${departmentId}`
        );
        return response;
    } catch (error) {
        console.error("Error deleting department:", error);
        throw error;
    }
};