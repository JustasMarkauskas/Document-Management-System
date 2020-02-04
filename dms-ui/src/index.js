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
import AdminHomePageDocumentsContainer from "./components/HomePage/AdminHomePage/AdminHomePageDocuments/AdminHomePageDocumentsContainer";
import AdminHomePageGroupContainer from "./components/HomePage/AdminHomePage/AdminHomePageGroups/AdminHomePageGroupsContainer";

import UserHomePageContainer from "./components/HomePage/UserHomePage/UserHomePageDoc/UserHomePageContainer";
import UserHomePageDFAContainer from "./components/HomePage/UserHomePage/UserHomePageDFA/UserHomePageDFAContainer";
import UserHomePageGroupsContainer from "./components/HomePage/UserHomePage/UserHomePageGroups/UserHomePageGroupsContainer";

ReactDOM.render(
  <BrowserRouter>
    <Switch>
      <Route exact path="/" component={LogInUserContainer} />
      <Route path="/admin" component={LogInAdminContainer} />
      <Route path="/adminhomepage-users" component={AdminHomePageContainer} />
      <Route
        path="/adminhomepage-documents"
        component={AdminHomePageDocumentsContainer}
      />
      <Route
        path="/adminhomepage-groups"
        component={AdminHomePageGroupContainer}
      />
      <Route
        path="/:username/userhomepage-documents"
        component={UserHomePageContainer}
      />
      <Route
        path="/:username/userhomepage-dfa"
        component={UserHomePageDFAContainer}
      />
      <Route
        path="/:username/userhomepage-groups"
        component={UserHomePageGroupsContainer}
      />
    </Switch>
  </BrowserRouter>,

  document.getElementById("root")
);

serviceWorker.unregister();
