import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/Layout/Header';
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter as Router, Route} from "react-router-dom";
import AddProject from './components/Project/AddProject';
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from './components/Project/UpdateProject';

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          {
            //<Route exact path="/dashboard" component={Dashboard}/>
          }
          <Route exact path="/dashboard" render={(props) => <Dashboard {...props} />}  />
          <Route exact path="/addProject" render={(props) => <AddProject {...props} />}  />
          <Route exact path="/updateProject/:id" component={UpdateProject}  />
          {
            //<Route exact path="/addProject" component={AddProject}  />
          }
        </div>
      </Router>
    </Provider>
  );
}

export default App;
