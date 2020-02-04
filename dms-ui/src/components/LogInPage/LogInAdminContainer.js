import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import LogInAdminComponent from "./LogInAdminComponent";

axios.defaults.withCredentials = true; // leidzia dalintis cookies
class LogInAdminContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      Admin: {
        adminName: "",
        adminPassword: ""
      }
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
        if (resp.data.isAdmin === "true") {
          this.props.history.push("/adminhomepage-users");
        } else {
          this.props.history.push("/userhomepage-documents");
        }
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
