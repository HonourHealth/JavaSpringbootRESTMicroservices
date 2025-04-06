import { describe, expect, test } from "@jest/globals";
import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import HeaderComponent from "../../src/components/HeaderComponent.jsx";

describe("HeaderComponent", () => {
    const renderWithRouter = () => {
        return render(
            <MemoryRouter>
                <HeaderComponent />
            </MemoryRouter>
        );
    };

    test("renders HeaderComponent correctly", () => {
        renderWithRouter();

        const headerElement = screen.getByRole("banner");
        expect(headerElement).toBeInTheDocument();
    });

    test("renders navigation bar", () => {
        renderWithRouter();

        const navElement = screen.getByRole("navigation");
        expect(navElement).toBeInTheDocument();
        expect(navElement).toHaveClass("navbar-dark");
        expect(navElement).toHaveClass("bg-dark");
    });

    test("displays the correct title", () => {
        renderWithRouter();

        const titleElement = screen.getByText("Employee Management System");
        expect(titleElement).toBeInTheDocument();
    });

    test("title link has correct href attribute", () => {
        renderWithRouter();

        const linkElement = screen.getByRole("link", {
            name: "Employee Management System",
        });
        expect(linkElement).toHaveAttribute(
            "href",
            "https://github.com/HonourHealth/JavaSpringbootRESTMicroservices/tree/main/springboot-fullstack-employee-management-system"
        );
        expect(linkElement).toHaveClass("navbar-brand");
    });

    test("renders navigation links correctly", () => {
        renderWithRouter();

        const employeesLink = screen.getByRole("link", { name: /employees/i });
        const departmentsLink = screen.getByRole("link", { name: /departments/i });

        expect(employeesLink).toBeInTheDocument();
        expect(employeesLink).toHaveAttribute("href", "/employees");
        expect(employeesLink).toHaveClass("nav-link");

        expect(departmentsLink).toBeInTheDocument();
        expect(departmentsLink).toHaveAttribute("href", "/departments");
        expect(departmentsLink).toHaveClass("nav-link");
    });
});