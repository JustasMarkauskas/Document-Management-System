import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Switch, Route } from "react-router";
import { BrowserRouter } from "react-router-dom";



import LogInAdminContainer from "./components/LogInPage/LogInAdminContainer";
import LogInUserContainer from "./components/LogInPage/LogInUserContainer";
import AdminHomePageContainer from "./components/HomePage/AdminHomePage/AdminHomePageContainer";
import AdminHomePageDocumentContainer from "./components/HomePage/AdminHomePage/AdminHomePageDocuments/AdminHomePageDocumentContainer";
import AdminHomePageGroupContainer from "./components/HomePage/AdminHomePage/AdminHomePageGroups/AdminHomePageGroupsContainer"

ReactDOM.render(
  <BrowserRouter>    
      <Switch>
        <Route exact path="/" component={LogInUserContainer} />
        <Route path="/admin" component={LogInAdminContainer} />
        <Route path="/adminhomepage-users" component={AdminHomePageContainer}/>
        <Route path="/adminhomepage-documents" component={AdminHomePageDocumentContainer}/>
        <Route path="/adminhomepage-groups" component={AdminHomePageGroupContainer}/>
      </Switch>   
  </BrowserRouter>,

  document.getElementById("root")
);

serviceWorker.unregister();
