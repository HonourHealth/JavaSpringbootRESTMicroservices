import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import App from "./App";

describe("App Component", () => {
    test("renders employee component", () => {
        render(<App />);
        const headerElement = screen.getByText("View Employee Details");
        expect(headerElement).toBeInTheDocument();
    });
});
