import { beforeEach, describe, expect, jest, test } from "@jest/globals";
import axios from "axios";

import * as DepartmentService from "../../src/services/DepartmentService";

jest.mock("axios");

describe("DepartmentService", () => {
    const DEPARTMENT_REST_API_BASE_URL =
        "http://localhost:8080/api/departments";
    beforeEach(() => {
        jest.clearAllMocks();
    });
    const mockDepartment = {
        id: 1,
        departmentName: "departmentName1",
        departmentDescription: "departmentDescription1",
    };
    const mockDepartments = [mockDepartment];

    describe("getAllDepartments", () => {
        test("should return departments when API call is successful", async () => {
            const mockResponse = { data: mockDepartments };
            axios.get.mockResolvedValueOnce(mockResponse);

            const result = await DepartmentService.getAllDepartments();

            expect(axios.get).toHaveBeenCalledWith(
                DEPARTMENT_REST_API_BASE_URL
            );
            expect(result).toEqual(mockResponse);
        });

        test("should throw error when API call fails", async () => {
            const mockError = new Error("Network error");
            axios.get.mockRejectedValueOnce(mockError);

            await expect(DepartmentService.getAllDepartments()).rejects.toEqual(
                mockError
            );

            expect(axios.get).toHaveBeenCalledWith(
                DEPARTMENT_REST_API_BASE_URL
            );
        });
    });

    describe("createDepartment", () => {
        test("should create department when API call is successful", async () => {
            const newDepartment = {
                departmentName: "New Department",
                departmentDescription: "New Description",
            };
            const mockResponse = {
                data: {
                    ...newDepartment,
                    id: 2,
                },
            };
            axios.post.mockResolvedValueOnce(mockResponse);

            const result = await DepartmentService.createDepartment(
                newDepartment
            );

            expect(axios.post).toHaveBeenCalledWith(
                DEPARTMENT_REST_API_BASE_URL,
                newDepartment
            );
            expect(result).toEqual(mockResponse);
        });

        test("should throw error when create department API call fails", async () => {
            const newDepartment = {
                departmentName: "New Department",
                departmentDescription: "New Description",
            };
            const mockError = new Error("Failed to create department");
            axios.post.mockRejectedValueOnce(mockError);

            await expect(
                DepartmentService.createDepartment(newDepartment)
            ).rejects.toEqual(mockError);

            expect(axios.post).toHaveBeenCalledWith(
                DEPARTMENT_REST_API_BASE_URL,
                newDepartment
            );
        });
    });

    describe("getDepartmentById", () => {
        test("should return department when API call is successful", async () => {
            const departmentId = 1;
            const mockResponse = { data: mockDepartment };
            axios.get.mockResolvedValueOnce(mockResponse);

            const result = await DepartmentService.getDepartmentById(
                departmentId
            );

            expect(axios.get).toHaveBeenCalledWith(
                `${DEPARTMENT_REST_API_BASE_URL}/${departmentId}`
            );
            expect(result).toEqual(mockResponse);
        });

        test("should throw error when get department by id API call fails", async () => {
            const departmentId = 999;
            const mockError = new Error("Department not found");
            axios.get.mockRejectedValueOnce(mockError);

            await expect(
                DepartmentService.getDepartmentById(departmentId)
            ).rejects.toEqual(mockError);

            expect(axios.get).toHaveBeenCalledWith(
                `${DEPARTMENT_REST_API_BASE_URL}/${departmentId}`
            );
        });
    });

    describe("updateDepartment", () => {
        test("should update department when API call is successful", async () => {
            const departmentId = 1;
            const updatedDepartment = {
                ...mockDepartment,
                departmentName: "Updated Department Name",
            };
            const mockResponse = { data: updatedDepartment };
            axios.put.mockResolvedValueOnce(mockResponse);

            const result = await DepartmentService.updateDepartment(
                departmentId,
                updatedDepartment
            );

            expect(axios.put).toHaveBeenCalledWith(
                `${DEPARTMENT_REST_API_BASE_URL}/${departmentId}`,
                updatedDepartment
            );
            expect(result).toEqual(mockResponse);
        });

        test("should throw error when update department API call fails", async () => {
            const departmentId = 1;
            const updatedDepartment = {
                ...mockDepartment,
                departmentName: "Updated Department Name",
            };
            const mockError = new Error("Failed to update department");
            axios.put.mockRejectedValueOnce(mockError);

            await expect(
                DepartmentService.updateDepartment(
                    departmentId,
                    updatedDepartment
                )
            ).rejects.toEqual(mockError);

            expect(axios.put).toHaveBeenCalledWith(
                `${DEPARTMENT_REST_API_BASE_URL}/${departmentId}`,
                updatedDepartment
            );
        });
    });

    describe("deleteDepartment", () => {
        test("should delete department when API call is successful", async () => {
            const departmentId = 1;
            const mockResponse = { data: "Department deleted successfully" };
            axios.delete.mockResolvedValueOnce(mockResponse);

            const result = await DepartmentService.deleteDepartment(
                departmentId
            );

            expect(axios.delete).toHaveBeenCalledWith(
                `${DEPARTMENT_REST_API_BASE_URL}/${departmentId}`
            );
            expect(result).toEqual(mockResponse);
        });

        test("should throw error when delete department API call fails", async () => {
            const departmentId = 1;
            const mockError = new Error("Failed to delete department");
            axios.delete.mockRejectedValueOnce(mockError);

            await expect(
                DepartmentService.deleteDepartment(departmentId)
            ).rejects.toEqual(mockError);

            expect(axios.delete).toHaveBeenCalledWith(
                `${DEPARTMENT_REST_API_BASE_URL}/${departmentId}`
            );
        });
    });
});
