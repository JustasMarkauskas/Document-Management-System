import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageDocumentsNavComponent from "./AdminHomePageDocumentsNavComponent";
import serverUrl from "../../../URL/ServerUrl";

class AdminHomePageDocumentsNavContainer extends React.Component {
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
      <AdminHomePageDocumentsNavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(AdminHomePageDocumentsNavContainer);
