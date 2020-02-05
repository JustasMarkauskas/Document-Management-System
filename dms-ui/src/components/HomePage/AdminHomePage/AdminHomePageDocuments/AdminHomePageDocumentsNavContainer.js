import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageDocumentsNavComponent from "./AdminHomePageDocumentsNavComponent";

class AdminHomePageDocumentsNavContainer extends React.Component {
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
      <AdminHomePageDocumentsNavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(AdminHomePageDocumentsNavContainer);
