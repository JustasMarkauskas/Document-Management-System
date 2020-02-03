import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Switch, Route } from "react-router";
import { BrowserRouter } from "react-router-dom";

import AddNewUserContainer from "./components/NewUserForm/AddNewUserContainer"


ReactDOM.render(
  <BrowserRouter>    
      <Switch>
        <Route exact path="/" component={AddNewUserContainer} />               
      </Switch>   
  </BrowserRouter>,

  document.getElementById("root")
);

serviceWorker.unregister();
