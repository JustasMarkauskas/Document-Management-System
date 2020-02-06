import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import LogInUserComponent from "./LogInUserComponents";
import user from "../User/User";

axios.defaults.withCredentials = true;
class LoginUserContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      User: {
        userName: "",
        userPassword: ""
      },
      incorrectLogin: false
    };
  }

  handleUserNameChange = event => {
    this.setState({ userName: event.target.value });
  };

  handleUserPasswordChange = event => {
    this.setState({ userPassword: event.target.value });
  };

  handleUserLogIn = event => {
    let userData = new URLSearchParams();
    userData.append("username", this.state.userName);
    userData.append("password", this.state.userPassword);
    axios
      .post("http://localhost:8081/login", userData, {
        headers: {
          "Content-type": "application/x-www-form-urlencoded"
        }
      })
      .then(resp => {
        user.loggedIn = true;
        user.username = this.state.userName;
        if (resp.data.isAdmin === "true") {
          this.props.history.push("/adminhomepage-users");
        } else {
          this.props.history.push("/userhomepage-documents");
        }
      })
      .catch(e => {
        this.setState({ incorrectLogin: true });
        console.log(e.resp);
      });
    event.preventDefault();
    document.getElementById("inputUserNameLogin").value = "";
    document.getElementById("inputUserPasswordLogin").value = "";
  };

  render() {
    return (
      <LogInUserComponent
        handleUserNameChange={this.handleUserNameChange}
        handleUserPasswordChange={this.handleUserPasswordChange}
        handleUserLogIn={this.handleUserLogIn}
        incorrectLogin={this.state.incorrectLogin}
      />
    );
  }
}
export default withRouter(LoginUserContainer);
