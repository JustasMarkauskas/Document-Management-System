import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import LogInUserComponent from "./LogInUserComponents";

axios.defaults.withCredentials = true; // leidzia dalintis cookies
class LoginUserContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      User: {
        userName: "",
        userPassword: ""
      }
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
        if (resp.data.isAdmin === "true") {
          this.props.history.push("/adminhomepage-users");
        } else {
          this.props.history.push("/userhomepage");
        }
      })
      .catch(e => {
        console.log(e.resp);
      });
    event.preventDefault();
  };

  render() {
    return (
      <LogInUserComponent
        handleUserNameChange={this.handleUserNameChange}
        handleUserPasswordChange={this.handleUserPasswordChange}
        handleUserLogIn={this.handleUserLogIn}
      />
    );
  }
}
export default withRouter(LoginUserContainer);
