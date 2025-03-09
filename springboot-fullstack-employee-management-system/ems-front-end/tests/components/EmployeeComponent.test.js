import { beforeEach, describe, expect, jest, test } from "@jest/globals";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes, useNavigate } from "react-router-dom";
import EmployeeComponent from "../../src/components/EmployeeComponent.jsx";
import * as EmployeeService from "../../src/services/EmployeeService";

jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    useNavigate: jest.fn(),
}));

jest.mock("../../src/services/EmployeeService", () => ({
    getEmployee: jest.fn(),
    createEmployee: jest.fn(),
    updateEmployee: jest.fn(),
}));

describe("EmployeeComponent", () => {
    const mockNavigate = jest.fn();

    beforeEach(() => {
        jest.clearAllMocks();
        useNavigate.mockImplementation(() => mockNavigate);
    });

    test("renders component in create mode and calls createEmployee on submit", async () => {
        EmployeeService.createEmployee.mockResolvedValue({ data: { id: 123 } });

        render(
            <MemoryRouter initialEntries={["/add-employee"]}>
                <Routes>
                    <Route
                        path="/add-employee"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        expect(screen.getByText("Add Employee")).toBeInTheDocument();

        const firstNameInput = screen.getByPlaceholderText(
            /Enter Employee First Name/i
        );
        const lastNameInput = screen.getByPlaceholderText(
            /Enter Employee Last Name/i
        );
        const emailInput = screen.getByPlaceholderText(/Enter Email Address/i);

        fireEvent.change(firstNameInput, { target: { value: "firstName1" } });
        fireEvent.change(lastNameInput, { target: { value: "lastName1" } });
        fireEvent.change(emailInput, { target: { value: "email1@email.com" } });

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(EmployeeService.createEmployee).toHaveBeenCalledWith({
                firstName: "firstName1",
                lastName: "lastName1",
                email: "email1@email.com",
            });
        });

        expect(mockNavigate).toHaveBeenCalledWith("/employees");
    });

    test("renders component in edit mode and calls getEmployee + updateEmployee on submit", async () => {
        EmployeeService.getEmployee.mockResolvedValue({
            data: {
                id: 456,
                firstName: "firstName2",
                lastName: "lastName2",
                email: "email2@email.com",
            },
        });
        EmployeeService.updateEmployee.mockResolvedValue({});

        render(
            <MemoryRouter initialEntries={["/edit-employee/456"]}>
                <Routes>
                    <Route
                        path="/edit-employee/:id"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            const firstNameInput = screen.getByPlaceholderText(
                /Enter Employee First Name/i
            );
            const lastNameInput = screen.getByPlaceholderText(
                /Enter Employee Last Name/i
            );
            const emailInput =
                screen.getByPlaceholderText(/Enter Email Address/i);

            expect(firstNameInput).toHaveValue("firstName2");
            expect(lastNameInput).toHaveValue("lastName2");
            expect(emailInput).toHaveValue("email2@email.com");
            expect(screen.getByText("Update Employee")).toBeInTheDocument();
            expect(EmployeeService.getEmployee).toHaveBeenCalledWith("456");
        });

        fireEvent.change(
            screen.getByPlaceholderText(/Enter Employee First Name/i),
            {
                target: { value: "firstName3" },
            }
        );
        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(EmployeeService.updateEmployee).toHaveBeenCalledWith("456", {
                firstName: "firstName3",
                lastName: "lastName2",
                email: "email2@email.com",
            });
        });

        expect(mockNavigate).toHaveBeenCalledWith("/employees");
    });

    test("validates form fields before submission", async () => {
        render(
            <MemoryRouter initialEntries={["/add-employee"]}>
                <Routes>
                    <Route
                        path="/add-employee"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(
                screen.getByText("First name is required")
            ).toBeInTheDocument();
            expect(
                screen.getByText("Last name is required")
            ).toBeInTheDocument();
            expect(screen.getByText("Email is required")).toBeInTheDocument();
        });

        expect(EmployeeService.createEmployee).not.toHaveBeenCalled();
    });

    test("handles error when fetching employee data", async () => {
        EmployeeService.getEmployee.mockRejectedValue(
            new Error("Failed to fetch employee")
        );

        jest.spyOn(console, "error").mockImplementation(() => {});

        render(
            <MemoryRouter initialEntries={["/edit-employee/456"]}>
                <Routes>
                    <Route
                        path="/edit-employee/:id"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(EmployeeService.getEmployee).toHaveBeenCalledWith("456");
        });

        await waitFor(() => {
            expect(console.error).toHaveBeenCalled();
        });

        console.error.mockRestore();
    });

    test("handles error when creating employee", async () => {
        EmployeeService.createEmployee.mockRejectedValue(
            new Error("Failed to create employee")
        );

        jest.spyOn(console, "error").mockImplementation(() => {});

        render(
            <MemoryRouter initialEntries={["/add-employee"]}>
                <Routes>
                    <Route
                        path="/add-employee"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        const firstNameInput = screen.getByPlaceholderText(
            /Enter Employee First Name/i
        );
        const lastNameInput = screen.getByPlaceholderText(
            /Enter Employee Last Name/i
        );
        const emailInput = screen.getByPlaceholderText(/Enter Email Address/i);

        fireEvent.change(firstNameInput, { target: { value: "firstName1" } });
        fireEvent.change(lastNameInput, { target: { value: "lastName1" } });
        fireEvent.change(emailInput, { target: { value: "email1@email.com" } });

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(EmployeeService.createEmployee).toHaveBeenCalled();
            expect(console.error).toHaveBeenCalled();
        });

        expect(mockNavigate).not.toHaveBeenCalled();

        console.error.mockRestore();
    });

    test("handles error when updating employee", async () => {
        EmployeeService.getEmployee.mockResolvedValue({
            data: {
                id: 456,
                firstName: "firstName2",
                lastName: "lastName2",
                email: "email2@email.com",
            },
        });
        EmployeeService.updateEmployee.mockRejectedValue(
            new Error("Failed to update employee")
        );

        jest.spyOn(console, "error").mockImplementation(() => {});

        render(
            <MemoryRouter initialEntries={["/edit-employee/456"]}>
                <Routes>
                    <Route
                        path="/edit-employee/:id"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            const firstNameInput = screen.getByPlaceholderText(
                /Enter Employee First Name/i
            );
            expect(firstNameInput).toHaveValue("firstName2");
        });

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(EmployeeService.updateEmployee).toHaveBeenCalled();
            expect(console.error).toHaveBeenCalled();
        });

        expect(mockNavigate).not.toHaveBeenCalled();

        console.error.mockRestore();
    });

    test("navigates back to employees list when Back button is clicked", () => {
        render(
            <MemoryRouter initialEntries={["/add-employee"]}>
                <Routes>
                    <Route
                        path="/add-employee"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        const backButton = screen.getByText("Back");

        const mockEvent = { preventDefault: jest.fn() };

        fireEvent.click(backButton, mockEvent);

        expect(mockNavigate).toHaveBeenCalledWith("/employees");
    });

    test("validates email format", () => {
        render(
            <MemoryRouter initialEntries={["/add-employee"]}>
                <Routes>
                    <Route
                        path="/add-employee"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        const firstNameInput = screen.getByPlaceholderText(
            /Enter Employee First Name/i
        );
        const lastNameInput = screen.getByPlaceholderText(
            /Enter Employee Last Name/i
        );
        const emailInput = screen.getByPlaceholderText(/Enter Email Address/i);

        fireEvent.change(firstNameInput, { target: { value: "John" } });
        fireEvent.change(lastNameInput, { target: { value: "Doe" } });
        fireEvent.change(emailInput, { target: { value: "" } });

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        expect(screen.getByText("Email is required")).toBeInTheDocument();
    });

    test("updates state when form inputs change", () => {
        render(
            <MemoryRouter initialEntries={["/add-employee"]}>
                <Routes>
                    <Route
                        path="/add-employee"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        const firstNameInput = screen.getByPlaceholderText(
            /Enter Employee First Name/i
        );
        const lastNameInput = screen.getByPlaceholderText(
            /Enter Employee Last Name/i
        );
        const emailInput = screen.getByPlaceholderText(/Enter Email Address/i);

        fireEvent.change(firstNameInput, { target: { value: "John" } });
        fireEvent.change(lastNameInput, { target: { value: "Doe" } });
        fireEvent.change(emailInput, { target: { value: "john@example.com" } });

        expect(firstNameInput).toHaveValue("John");
        expect(lastNameInput).toHaveValue("Doe");
        expect(emailInput).toHaveValue("john@example.com");
    });

    test("displays correct page title based on mode", () => {
        const { unmount } = render(
            <MemoryRouter initialEntries={["/add-employee"]}>
                <Routes>
                    <Route
                        path="/add-employee"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        expect(screen.getByText("Add Employee")).toBeInTheDocument();

        unmount();

        render(
            <MemoryRouter initialEntries={["/edit-employee/1"]}>
                <Routes>
                    <Route
                        path="/edit-employee/:id"
                        element={<EmployeeComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        expect(screen.getByText("Update Employee")).toBeInTheDocument();
    });
});
