import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
    createEmployee,
    getEmployee,
    updateEmployee,
} from "../services/EmployeeService";
import { getAllDepartments } from "../services/DepartmentService";

const EmployeeComponent = () => {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [departmentId, setDepartmentId] = useState("");
    const [departments, setDepartments] = useState([]);

    useEffect(() => {
        getAllDepartments()
            .then((response) => {
                setDepartments(response.data);
            })
            .catch((error) => {
                console.error("Error fetching departments:", error);
            });
    }, []);

    const navigate = useNavigate();

    const { id } = useParams();

    const [errors, setErrors] = useState({
        firstName: "",
        lastName: "",
        email: "",
        department: "",
    });

    useEffect(() => {
        if (id) {
            getEmployee(id)
                .then((response) => {
                    setFirstName(response.data.firstName);
                    setLastName(response.data.lastName);
                    setEmail(response.data.email);
                    setDepartmentId(response.data.departmentId);
                })
                .catch((error) => {
                    console.error("Error fetching employee:", error);
                });
        }
    }, [id]);

    function saveOrUpdateEmployee(e) {
        e.preventDefault();

        if (validateForm()) {
            const employee = {
                firstName,
                lastName,
                email,
                departmentId,
            };

            if (id) {
                updateEmployee(id, employee)
                    .then((response) => {
                        navigate("/employees");
                    })
                    .catch((error) => {
                        console.error("Error updating employee:", error);
                    });
            } else {
                createEmployee(employee)
                    .then((response) => {
                        navigate("/employees");
                    })
                    .catch((error) => {
                        console.error("Error saving employee:", error);
                    });
            }
        }
    }

    function goBack() {
        navigate("/employees");
    }

    function validateForm() {
        let valid = true;
        const errorsCopy = { ...errors };

        if (firstName.trim()) {
            errorsCopy.firstName = "";
        } else {
            errorsCopy.firstName = "First name is required";
            valid = false;
        }

        if (lastName.trim()) {
            errorsCopy.lastName = "";
        } else {
            errorsCopy.lastName = "Last name is required";
            valid = false;
        }

        if (email.trim()) {
            errorsCopy.email = "";
        } else {
            errorsCopy.email = "Email is required";
            valid = false;
        }

        if (departmentId) {
            errorsCopy.department = "";
        } else {
            errorsCopy.department = "Selecting department is required";
            valid = false;
        }

        setErrors(errorsCopy);
        return valid;
    }

    function pageTitle() {
        if (id) {
            return <h3 className="text-center">Update Employee</h3>;
        } else {
            return <h3 className="text-center">Add Employee</h3>;
        }
    }

    return (
        <div>
            <div className="container">
                <br />
                <br />
                <div className="row">
                    <div className="card col-md-6 offset-md-3 offset-md-3">
                        <br />
                        {pageTitle()}
                        <div className="card-body">
                            <form>
                                <div className="form-group mb-2">
                                    <label
                                        className="form-label"
                                        htmlFor="firstName"
                                    >
                                        First Name:
                                    </label>
                                    <input
                                        type="text"
                                        placeholder="Enter Employee First Name"
                                        name="firstName"
                                        className={`form-control ${
                                            errors.firstName ? "is-invalid" : ""
                                        }`}
                                        value={firstName}
                                        onChange={(e) =>
                                            setFirstName(e.target.value)
                                        }
                                    />
                                    {errors.firstName && (
                                        <div className="invalid-feedback">
                                            {errors.firstName}
                                        </div>
                                    )}
                                </div>
                                <div className="form-group mb-2">
                                    <label
                                        className="form-label"
                                        htmlFor="lastName"
                                    >
                                        Last Name:
                                    </label>
                                    <input
                                        type="text"
                                        placeholder="Enter Employee Last Name"
                                        name="lastName"
                                        className={`form-control ${
                                            errors.lastName ? "is-invalid" : ""
                                        }`}
                                        value={lastName}
                                        onChange={(e) =>
                                            setLastName(e.target.value)
                                        }
                                    />
                                    {errors.lastName && (
                                        <div className="invalid-feedback">
                                            {errors.lastName}
                                        </div>
                                    )}
                                </div>
                                <div className="form-group mb-2">
                                    <label
                                        className="form-label"
                                        htmlFor="email"
                                    >
                                        Email:
                                    </label>
                                    <input
                                        type="email"
                                        placeholder="Enter Email Address"
                                        name="email"
                                        className={`form-control ${
                                            errors.email ? "is-invalid" : ""
                                        }`}
                                        value={email}
                                        onChange={(e) =>
                                            setEmail(e.target.value)
                                        }
                                    />
                                    {errors.email && (
                                        <div className="invalid-feedback">
                                            {errors.email}
                                        </div>
                                    )}
                                </div>

                                <div className="form-group mb-2">
                                    <label
                                        className="form-label"
                                        htmlFor="department"
                                    >
                                        Select Department:
                                    </label>
                                    <select
                                        className={`form-control ${
                                            errors.department
                                                ? "is-invalid"
                                                : ""
                                        }`}
                                        name="department"
                                        id="department"
                                        value={departmentId}
                                        onChange={(e) =>
                                            setDepartmentId(e.target.value)
                                        }
                                    >
                                        <option value="Select Department">
                                            Select Department
                                        </option>
                                        {departments.map((department) => (
                                            <option
                                                key={department.id}
                                                value={department.id}
                                            >
                                                {department.departmentName}
                                            </option>
                                        ))}
                                    </select>
                                    {errors.department && (
                                        <div className="invalid-feedback">
                                            {errors.department}
                                        </div>
                                    )}
                                </div>

                                <button
                                    className="btn btn-success"
                                    type="submit"
                                    onClick={saveOrUpdateEmployee}
                                >
                                    Submit
                                </button>
                                <button
                                    className="btn btn-danger"
                                    onClick={goBack}
                                    type="button"
                                    style={{ marginLeft: "10px" }}
                                >
                                    Back
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EmployeeComponent;
