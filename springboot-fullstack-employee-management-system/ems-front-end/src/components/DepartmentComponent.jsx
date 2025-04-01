import React, { useEffect, useState } from "react";
import {
    createDepartment,
    getDepartmentById,
} from "../services/DepartmentService";
import { useNavigate, useParams } from "react-router-dom";

const DepartmentComponent = () => {
    const [departmentName, setDepartmentName] = useState("");
    const [departmentDescription, setDepartmentDescription] = useState("");

    const { id } = useParams();

    const navigator = useNavigate();

    useEffect(() => {
        getDepartmentById(id).then((response) => {
            setDepartmentName(response.data.departmentName);
            setDepartmentDescription(response.data.departmentDescription);
        }).catch((error) => {
            console.error("Error fetching department:", error);
        });
    }, [id]);

    function saveDepartment(e) {
        e.preventDefault();
        const department = {
            departmentName,
            departmentDescription,
        };

        createDepartment(department)
            .then((response) => {
                console.log(response.data);
                navigator("/departments");
            })
            .catch((error) => {
                console.error("Error creating department:", error);
            });
    }

    function pageTitle() {
        if (id) {
            return <h3 className="text-center">Update Department</h3>;
        } else {
            return <h3 className="text-center">Add Department</h3>;
        }
    }

    return (
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
                                    htmlFor="departmentName"
                                >
                                    Department Name:
                                </label>
                                <input
                                    type="text"
                                    id="departmentName"
                                    placeholder="Department Name"
                                    name="departmentName"
                                    className="form-control"
                                    value={departmentName}
                                    onChange={(e) =>
                                        setDepartmentName(e.target.value)
                                    }
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label
                                    className="form-label"
                                    htmlFor="departmentDescription"
                                >
                                    Department Description:
                                </label>
                                <input
                                    type="text"
                                    id="departmentDescription"
                                    placeholder="Department Description"
                                    name="departmentDescription"
                                    className="form-control"
                                    value={departmentDescription}
                                    onChange={(e) =>
                                        setDepartmentDescription(e.target.value)
                                    }
                                />
                            </div>
                            <button
                                onClick={(e) => saveDepartment(e)}
                                className="btn btn-success"
                                type="submit"
                            >
                                Submit
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DepartmentComponent;
