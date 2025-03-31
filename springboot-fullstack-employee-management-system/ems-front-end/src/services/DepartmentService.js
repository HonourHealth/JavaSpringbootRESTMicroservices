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
