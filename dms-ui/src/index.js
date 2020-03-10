import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Switch, Route } from "react-router";
import { HashRouter as Router, BrowserRouter } from "react-router-dom";
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

var AppContainer = props => {
  return <div>{props.children}</div>;
};

ReactDOM.render(
  <BrowserRouter>
    <Router>
      <AppContainer>
        <Switch>
          <Route exact path="/" component={LogInUserContainer} />
          <Route
            path="/adminhomepage-users"
            component={AdminHomePageContainer}
          />
          <Route
            path="/submitted-document"
            component={SubmittedDocReviewContainer}
          />
          <Route
            path="/adminhomepage-documents"
            component={AdminHomePageDocumentsContainer}
          />
          <Route
            path="/adminhomepage-groups"
            component={AdminHomePageGroupContainer}
          />
          <Route
            path="/userhomepage-documents"
            component={UserHomePageContainer}
          />
          <Route
            path="/userhomepage-dfa"
            component={UserHomePageDFAContainer}
          />
          <Route
            path="/group-review/:groupName"
            component={GroupReviewMainPage}
          />
          <Route path="/user-review/:username" component={UserReviewMainPage} />
          <Route
            path="/dfa-statistics/:username"
            component={DFAStatisticsMainPage}
          />
          <Route
            path="/userhomepage-profile"
            component={UserHomePageProfileMainPage}
          />
        </Switch>
      </AppContainer>
    </Router>
  </BrowserRouter>,
  document.getElementById("root")
);

serviceWorker.unregister();
