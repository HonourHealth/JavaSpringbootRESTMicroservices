import { describe, expect, jest, test } from "@jest/globals";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "../../src/App";

jest.mock("react-dom/client", () => ({
    createRoot: jest.fn(() => ({
        render: jest.fn(),
    })),
}));

jest.mock("../../src/App.jsx", () => {
    return function MockApp() {
        return <div data-testid="mock-app">App Component</div>;
    };
});

jest.mock("../../src/index.css", () => ({}), { virtual: true });
jest.mock("bootstrap/dist/css/bootstrap.min.css", () => ({}), {
    virtual: true,
});

describe("Main Entry Point", () => {
    test("renders App component in StrictMode", () => {
        const mockRootElement = document.createElement("div");
        jest.spyOn(document, "getElementById").mockReturnValue(mockRootElement);

        require("../../src/main.jsx");

        expect(document.getElementById).toHaveBeenCalledWith("root");
        expect(createRoot).toHaveBeenCalledWith(mockRootElement);

        const mockRoot = createRoot.mock.results[0].value;
        expect(mockRoot.render).toHaveBeenCalled();

        const renderArg = mockRoot.render.mock.calls[0][0];

        expect(renderArg.type).toBe(StrictMode);

        expect(renderArg.props.children.type).toBe(App);
    });
});
