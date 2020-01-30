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
import AdminHomePageUsersContainer from "./components/HomePage/AdminHomePage/AdminHomePageUsers/AdminHomePageUsersContainer";

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
      <Route path="/users" component={AdminHomePageUsersContainer} />
    </Switch>
  </BrowserRouter>,

  document.getElementById("root")
);

serviceWorker.unregister();
