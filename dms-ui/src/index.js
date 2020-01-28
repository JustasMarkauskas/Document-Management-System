import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Switch, Route } from "react-router";
import { BrowserRouter } from "react-router-dom";



import LogInAdminContainer from "./components/LogInPage/LogInAdminContainer";
import LogInUserContainer from "./components/LogInPage/LogInUserContainer"


ReactDOM.render(
  <BrowserRouter>    
      <Switch>
        <Route exact path="/" component={LogInUserContainer} />
        <Route path="/admin" component={LogInAdminContainer} />        
      </Switch>   
  </BrowserRouter>,

  document.getElementById("root")
);

serviceWorker.unregister();
