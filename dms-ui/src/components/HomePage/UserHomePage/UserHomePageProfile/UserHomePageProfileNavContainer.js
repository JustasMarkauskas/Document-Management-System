import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageProfileNavComponent from "./UserHomePageProfileNavComponents";
import serverUrl from "../../../URL/ServerUrl";

class UserHomePageProfileNavContainer extends React.Component {
  handleLogoutButton = event => {
    axios
      .post(serverUrl + "logout")
      .then(() => {
        this.props.history.push("/dms/");
      })
      .catch(e => {
        console.log(e);
      });
    event.preventDefault();
  };

  render() {
    return (
      <UserHomePageProfileNavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(UserHomePageProfileNavContainer);
