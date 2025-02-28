import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createEmployee } from "../services/EmployeeService";

const EmployeeComponent = () => {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const navigate = useNavigate();

    const [errors, setErrors] = useState({
        firstName: "",
        lastName: "",
        email: "",
    });

    function saveEmployee(e) {
        e.preventDefault();

        if (validateForm()) {
            const employee = {
                firstName,
                lastName,
                email,
            };
            console.log("Employee => " + JSON.stringify(employee));

            createEmployee(employee)
                .then((response) => {
                    console.log(response.data);
                    navigate("/employees");
                })
                .catch((error) => {
                    console.error("Error saving employee:", error);
                });
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

        setErrors(errorsCopy);
        return valid;
    }

    return (
        <div>
            <div className="container">
                <br />
                <br />
                <div className="row">
                    <div className="card col-md-6 offset-md-3 offset-md-3">
                        <br />
                        <h3 className="text-center">Add Employee</h3>
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
                                <button
                                    className="btn btn-success"
                                    type="submit"
                                    onClick={saveEmployee}
                                >
                                    Submit
                                </button>
                                <button
                                    className="btn btn-danger"
                                    onClick={goBack}
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
