import { describe, expect, test } from "@jest/globals";
import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";

jest.mock("../../src/App.css", () => ({}), { virtual: true });

jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    BrowserRouter: ({ children }) => children,
}));

import App from "../../src/App";

jest.mock("../../src/components/HeaderComponent", () => {
    return function MockHeaderComponent() {
        return <div data-testid="mock-header">Header Component</div>;
    };
});

jest.mock("../../src/components/FooterComponent", () => {
    return function MockFooterComponent() {
        return <div data-testid="mock-footer">Footer Component</div>;
    };
});

jest.mock("../../src/components/ListEmployeeComponent", () => {
    return function MockListEmployeeComponent() {
        return (
            <div data-testid="mock-list-employee">List Employee Component</div>
        );
    };
});

jest.mock("../../src/components/EmployeeComponent", () => {
    return function MockEmployeeComponent() {
        return <div data-testid="mock-employee">Employee Component</div>;
    };
});

describe("App Component", () => {
    test("renders header and footer components", () => {
        render(
            <MemoryRouter>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByTestId("mock-header")).toBeInTheDocument();
        expect(screen.getByTestId("mock-footer")).toBeInTheDocument();
    });

    test("renders ListEmployeeComponent for root path", () => {
        render(
            <MemoryRouter initialEntries={["/"]}>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByTestId("mock-list-employee")).toBeInTheDocument();
    });

    test("renders ListEmployeeComponent for /employees path", () => {
        render(
            <MemoryRouter initialEntries={["/employees"]}>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByTestId("mock-list-employee")).toBeInTheDocument();
    });

    test("renders EmployeeComponent for /add-employee path", () => {
        render(
            <MemoryRouter initialEntries={["/add-employee"]}>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByTestId("mock-employee")).toBeInTheDocument();
    });

    test("renders EmployeeComponent for /edit-employee/:id path", () => {
        render(
            <MemoryRouter initialEntries={["/edit-employee/1"]}>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByTestId("mock-employee")).toBeInTheDocument();
    });

    test("renders only header and footer for unknown routes", () => {
        render(
            <MemoryRouter initialEntries={["/unknown-route"]}>
                <App />
            </MemoryRouter>
        );

        expect(screen.getByTestId("mock-header")).toBeInTheDocument();
        expect(screen.getByTestId("mock-footer")).toBeInTheDocument();

        expect(
            screen.queryByTestId("mock-list-employee")
        ).not.toBeInTheDocument();
        expect(screen.queryByTestId("mock-employee")).not.toBeInTheDocument();
    });
});
