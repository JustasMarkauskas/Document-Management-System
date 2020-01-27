import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import LogInAdminComponent from "./LogInAdminComponent";

axios.defaults.withCredentials = true; // leidzia dalintis cookies
class LogInAdminContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      adminName: "",
      adminPassword: ""
    };
  }

  handleAdminNameChange = event => {
    this.setState({ adminName: event.target.value });
  };

  handleAdminPasswordChange = event => {
    this.setState({ adminPassword: event.target.value });
  };

  //   handleUserButton = event => {
  //     event.preventDefault();
  //     this.props.history.push("/");
  //   };

  handleAdminLogIn = event => {
    let userData = new URLSearchParams();
    userData.append("username", this.state.adminName);
    userData.append("password", this.state.adminPassword);
    axios
      .post("http://localhost:8081/login", userData, {
        headers: { "Content-type": "application/x-www-form-urlencoded" }
      })
      .then(resp => {
        console.log("user " + resp.data.username + " logged in"); //veliau istrinti
        this.props.history.push("/adminPage");
      })
      .catch(e => {
        console.log(e.resp);
      });
    event.preventDefault();
  };

  render() {
    return (
      <LogInAdminComponent
        handleAdminNameChange={this.handleAdminNameChange}
        handleAdminPasswordChange={this.handleAdminPasswordChange}
        handleAdminLogIn={this.handleAdminLogIn}
      />
    );
  }
}
export default withRouter(LogInAdminContainer);
