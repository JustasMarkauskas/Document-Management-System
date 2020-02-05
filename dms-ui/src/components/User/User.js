import React from "react";

class User extends React.Component {
  constructor(props) {
    super(props);
    this._loggedIn = false;
    this._name = "";
  }

  get loggedIn() {
    return this._loggedIn;
  }
  set loggedIn(loggedInValue) {
    this._loggedIn = loggedInValue;
  }

  get username() {
    return this._name;
  }
  set username(username) {
    this._name = username;
  }
}

var user = new User();

export default user;
