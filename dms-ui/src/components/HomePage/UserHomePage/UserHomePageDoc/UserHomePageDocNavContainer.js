import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDocNavComponent from "./UserHomePageDocNavComponents";
import serverUrl from "../../../URL/ServerUrl";

class UserHomePageDocNavContainer extends React.Component {
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
      <UserHomePageDocNavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(UserHomePageDocNavContainer);
