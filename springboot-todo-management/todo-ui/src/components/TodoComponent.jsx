import { useEffect, useState } from "react";
import { getTodoById, saveTodo, updateTodo } from "../services/TodoService";
import { useNavigate, useParams } from "react-router-dom";

const TodoComponent = () => {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [completed, setCompleted] = useState(false);
    const navigate = useNavigate();

    const { id } = useParams();

    useEffect(() => {
        if (id) {
            getTodoById(id)
                .then((response) => {
                    console.log(response.data);
                    const { title, description, completed } = response.data;
                    setTitle(title);
                    setDescription(description);
                    setCompleted(completed);
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    }, [id]);

    function saveOrUpdateTodo(e) {
        e.preventDefault();
        const todo = { title, description, completed };
        console.log(todo);

        if (id) {
            updateTodo(id, todo)
                .then((response) => {
                    console.log(response.data);
                    navigate("/todos");
                })
                .catch((error) => {
                    console.error(error);
                });
        } else {
            saveTodo(todo)
                .then((response) => {
                    console.log(response.data);
                    navigate("/todos");
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    }

    function pageTitle() {
        return id ? (
            <h2 className="text-center">Update Todo</h2>
        ) : (
            <h2 className="text-center">Add Todo</h2>
        );
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
                                    htmlFor="todoTitle"
                                    className="form-label"
                                >
                                    Todo Title:
                                </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Enter Todo Title"
                                    name="title"
                                    value={title}
                                    onChange={(e) => setTitle(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label
                                    htmlFor="todoDescription"
                                    className="form-label"
                                >
                                    Todo Description:
                                </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Enter Todo Description"
                                    name="description"
                                    value={description}
                                    onChange={(e) =>
                                        setDescription(e.target.value)
                                    }
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label
                                    htmlFor="todoCompleted"
                                    className="form-label"
                                >
                                    Todo Completed:
                                </label>
                                <select
                                    className="form-control"
                                    value={completed}
                                    onChange={(e) =>
                                        setCompleted(e.target.value)
                                    }
                                >
                                    <option value={false}>No</option>
                                    <option value={true}>Yes</option>
                                </select>
                            </div>
                            <button
                                className="btn btn-success"
                                //type="submit"
                                onClick={(e) => saveOrUpdateTodo(e)}
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

export default TodoComponent;
