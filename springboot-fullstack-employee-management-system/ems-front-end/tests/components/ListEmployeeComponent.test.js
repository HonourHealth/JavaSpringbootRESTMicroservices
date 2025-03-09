import { beforeEach, describe, expect, jest, test } from "@jest/globals";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, useNavigate } from "react-router-dom";
import ListEmployeeComponent from "../../src/components/ListEmployeeComponent";
import * as EmployeeService from "../../src/services/EmployeeService";

jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    useNavigate: jest.fn(),
}));

jest.mock("../../src/services/EmployeeService", () => ({
    listEmployees: jest.fn(),
    deleteEmployee: jest.fn(),
}));

describe("ListEmployeeComponent", () => {
    const mockNavigate = jest.fn();
    const mockEmployees = [
        {
            id: 1,
            firstName: "firstName1",
            lastName: "lastName1",
            email: "email1@email.com",
        },
        {
            id: 2,
            firstName: "firstName2",
            lastName: "lastName2",
            email: "email2@email.com",
        },
    ];

    beforeEach(() => {
        jest.clearAllMocks();
        useNavigate.mockImplementation(() => mockNavigate);
        EmployeeService.listEmployees.mockResolvedValue({
            data: mockEmployees,
        });
        EmployeeService.deleteEmployee.mockResolvedValue({});
    });

    test("renders list of employees correctly", async () => {
        render(
            <MemoryRouter>
                <ListEmployeeComponent />
            </MemoryRouter>
        );

        expect(screen.getByText("List of Employees")).toBeInTheDocument();
        expect(screen.getByText("Add Employee")).toBeInTheDocument();
        expect(screen.getByText("Employee Id")).toBeInTheDocument();
        expect(screen.getByText("Employee First Name")).toBeInTheDocument();
        expect(screen.getByText("Employee Last Name")).toBeInTheDocument();
        expect(screen.getByText("Employee Email Id")).toBeInTheDocument();
        expect(screen.getByText("Actions")).toBeInTheDocument();

        await waitFor(() => {
            expect(EmployeeService.listEmployees).toHaveBeenCalledTimes(1);
            expect(screen.getByText("firstName1")).toBeInTheDocument();
            expect(screen.getByText("lastName1")).toBeInTheDocument();
            expect(screen.getByText("email1@email.com")).toBeInTheDocument();
            expect(screen.getByText("firstName2")).toBeInTheDocument();
            expect(screen.getByText("lastName2")).toBeInTheDocument();
            expect(screen.getByText("email2@email.com")).toBeInTheDocument();
        });
    });

    test("navigates to add employee page when Add Employee button is clicked", async () => {
        render(
            <MemoryRouter>
                <ListEmployeeComponent />
            </MemoryRouter>
        );

        fireEvent.click(screen.getByText("Add Employee"));

        expect(mockNavigate).toHaveBeenCalledWith("/add-employee");
    });

    test("navigates to edit employee page when Update button is clicked", async () => {
        render(
            <MemoryRouter>
                <ListEmployeeComponent />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.getByText("firstName1")).toBeInTheDocument();
        });

        const updateButtons = screen.getAllByText("Update");
        fireEvent.click(updateButtons[0]);

        expect(mockNavigate).toHaveBeenCalledWith("/edit-employee/1");
    });

    test("deletes employee when Delete button is clicked", async () => {
        render(
            <MemoryRouter>
                <ListEmployeeComponent />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.getByText("firstName1")).toBeInTheDocument();
        });

        const deleteButtons = screen.getAllByText("Delete");
        fireEvent.click(deleteButtons[0]);

        expect(EmployeeService.deleteEmployee).toHaveBeenCalledWith(1);

        await waitFor(() => {
            expect(EmployeeService.listEmployees).toHaveBeenCalledTimes(2);
        });
    });

    test("handles error when fetching employees fails", async () => {
        const originalConsoleError = console.error;
        console.error = jest.fn();

        EmployeeService.listEmployees.mockRejectedValueOnce(
            new Error("Failed to fetch employees")
        );

        render(
            <MemoryRouter>
                <ListEmployeeComponent />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(EmployeeService.listEmployees).toHaveBeenCalledTimes(1);
            expect(console.error).toHaveBeenCalled();
        });

        console.error = originalConsoleError;
    });

    test("handles error when deleting employee fails", async () => {
        const originalConsoleError = console.error;
        console.error = jest.fn();

        EmployeeService.deleteEmployee.mockRejectedValueOnce(
            new Error("Failed to delete employee")
        );

        render(
            <MemoryRouter>
                <ListEmployeeComponent />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.getByText("firstName1")).toBeInTheDocument();
        });

        const deleteButtons = screen.getAllByText("Delete");
        fireEvent.click(deleteButtons[0]);

        expect(EmployeeService.deleteEmployee).toHaveBeenCalledWith(1);

        await waitFor(() => {
            expect(console.error).toHaveBeenCalledWith(
                "Error deleting employee:",
                expect.any(Error)
            );
        });

        console.error = originalConsoleError;
    });
});
