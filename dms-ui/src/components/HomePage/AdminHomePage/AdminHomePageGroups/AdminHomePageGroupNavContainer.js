import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageGroupNavComponent from "./AdminHomePageGroupNavcomponent";

class AdminHomePageGroupNavContainer extends React.Component {
  handleLogoutButton = event => {
    axios
      .post("http://localhost:8081/logout")
      .then(() => {
        this.props.history.push("/");
      })
      .catch(e => {
        console.log(e);
      });
    event.preventDefault();
  };

  render() {
    return (
      <AdminHomePageGroupNavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(AdminHomePageGroupNavContainer);
