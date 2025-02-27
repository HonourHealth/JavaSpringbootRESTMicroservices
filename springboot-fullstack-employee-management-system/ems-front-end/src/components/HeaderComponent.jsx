import React from "react";

const HeaderComponent = () => {
    return (
        <div>
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <div className="container-fluid">
                        <a
                            className="navbar-brand mx-auto"
                            href="https://github.com/HonourHealth/JavaSpringbootRESTMicroservices/tree/main/springboot-fullstack-employee-management-system"
                        >
                            Employee Management System
                        </a>
                    </div>
                </nav>
            </header>
        </div>
    );
};

export default HeaderComponent;
