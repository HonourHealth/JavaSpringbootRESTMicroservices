import axios from "axios";
import EmployeeService from "./EmployeeService";

jest.mock("axios");

describe("EmployeeService", () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    test("getEmployee fetches employee successfully", async () => {
        const mockEmployee = {
            id: 2,
            firstName: "John",
            lastName: "Doe",
            email: "john@example.com",
        };

        axios.get.mockResolvedValueOnce({ data: mockEmployee });

        const result = await EmployeeService.getEmployee();

        expect(axios.get).toHaveBeenCalledWith(
            "http://localhost:9191/api/employees/2"
        );

        expect(result.data).toEqual(mockEmployee);
    });

    test("getEmployee fetches employee with error", async () => {
        axios.get.mockRejectedValueOnce(new Error("Async error"));

        await expect(EmployeeService.getEmployee()).rejects.toThrow(
            "Async error"
        );
    });
});
