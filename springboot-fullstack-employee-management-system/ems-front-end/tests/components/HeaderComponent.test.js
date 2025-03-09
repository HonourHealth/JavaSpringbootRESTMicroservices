import { describe, expect, test } from "@jest/globals";
import { render, screen } from "@testing-library/react";
import HeaderComponent from "../../src/components/HeaderComponent.jsx";

describe("HeaderComponent", () => {
    test("renders HeaderComponent correctly", () => {
        render(<HeaderComponent />);

        const headerElement = screen.getByRole("banner");
        expect(headerElement).toBeInTheDocument();
    });

    test("renders navigation bar", () => {
        render(<HeaderComponent />);

        const navElement = screen.getByRole("navigation");
        expect(navElement).toBeInTheDocument();
        expect(navElement).toHaveClass("navbar-dark");
        expect(navElement).toHaveClass("bg-dark");
    });

    test("displays the correct title", () => {
        render(<HeaderComponent />);

        const titleElement = screen.getByText("Employee Management System");
        expect(titleElement).toBeInTheDocument();
    });

    test("title link has correct href attribute", () => {
        render(<HeaderComponent />);

        const linkElement = screen.getByRole("link", {
            name: "Employee Management System",
        });
        expect(linkElement).toHaveAttribute(
            "href",
            "https://github.com/HonourHealth/JavaSpringbootRESTMicroservices/tree/main/springboot-fullstack-employee-management-system"
        );
        expect(linkElement).toHaveClass("navbar-brand");
    });
});
