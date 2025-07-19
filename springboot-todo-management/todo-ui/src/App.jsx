import "./App.css";
import FooterComponent from "./components/FooterComponent";
import HeaderComponent from "./components/HeaderComponent";
import ListTodoComponent from "./components/ListTodoComponent";

function App() {
    return (
        <>
            <HeaderComponent />
            <ListTodoComponent />
            <FooterComponent />
        </>
    );
}

export default App;
