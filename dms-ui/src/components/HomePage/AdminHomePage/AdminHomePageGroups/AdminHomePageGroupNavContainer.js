import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageGroupNavComponent from "./AdminHomePageGroupNavcomponent";

class AdminHomePageGroupNavContainer extends React.Component {
  handleLogoutButton = event => {
    axios
      .post("http://akademijait.vtmc.lt:8180/dms/logout")
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
