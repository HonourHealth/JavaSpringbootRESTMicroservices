import { useEffect, useState } from "react";
import {
    completeTodo,
    deleteTodo,
    getAllTodos,
    incompleteTodo,
} from "../services/TodoService";
import { useNavigate } from "react-router-dom";

const ListTodoComponent = () => {
    const [todos, setTodos] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        listTodos();
    }, []);

    function listTodos() {
        getAllTodos()
            .then((response) => {
                setTodos(response.data);
            })
            .catch((error) => {
                console.error("Error fetching todos:", error);
            });
    }

    function addNewTodo() {
        navigate("/add-todo");
    }

    function updateTodo(id) {
        console.log("Update todo with ID:", id);
        navigate(`/update-todo/${id}`);
    }

    function removeTodo(id) {
        deleteTodo(id)
            .then(() => {
                listTodos();
            })
            .catch((error) => {
                console.error("Error deleting todo:", error);
            });
    }

    function toggleTodoCompletion(id, isCompleted) {
        const action = isCompleted ? incompleteTodo : completeTodo;
        const actionText = isCompleted
            ? "marking todo as incomplete"
            : "completing todo";

        action(id)
            .then(() => {
                listTodos();
            })
            .catch((error) => {
                console.error(`Error ${actionText}:`, error);
            });
    }

    return (
        <div className="container">
            <br />
            <h2 className="text-center">List of Todos</h2>
            <button className="btn btn-primary mb-2" onClick={addNewTodo}>
                Add Todo
            </button>
            <div>
                <table className="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th style={{ textAlign: "center" }}>ID</th>
                            <th style={{ textAlign: "center" }}>Title</th>
                            <th style={{ textAlign: "center" }}>Description</th>
                            <th style={{ textAlign: "center" }}>Completed</th>
                            <th style={{ textAlign: "center" }}>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {todos.map((todo) => (
                            <tr key={todo.id}>
                                <td
                                    style={{
                                        width: "1px",
                                        whiteSpace: "nowrap",
                                        textAlign: "center",
                                    }}
                                >
                                    {todo.id}
                                </td>
                                <td>{todo.title}</td>
                                <td>{todo.description}</td>
                                <td
                                    style={{
                                        width: "1px",
                                        whiteSpace: "nowrap",
                                        textAlign: "center",
                                    }}
                                >
                                    {todo.completed ? "Yes" : "No"}
                                </td>
                                <td
                                    style={{
                                        width: "1px",
                                        whiteSpace: "nowrap",
                                    }}
                                >
                                    <button
                                        className="btn btn-info"
                                        style={{ marginRight: "5px" }}
                                        onClick={() => updateTodo(todo.id)}
                                    >
                                        Update
                                    </button>
                                    <button
                                        className="btn btn-danger"
                                        style={{ marginRight: "5px" }}
                                        onClick={() => {
                                            if (
                                                window.confirm(
                                                    "Are you sure you want to delete this todo?"
                                                )
                                            ) {
                                                removeTodo(todo.id);
                                            }
                                        }}
                                    >
                                        Delete
                                    </button>
                                    <button
                                        className={`btn ${
                                            todo.completed
                                                ? "btn-warning"
                                                : "btn-success"
                                        }`}
                                        onClick={() => {
                                            const actionText = todo.completed
                                                ? "mark this todo as incomplete"
                                                : "mark this todo as complete";
                                            if (
                                                window.confirm(
                                                    `Are you sure you want to ${actionText}?`
                                                )
                                            ) {
                                                toggleTodoCompletion(
                                                    todo.id,
                                                    todo.completed
                                                );
                                            }
                                        }}
                                    >
                                        {todo.completed
                                            ? "Mark Incomplete"
                                            : "Mark Complete"}
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListTodoComponent;
