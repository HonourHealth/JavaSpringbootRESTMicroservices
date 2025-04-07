import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import ListDepartmentComponent from "../../src/components/ListDepartmentComponent";
import {
    getAllDepartments,
    deleteDepartment,
} from "../../src/services/DepartmentService";

jest.mock("../../src/services/DepartmentService");

const mockedUseNavigate = jest.fn();
jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    useNavigate: () => mockedUseNavigate,
}));

describe("ListDepartmentComponent", () => {
    const mockDepartments = [
        {
            id: 1,
            departmentName: "Engineering",
            departmentDescription: "Software Engineering Department",
        },
        {
            id: 2,
            departmentName: "HR",
            departmentDescription: "Human Resources Department",
        },
    ];

    beforeEach(() => {
        jest.clearAllMocks();

        getAllDepartments.mockResolvedValue({ data: mockDepartments });
    });

    test("renders component with department list", async () => {
        render(
            <BrowserRouter>
                <ListDepartmentComponent />
            </BrowserRouter>
        );

        expect(screen.getByText("List of Departments")).toBeInTheDocument();

        await waitFor(() => {
            expect(screen.getByText("Engineering")).toBeInTheDocument();
        });

        expect(screen.getByText("Department ID")).toBeInTheDocument();
        expect(screen.getByText("Department Name")).toBeInTheDocument();
        expect(screen.getByText("Department Description")).toBeInTheDocument();
        expect(screen.getByText("Actions")).toBeInTheDocument();

        expect(screen.getByText("HR")).toBeInTheDocument();
        expect(
            screen.getByText("Software Engineering Department")
        ).toBeInTheDocument();
        expect(
            screen.getByText("Human Resources Department")
        ).toBeInTheDocument();

        const updateButtons = screen.getAllByText("Update");
        const deleteButtons = screen.getAllByText("Delete");
        expect(updateButtons).toHaveLength(2);
        expect(deleteButtons).toHaveLength(2);

        expect(getAllDepartments).toHaveBeenCalledTimes(1);
    });

    test("handles API error when fetching departments", async () => {
        const originalConsoleError = console.error;
        console.error = jest.fn();

        const errorMessage = "Failed to fetch departments";
        getAllDepartments.mockRejectedValueOnce(new Error(errorMessage));

        render(
            <BrowserRouter>
                <ListDepartmentComponent />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(console.error).toHaveBeenCalledWith(
                "Error fetching departments:",
                expect.any(Error)
            );
        });

        console.error = originalConsoleError;
    });

    test("navigates to edit department page when Update button is clicked", async () => {
        render(
            <BrowserRouter>
                <ListDepartmentComponent />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(screen.getByText("Engineering")).toBeInTheDocument();
        });

        const updateButtons = screen.getAllByText("Update");
        fireEvent.click(updateButtons[0]);

        expect(mockedUseNavigate).toHaveBeenCalledWith("/edit-department/1");
    });

    test("deletes department when Delete button is clicked", async () => {
        deleteDepartment.mockResolvedValueOnce({
            data: "Department deleted successfully",
        });

        render(
            <BrowserRouter>
                <ListDepartmentComponent />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(screen.getByText("Engineering")).toBeInTheDocument();
        });

        const deleteButtons = screen.getAllByText("Delete");
        fireEvent.click(deleteButtons[0]);

        await waitFor(() => {
            expect(deleteDepartment).toHaveBeenCalledWith(1);

            expect(getAllDepartments).toHaveBeenCalledTimes(2);
        });
    });

    test("handles error when deleting department", async () => {
        const originalConsoleError = console.error;
        console.error = jest.fn();

        const errorMessage = "Failed to delete department";
        deleteDepartment.mockRejectedValueOnce(new Error(errorMessage));

        render(
            <BrowserRouter>
                <ListDepartmentComponent />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(screen.getByText("Engineering")).toBeInTheDocument();
        });

        const deleteButtons = screen.getAllByText("Delete");
        fireEvent.click(deleteButtons[0]);

        await waitFor(() => {
            expect(console.error).toHaveBeenCalledWith(
                "Error deleting department:",
                expect.any(Error)
            );
        });

        console.error = originalConsoleError;
    });

    test("renders Add Department button that links to the add department page", async () => {
        render(
            <BrowserRouter>
                <ListDepartmentComponent />
            </BrowserRouter>
        );

        const addButton = screen.getByText("Add Department");
        expect(addButton).toBeInTheDocument();

        expect(addButton.closest("a")).toHaveAttribute(
            "href",
            "/add-department"
        );
    });
});
