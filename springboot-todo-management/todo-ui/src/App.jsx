import "./App.css";
import FooterComponent from "./components/FooterComponent";
import HeaderComponent from "./components/HeaderComponent";
import ListTodoComponent from "./components/ListTodoComponent";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import TodoComponent from "./components/TodoComponent";
import RegisterComponent from "./components/RegisterComponent";
import LoginComponent from "./components/LoginComponent";
import { isUserLoggedIn } from "./services/AuthService";

function App() {
    function AuthenticatedRoute({ children }) {
        const isAuthenticated = isUserLoggedIn();
        return isAuthenticated ? children : <Navigate to="/login" />;
    }

    return (
        <BrowserRouter>
            <HeaderComponent />
            <Routes>
                <Route path="/" element={<LoginComponent />} />
                <Route
                    path="/todos"
                    element={
                        <AuthenticatedRoute>
                            <ListTodoComponent />
                        </AuthenticatedRoute>
                    }
                />
                <Route
                    path="/add-todo"
                    element={
                        <AuthenticatedRoute>
                            <TodoComponent />
                        </AuthenticatedRoute>
                    }
                />
                <Route
                    path="/update-todo/:id"
                    element={
                        <AuthenticatedRoute>
                            <TodoComponent />
                        </AuthenticatedRoute>
                    }
                />
                <Route path="/register" element={<RegisterComponent />} />
                <Route path="/login" element={<LoginComponent />} />
            </Routes>
            <FooterComponent />
        </BrowserRouter>
    );
}

export default App;
