import "./App.css";
import FooterComponent from "./components/FooterComponent";
import HeaderComponent from "./components/HeaderComponent";
import ListTodoComponent from "./components/ListTodoComponent";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import TodoComponent from "./components/TodoComponent";
import RegisterComponent from "./components/RegisterComponent";
import LoginComponent from "./components/LoginComponent";

function App() {
    return (
        <BrowserRouter>
            <HeaderComponent />
            <Routes>
                <Route path="/" element={<ListTodoComponent />} />
                <Route path="/todos" element={<ListTodoComponent />} />
                <Route path="/add-todo" element={<TodoComponent />} />
                <Route path="/update-todo/:id" element={<TodoComponent />} />
                <Route path="/register" element={<RegisterComponent />} />
                <Route path="/login" element={<LoginComponent />} />
            </Routes>
            <FooterComponent />
        </BrowserRouter>
    );
}

export default App;
