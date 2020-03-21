import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Switch } from "react-router";
import { Route, BrowserRouter } from "react-router-dom";
import LogInUserContainer from "./components/LogInPage/LogInUserContainer";
import AdminHomePageContainer from "./components/HomePage/AdminHomePage/AdminHomePageContainer";
import AdminHomePageDocumentsContainer from "./components/HomePage/AdminHomePage/AdminHomePageDocuments/AdminHomePageDocumentsContainer";
import AdminHomePageGroupContainer from "./components/HomePage/AdminHomePage/AdminHomePageGroups/AdminHomePageGroupsContainer";
import UserHomePageContainer from "./components/HomePage/UserHomePage/UserHomePageDoc/UserHomePageContainer";
import UserHomePageDFAContainer from "./components/HomePage/UserHomePage/UserHomePageDFA/UserHomePageDFAContainer";
import SubmittedDocReviewContainer from "./components/HomePage/UserHomePage/UserHomePageDoc/SubmittedDocumentReviewPage/SubmittedDocReviewContainer";
import GroupReviewMainPage from "./components/HomePage/AdminHomePage/AdminHomePageGroups/GroupReviewPage/GroupReviewMainPage";
import DFAStatisticsMainPage from "./components/HomePage/UserHomePage/UserHomePageDFA/DFAStatisticsPage/DFAStatisticsMainPage";
import UserHomePageProfileMainPage from "./components/HomePage/UserHomePage/UserHomePageProfile/UserHomePageProfileMainPage";
import UserReviewMainPage from "./components/HomePage/AdminHomePage/AdminHomePageUsers/UserReviewPage/UserReviewMainPage";
import ReactNotification from "react-notifications-component";
import "react-notifications-component/dist/theme.css";

var AppContainer = props => {
  return (
    <div>
      <ReactNotification />
      {props.children}
    </div>
  );
};

ReactDOM.render(
  <BrowserRouter>
    <AppContainer>
      <Switch>
        <Route exact path="/dms/" component={LogInUserContainer} />
        <Route
          exact
          path="/dms/adminhomepage-users"
          component={AdminHomePageContainer}
        />
        <Route
          exact
          path="/dms/submitted-document"
          component={SubmittedDocReviewContainer}
        />
        <Route
          exact
          path="/dms/adminhomepage-documents"
          component={AdminHomePageDocumentsContainer}
        />
        <Route
          exact
          path="/dms/adminhomepage-groups"
          component={AdminHomePageGroupContainer}
        />
        <Route
          exact
          path="/dms/userhomepage-documents"
          component={UserHomePageContainer}
        />
        <Route
          exact
          path="/dms/userhomepage-dfa"
          component={UserHomePageDFAContainer}
        />
        <Route
          exact
          path="/dms/group-review/:groupName"
          component={GroupReviewMainPage}
        />
        <Route
          exact
          path="/dms/user-review/:username"
          component={UserReviewMainPage}
        />
        <Route
          exact
          path="/dms/dfa-statistics/:username"
          component={DFAStatisticsMainPage}
        />
        <Route
          exact
          path="/dms/userhomepage-profile"
          component={UserHomePageProfileMainPage}
        />
      </Switch>
    </AppContainer>
  </BrowserRouter>,
  document.getElementById("root")
);

serviceWorker.unregister();
