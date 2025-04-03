import React, { useEffect, useState } from "react";
import {
    deleteDepartment,
    getAllDepartments,
} from "../services/DepartmentService";
import { Link, useNavigate } from "react-router-dom";

const ListDepartmentComponent = () => {
    const [departments, setDepartments] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        listOfDepartments();
    }, []);

    function listOfDepartments() {
        getAllDepartments()
            .then((response) => {
                setDepartments(response.data);
            })
            .catch((error) => {
                console.error("Error fetching departments:", error);
            });
    }

    function updateDepartment(id) {
        navigate(`/edit-department/${id}`);
    }

    function removeDepartment(id) {
        deleteDepartment(id)
            .then((response) => {
                listOfDepartments();
            })
            .catch((error) => {
                console.error("Error deleting department:", error);
            });
    }

    return (
        <div className="container">
            <h2 className="text-center">List of Departments</h2>
            <Link to="/add-department" className="btn btn-primary mb-2">
                Add Department
            </Link>
            <table className="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>Department ID</th>
                        <th>Department Name</th>
                        <th>Department Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {departments.map((department) => (
                        <tr key={department.id}>
                            <td>{department.id}</td>
                            <td>{department.departmentName}</td>
                            <td>{department.departmentDescription}</td>
                            <td className="text-center">
                                <button
                                    onClick={() =>
                                        updateDepartment(department.id)
                                    }
                                    className="btn btn-info"
                                >
                                    Update
                                </button>
                                <button
                                    onClick={() =>
                                        removeDepartment(department.id)
                                    }
                                    className="btn btn-danger"
                                    style={{ marginLeft: "10px" }}
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ListDepartmentComponent;
