import React from "react";
import { NavLink } from "react-router-dom";
import { isUserLoggedIn } from "../services/AuthService";

const HeaderComponent = () => {
    const isAuthenticated = isUserLoggedIn();

    return (
        <div>
            <header>
                <nav className="navbar navbar-expand-md navbar-light bg-secondary">
                    <div className="container-fluid position-relative">
                        <div className="navbar-nav">
                            {isAuthenticated && (
                                <NavLink to="/todos" className="nav-link">
                                    Todos
                                </NavLink>
                            )}
                        </div>
                        <a
                            href="http://localhost:3000"
                            className="navbar-brand position-absolute start-50 translate-middle-x"
                            style={{ fontSize: "1.5rem" }}
                        >
                            Todo Management App
                        </a>

                        <div className="navbar-nav ms-auto">
                            {!isAuthenticated && (
                                <NavLink to="/register" className="nav-link">
                                    Register
                                </NavLink>
                            )}
                            {!isAuthenticated && (
                                <NavLink to="/login" className="nav-link">
                                    Login
                                </NavLink>
                            )}
                        </div>
                    </div>
                </nav>
            </header>
        </div>
    );
};

export default HeaderComponent;
