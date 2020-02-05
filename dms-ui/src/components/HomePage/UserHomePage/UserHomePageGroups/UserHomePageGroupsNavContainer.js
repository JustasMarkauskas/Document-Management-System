import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageGroupsNavComponent from "./UserHomePageGroupsNavComponents";

class UserHomePageGroupsNavContainer extends React.Component {
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
      <UserHomePageGroupsNavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(UserHomePageGroupsNavContainer);
