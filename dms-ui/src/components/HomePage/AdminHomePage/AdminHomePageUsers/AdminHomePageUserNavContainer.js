import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageUserNavComponent from "./AdminHomePageUserNavComponent";
import serverUrl from "../../../URL/ServerUrl";

class AdminHomePageUserNavContainer extends React.Component {
  handleLogoutButton = event => {
    axios
      .post(serverUrl + "logout")
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
      <AdminHomePageUserNavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(AdminHomePageUserNavContainer);
