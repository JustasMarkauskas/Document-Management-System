import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import LogInAdminComponent from "./LogInAdminComponent";
import user from "../User/User";

axios.defaults.withCredentials = true; // leidzia dalintis cookies
class LogInAdminContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      Admin: {
        adminName: "",
        adminPassword: ""
      },
      incorrectLogin: false
    };
  }

  handleAdminNameChange = event => {
    this.setState({ adminName: event.target.value });
  };

  handleAdminPasswordChange = event => {
    this.setState({ adminPassword: event.target.value });
  };

  handleAdminLogIn = event => {
    let userData = new URLSearchParams();
    userData.append("username", this.state.adminName);
    userData.append("password", this.state.adminPassword);
    axios
      .post("http://localhost:8081/login", userData, {
        headers: {
          "Content-type": "application/x-www-form-urlencoded"
        }
      })
      .then(resp => {
        user.loggedIn = true;
        user.username = this.state.adminName;
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
  };

  render() {
    return (
      <LogInAdminComponent
        handleAdminNameChange={this.handleAdminNameChange}
        handleAdminPasswordChange={this.handleAdminPasswordChange}
        handleAdminLogIn={this.handleAdminLogIn}
        incorrectLogin={this.state.incorrectLogin}
      />
    );
  }
}
export default withRouter(LogInAdminContainer);
