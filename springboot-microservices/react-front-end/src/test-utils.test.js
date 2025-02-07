import React from "react";
import { render as customRender } from "./test-utils";

describe("test-utils", () => {
    test("customRender works correctly", () => {
        const TestComponent = () => <div>Test</div>;
        const { getByText } = customRender(<TestComponent />);
        expect(getByText("Test")).toBeInTheDocument();
    });
});
