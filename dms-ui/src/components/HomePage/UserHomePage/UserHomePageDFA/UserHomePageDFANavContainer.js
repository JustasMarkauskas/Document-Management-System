import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDFANavComponent from "./UserHomePageDFANavComponents";
import serverUrl from "../../../URL/ServerUrl";

class UserHomePageDFANavContainer extends React.Component {
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
      <UserHomePageDFANavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(UserHomePageDFANavContainer);
