import React from "react";
import { render, screen } from "@testing-library/react";
import { act } from "react";
import "@testing-library/jest-dom";
import EmployeeComponent from "./EmployeeComponent";
import EmployeeService from "../service/EmployeeService";

jest.mock("../service/EmployeeService", () => ({
    getEmployee: jest.fn(),
}));

describe("EmployeeComponent", () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    test("renders employee details after API call", async () => {
        const mockEmployee = {
            data: {
                employeeDto: {
                    firstName: "John",
                    lastName: "Doe",
                    email: "john@example.com",
                },
                departmentDto: {
                    departmentName: "IT",
                    departmentDescription: "Information Technology",
                    departmentCode: "IT001",
                },
                organizationDto: {
                    organizationName: "Tech Corp",
                    organizationDescription: "Technology Company",
                    organizationCode: "TECH001",
                    createdDate: "2024-02-20",
                },
            },
        };

        EmployeeService.getEmployee.mockResolvedValueOnce(mockEmployee);

        await act(async () => {
            render(<EmployeeComponent />);
        });

        await new Promise((resolve) => setTimeout(resolve, 0));

        expect(screen.getByText("John")).toBeInTheDocument();
        expect(screen.getByText("Doe")).toBeInTheDocument();
        expect(screen.getByText("john@example.com")).toBeInTheDocument();

        expect(
            screen.getByText((content, element) => {
                return (
                    element.tagName.toLowerCase() === "p" &&
                    element.textContent.includes("Department Name:") &&
                    element.textContent.includes("IT")
                );
            })
        ).toBeInTheDocument();

        expect(screen.getByText("Information Technology")).toBeInTheDocument();
        expect(screen.getByText("IT001")).toBeInTheDocument();

        expect(screen.getByText("Tech Corp")).toBeInTheDocument();
        expect(screen.getByText("Technology Company")).toBeInTheDocument();
        expect(screen.getByText("TECH001")).toBeInTheDocument();
        expect(screen.getByText("2024-02-20")).toBeInTheDocument();

        expect(EmployeeService.getEmployee).toHaveBeenCalled();
    });
});
