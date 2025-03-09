import { describe, expect, test } from "@jest/globals";
import { render, screen } from "@testing-library/react";
import FooterComponent from "../../src/components/FooterComponent.jsx";

describe("FooterComponent", () => {
    test("renders FooterComponent correctly", () => {
        render(<FooterComponent />);
        const footerElement = screen.getByRole("contentinfo");
        expect(footerElement).toBeInTheDocument();
    });

    test("displays the correct copywrite text", () => {
        render(<FooterComponent />);
        const copywriteElement = screen.getByText(
            /All rights reserved 2025 by Onur Can/i
        );
        expect(copywriteElement).toBeInTheDocument();
    });

    test("footer has the correct CSS class", () => {
        render(<FooterComponent />);
        const footerElement = screen.getByRole("contentinfo");
        expect(footerElement).toHaveClass("footer");
    });
});
