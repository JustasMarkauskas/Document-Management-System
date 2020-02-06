import React from "react";

class User extends React.Component {
  constructor(props) {
    super(props);
    this._loggedIn = false;
    this._isAdmin = false;
    this._name = "";
  }

  get loggedIn() {
    return this._loggedIn;
  }
  set loggedIn(loggedInValue) {
    this._loggedIn = loggedInValue;
  }

  get isAdmin() {
    return this._isAdmin;
  }
  set isAdmin(isAdmin) {
    this._isAdmin = isAdmin;
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
