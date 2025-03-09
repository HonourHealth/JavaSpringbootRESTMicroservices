import { beforeEach, describe, expect, jest, test } from "@jest/globals";
import axios from "axios";
import * as EmployeeService from "../../src/services/EmployeeService";

jest.mock("axios");

describe("EmployeeService", () => {
    const REST_API_BASE_URL = "http://localhost:8080/api/employees";
    const mockEmployee = {
        id: 1,
        firstName: "firstName",
        lastName: "lastName",
        email: "email@email.com",
    };
    const mockEmployees = [mockEmployee];

    beforeEach(() => {
        jest.clearAllMocks();
    });

    describe("listEmployees", () => {
        test("should return employees when API call is successful", async () => {
            const mockResponse = { data: mockEmployees };
            axios.get.mockResolvedValueOnce(mockResponse);

            const result = await EmployeeService.listEmployees();

            expect(axios.get).toHaveBeenCalledWith(REST_API_BASE_URL);
            expect(result).toEqual(mockResponse);
        });

        test("should return error when API call fails", async () => {
            const mockError = new Error("Network error");
            axios.get.mockRejectedValueOnce(mockError);

            const result = await EmployeeService.listEmployees();

            expect(axios.get).toHaveBeenCalledWith(REST_API_BASE_URL);
            expect(result).toEqual(mockError);
        });
    });

    describe("getEmployee", () => {
        test("should return employee when API call is successful", async () => {
            const employeeId = 1;
            const mockResponse = { data: mockEmployee };
            axios.get.mockResolvedValueOnce(mockResponse);

            const result = await EmployeeService.getEmployee(employeeId);

            expect(axios.get).toHaveBeenCalledWith(
                `${REST_API_BASE_URL}/${employeeId}`
            );
            expect(result).toEqual(mockResponse);
        });

        test("should return error when API call fails", async () => {
            const employeeId = 1;
            const mockError = new Error("Network error");
            axios.get.mockRejectedValueOnce(mockError);

            const result = await EmployeeService.getEmployee(employeeId);

            expect(axios.get).toHaveBeenCalledWith(
                `${REST_API_BASE_URL}/${employeeId}`
            );
            expect(result).toEqual(mockError);
        });
    });

    describe("createEmployee", () => {
        test("should create employee when API call is successful", async () => {
            const newEmployee = {
                firstName: "firstName2",
                lastName: "lastName2",
                email: "email2@email.com",
            };
            const mockResponse = { data: { ...newEmployee, id: 2 } };
            axios.post.mockResolvedValueOnce(mockResponse);

            const result = await EmployeeService.createEmployee(newEmployee);

            expect(axios.post).toHaveBeenCalledWith(
                REST_API_BASE_URL,
                newEmployee
            );
            expect(result).toEqual(mockResponse);
        });

        test("should return error when API call fails", async () => {
            const newEmployee = {
                firstName: "firstName2",
                lastName: "lastName2",
                email: "email2@email.com",
            };
            const mockError = new Error("Network error");
            axios.post.mockRejectedValueOnce(mockError);

            const result = await EmployeeService.createEmployee(newEmployee);

            expect(axios.post).toHaveBeenCalledWith(
                REST_API_BASE_URL,
                newEmployee
            );
            expect(result).toEqual(mockError);
        });
    });

    describe("updateEmployee", () => {
        test("should update employee when API call is successful", async () => {
            const employeeId = 1;
            const updatedEmployee = {
                firstName: "firstName",
                lastName: "Updated",
                email: "email.updated@example.com",
            };
            const mockResponse = {
                data: { ...updatedEmployee, id: employeeId },
            };
            axios.put.mockResolvedValueOnce(mockResponse);

            const result = await EmployeeService.updateEmployee(
                employeeId,
                updatedEmployee
            );

            expect(axios.put).toHaveBeenCalledWith(
                `${REST_API_BASE_URL}/${employeeId}`,
                updatedEmployee
            );
            expect(result).toEqual(mockResponse);
        });

        test("should return error when API call fails", async () => {
            const employeeId = 1;
            const updatedEmployee = {
                firstName: "firstName",
                lastName: "Updated",
                email: "email.updated@example.com",
            };
            const mockError = new Error("Network error");
            axios.put.mockRejectedValueOnce(mockError);

            const result = await EmployeeService.updateEmployee(
                employeeId,
                updatedEmployee
            );

            expect(axios.put).toHaveBeenCalledWith(
                `${REST_API_BASE_URL}/${employeeId}`,
                updatedEmployee
            );
            expect(result).toEqual(mockError);
        });
    });

    describe("deleteEmployee", () => {
        test("should delete employee when API call is successful", async () => {
            const employeeId = 1;
            const mockResponse = { data: "Employee deleted successfully" };
            axios.delete.mockResolvedValueOnce(mockResponse);

            const result = await EmployeeService.deleteEmployee(employeeId);

            expect(axios.delete).toHaveBeenCalledWith(
                `${REST_API_BASE_URL}/${employeeId}`
            );
            expect(result).toEqual(mockResponse);
        });

        test("should return error when API call fails", async () => {
            const employeeId = 1;
            const mockError = new Error("Network error");
            axios.delete.mockRejectedValueOnce(mockError);

            const result = await EmployeeService.deleteEmployee(employeeId);

            expect(axios.delete).toHaveBeenCalledWith(
                `${REST_API_BASE_URL}/${employeeId}`
            );
            expect(result).toEqual(mockError);
        });
    });
});
