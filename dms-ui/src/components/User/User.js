import React from "react";

class User extends React.Component {
  constructor(props) {
    super(props);
    this._loggedIn = false;
<<<<<<< HEAD
    this._isAdmin = false;
=======
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
    this._name = "";
  }

  get loggedIn() {
    return this._loggedIn;
  }
  set loggedIn(loggedInValue) {
    this._loggedIn = loggedInValue;
  }

<<<<<<< HEAD
  get isAdmin() {
    return this._isAdmin;
  }
  set isAdmin(isAdmin) {
    this._isAdmin = isAdmin;
  }

=======
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
  get username() {
    return this._name;
  }
  set username(username) {
    this._name = username;
  }
}

var user = new User();

export default user;
