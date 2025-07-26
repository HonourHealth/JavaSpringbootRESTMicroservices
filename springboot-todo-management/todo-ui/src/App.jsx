import "./App.css";
import FooterComponent from "./components/FooterComponent";
import HeaderComponent from "./components/HeaderComponent";
import ListTodoComponent from "./components/ListTodoComponent";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import TodoComponent from "./components/TodoComponent";

function App() {
    return (
        <BrowserRouter>
            <HeaderComponent />
            <Routes>
                <Route path="/" element={<ListTodoComponent />} />
                <Route path="/todos" element={<ListTodoComponent />} />
                <Route path="/add-todo" element={<TodoComponent />} />
                <Route path="/update-todo/:id" element={<TodoComponent />} />
            </Routes>
            <FooterComponent />
        </BrowserRouter>
    );
}

export default App;
