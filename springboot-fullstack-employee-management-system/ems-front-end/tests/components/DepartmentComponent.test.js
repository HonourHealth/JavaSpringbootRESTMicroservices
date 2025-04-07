import { beforeEach, describe, expect, jest, test } from "@jest/globals";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes, useNavigate } from "react-router-dom";
import DepartmentComponent from "../../src/components/DepartmentComponent.jsx";
import * as DepartmentService from "../../src/services/DepartmentService";

jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    useNavigate: jest.fn(),
    useParams: () => ({
        id: undefined,
    }),
}));

jest.mock("../../src/services/DepartmentService", () => ({
    getDepartmentById: jest.fn().mockResolvedValue({ data: {} }),
    createDepartment: jest.fn(),
    updateDepartment: jest.fn(),
}));

describe("DepartmentComponent", () => {
    const mockNavigate = jest.fn();

    beforeEach(() => {
        jest.clearAllMocks();
        useNavigate.mockImplementation(() => mockNavigate);

        jest.spyOn(console, "error").mockImplementation(() => {});
    });

    afterEach(() => {
        console.error.mockRestore();
    });

    test("renders component in create mode and calls createDepartment on submit", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: undefined,
        });

        DepartmentService.createDepartment.mockResolvedValue({
            data: { id: 123 },
        });

        render(
            <MemoryRouter initialEntries={["/add-department"]}>
                <Routes>
                    <Route
                        path="/add-department"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        expect(screen.getByText("Add Department")).toBeInTheDocument();

        const nameInput = screen.getByLabelText(/Department Name/i);
        const descriptionInput = screen.getByLabelText(
            /Department Description/i
        );

        fireEvent.change(nameInput, { target: { value: "Finance" } });
        fireEvent.change(descriptionInput, {
            target: { value: "Finance Department" },
        });

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(DepartmentService.createDepartment).toHaveBeenCalledWith({
                departmentName: "Finance",
                departmentDescription: "Finance Department",
            });
        });

        expect(mockNavigate).toHaveBeenCalledWith("/departments");
    });

    test("renders component in edit mode and calls getDepartmentById + updateDepartment", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: "456",
        });

        DepartmentService.getDepartmentById.mockResolvedValue({
            data: {
                id: 456,
                departmentName: "HR",
                departmentDescription: "Human Resources Department",
            },
        });
        DepartmentService.updateDepartment.mockResolvedValue({});

        render(
            <MemoryRouter initialEntries={["/edit-department/456"]}>
                <Routes>
                    <Route
                        path="/edit-department/:id"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(DepartmentService.getDepartmentById).toHaveBeenCalledWith(
                "456"
            );
        });

        await waitFor(() => {
            const nameInput = screen.getByLabelText(/Department Name/i);
            const descriptionInput = screen.getByLabelText(
                /Department Description/i
            );

            expect(nameInput).toHaveValue("HR");
            expect(descriptionInput).toHaveValue("Human Resources Department");
            expect(screen.getByText("Update Department")).toBeInTheDocument();
        });

        fireEvent.change(screen.getByLabelText(/Department Name/i), {
            target: { value: "Human Resources" },
        });

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(DepartmentService.updateDepartment).toHaveBeenCalledWith(
                "456",
                {
                    departmentName: "Human Resources",
                    departmentDescription: "Human Resources Department",
                }
            );
        });

        expect(mockNavigate).toHaveBeenCalledWith("/departments");
    });

    test("handles error when fetching department data", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: "456",
        });

        DepartmentService.getDepartmentById.mockRejectedValue(
            new Error("Failed to fetch department")
        );

        render(
            <MemoryRouter initialEntries={["/edit-department/456"]}>
                <Routes>
                    <Route
                        path="/edit-department/:id"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(DepartmentService.getDepartmentById).toHaveBeenCalledWith(
                "456"
            );
            expect(console.error).toHaveBeenCalledWith(
                "Error fetching department:",
                expect.any(Error)
            );
        });

        expect(screen.getByText("Update Department")).toBeInTheDocument();
    });

    test("handles error when creating department", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: undefined,
        });

        DepartmentService.createDepartment.mockRejectedValue(
            new Error("Failed to create department")
        );

        render(
            <MemoryRouter initialEntries={["/add-department"]}>
                <Routes>
                    <Route
                        path="/add-department"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        const nameInput = screen.getByLabelText(/Department Name/i);
        const descriptionInput = screen.getByLabelText(
            /Department Description/i
        );

        fireEvent.change(nameInput, { target: { value: "Finance" } });
        fireEvent.change(descriptionInput, {
            target: { value: "Finance Department" },
        });

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(DepartmentService.createDepartment).toHaveBeenCalled();
            expect(console.error).toHaveBeenCalledWith(
                "Error creating department:",
                expect.any(Error)
            );
        });

        expect(mockNavigate).not.toHaveBeenCalled();
    });

    test("handles error when updating department", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: "456",
        });

        DepartmentService.getDepartmentById.mockResolvedValue({
            data: {
                id: 456,
                departmentName: "HR",
                departmentDescription: "Human Resources Department",
            },
        });
        DepartmentService.updateDepartment.mockRejectedValue(
            new Error("Failed to update department")
        );

        render(
            <MemoryRouter initialEntries={["/edit-department/456"]}>
                <Routes>
                    <Route
                        path="/edit-department/:id"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            const nameInput = screen.getByLabelText(/Department Name/i);
            expect(nameInput).toHaveValue("HR");
        });

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(DepartmentService.updateDepartment).toHaveBeenCalled();
            expect(console.error).toHaveBeenCalledWith(
                "Error updating department:",
                expect.any(Error)
            );
        });

        expect(mockNavigate).not.toHaveBeenCalled();
    });

    test("updates state when form inputs change", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: undefined,
        });

        render(
            <MemoryRouter initialEntries={["/add-department"]}>
                <Routes>
                    <Route
                        path="/add-department"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        const nameInput = screen.getByLabelText(/Department Name/i);
        const descriptionInput = screen.getByLabelText(
            /Department Description/i
        );

        fireEvent.change(nameInput, { target: { value: "Finance" } });
        fireEvent.change(descriptionInput, {
            target: { value: "Finance Department" },
        });

        expect(nameInput).toHaveValue("Finance");
        expect(descriptionInput).toHaveValue("Finance Department");
    });

    test("displays correct page title based on mode", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: undefined,
        });

        const { unmount } = render(
            <MemoryRouter initialEntries={["/add-department"]}>
                <Routes>
                    <Route
                        path="/add-department"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        expect(screen.getByText("Add Department")).toBeInTheDocument();

        unmount();

        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: "1",
        });

        DepartmentService.getDepartmentById.mockResolvedValue({
            data: {
                id: 1,
                departmentName: "HR",
                departmentDescription: "Human Resources Department",
            },
        });

        render(
            <MemoryRouter initialEntries={["/edit-department/1"]}>
                <Routes>
                    <Route
                        path="/edit-department/:id"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.getByText("Update Department")).toBeInTheDocument();
        });
    });

    test("form validation - empty fields", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: undefined,
        });

        render(
            <MemoryRouter initialEntries={["/add-department"]}>
                <Routes>
                    <Route
                        path="/add-department"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(DepartmentService.createDepartment).toHaveBeenCalledWith({
                departmentName: "",
                departmentDescription: "",
            });
        });
    });

    test("checks correct flow when id is undefined in create mode", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: undefined,
        });

        render(
            <MemoryRouter initialEntries={["/add-department"]}>
                <Routes>
                    <Route
                        path="/add-department"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(DepartmentService.getDepartmentById).toHaveBeenCalledWith(
                undefined
            );
        });

        const submitButton = screen.getByRole("button", { name: /Submit/i });
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(DepartmentService.createDepartment).toHaveBeenCalled();
            expect(DepartmentService.updateDepartment).not.toHaveBeenCalled();
        });
    });

    test("navigates back to departments list when Back button is clicked", async () => {
        jest.spyOn(require("react-router-dom"), "useParams").mockReturnValue({
            id: undefined,
        });

        render(
            <MemoryRouter initialEntries={["/add-department"]}>
                <Routes>
                    <Route
                        path="/add-department"
                        element={<DepartmentComponent />}
                    />
                </Routes>
            </MemoryRouter>
        );

        const backButton = screen.getByRole("button", { name: /Back/i });
        fireEvent.click(backButton);

        expect(mockNavigate).toHaveBeenCalledWith("/departments");
    });
});
