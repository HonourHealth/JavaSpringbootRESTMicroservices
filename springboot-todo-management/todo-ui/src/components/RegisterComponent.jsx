import { useState } from "react";
import { registerAPICall } from "../services/AuthService";

const RegisterComponent = () => {
    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    function handleRegistrationForm(e) {
        e.preventDefault();
        const register = {
            name,
            username,
            email,
            password,
        };
        console.log("User Data Submitted:", register);
        registerAPICall(register)
            .then((response) => {
                console.log("Registration Successful:", response.data);
            })
            .catch((error) => {
                console.error("Registration Failed:", error);
            });
    }

    return (
        <div className="container">
            <br />
            <br />
            <div className="row">
                <div className="col-md-6 offset-md-3">
                    <div className="card">
                        <div className="card-header">
                            <h2 className="text-center">
                                User Registration Form
                            </h2>
                        </div>
                        <div className="card-body">
                            <form>
                                <div className="row mb-3">
                                    <label
                                        htmlFor="name"
                                        className="col-md-3 control-label"
                                    >
                                        Name
                                    </label>
                                    <div className="col-md-9">
                                        <input
                                            type="text"
                                            id="name"
                                            name="name"
                                            className="form-control"
                                            value={name}
                                            onChange={(e) =>
                                                setName(e.target.value)
                                            }
                                            placeholder="Enter your name"
                                        />
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <label
                                        htmlFor="username"
                                        className="col-md-3 control-label"
                                    >
                                        Username
                                    </label>
                                    <div className="col-md-9">
                                        <input
                                            type="text"
                                            id="username"
                                            name="username"
                                            className="form-control"
                                            value={username}
                                            onChange={(e) =>
                                                setUsername(e.target.value)
                                            }
                                            placeholder="Enter your username"
                                        />
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <label
                                        htmlFor="email"
                                        className="col-md-3 control-label"
                                    >
                                        Email
                                    </label>
                                    <div className="col-md-9">
                                        <input
                                            type="text"
                                            id="email"
                                            name="email"
                                            className="form-control"
                                            value={email}
                                            onChange={(e) =>
                                                setEmail(e.target.value)
                                            }
                                            placeholder="Enter your email address"
                                        />
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <label
                                        htmlFor="password"
                                        className="col-md-3 control-label"
                                    >
                                        Password
                                    </label>
                                    <div className="col-md-9">
                                        <input
                                            type="password"
                                            id="password"
                                            name="password"
                                            className="form-control"
                                            value={password}
                                            onChange={(e) =>
                                                setPassword(e.target.value)
                                            }
                                            placeholder="Enter your password"
                                        />
                                    </div>
                                </div>
                                <div className="form-group mb-3">
                                    <button
                                        className="btn btn-primary"
                                        onClick={(e) =>
                                            handleRegistrationForm(e)
                                        }
                                    >
                                        Submit
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RegisterComponent;
