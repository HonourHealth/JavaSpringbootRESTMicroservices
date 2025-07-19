import { useEffect, useState } from "react";
import { getAllTodos } from "../services/TodoService";

const ListTodoComponent = () => {
    const [todos, setTodos] = useState([]);

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

    return (
        <div className="container">
            <h2 className="text-center">List of Todos</h2>
            <div>
                <table className="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Completed</th>
                        </tr>
                    </thead>
                    <tbody>
                        {todos.map((todo) => (
                            <tr key={todo.id}>
                                <td>{todo.id}</td>
                                <td>{todo.title}</td>
                                <td>{todo.description}</td>
                                <td>{todo.completed ? "Yes" : "No"}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListTodoComponent;
