import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { Switch, Route } from "react-router";
import { BrowserRouter } from "react-router-dom";

import NavigationComponent from "./components/Navigation/NavigationComponent";
import HomeJumbotronContainer from "./components/HomePage/HomeJumbotronContainer";

var AppContainer = props => {
  return (
    <div>
      <NavigationComponent />
      {props.children}
    </div>
  );
};

var NoMatch = props => {
  var goApp = () => props.history.push("/");
  return (
    <div>
      Sorry, this route does not exists
      <button onClick={goApp}>Go Home</button>
    </div>
  );
};

ReactDOM.render(
  <BrowserRouter>
    <AppContainer>
      <Switch>
        <Route exact path="/" component={HomeJumbotronContainer} />
        <Route path="*" component={NoMatch} />
        <Route component={NoMatch} />
      </Switch>
    </AppContainer>
  </BrowserRouter>,

  document.getElementById("root")
);

serviceWorker.unregister();
