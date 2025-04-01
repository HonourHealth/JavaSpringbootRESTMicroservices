import React, { useEffect, useState } from "react";
import { getAllDepartments } from "../services/DepartmentService";
import { Link, useNavigate } from "react-router-dom";

const ListDepartmentComponent = () => {
    const [departments, setDepartments] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        getAllDepartments()
            .then((response) => {
                setDepartments(response.data);
            })
            .catch((error) => {
                console.error("Error fetching departments:", error);
            });
    }, []);

    function updateDepartment(id) {
        navigate(`/edit-department/${id}`);
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
                            <td>
                                <button onClick={() => updateDepartment(department.id)} className="btn btn-info">Update</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ListDepartmentComponent;
