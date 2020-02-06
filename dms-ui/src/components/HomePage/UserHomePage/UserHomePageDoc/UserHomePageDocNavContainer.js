import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDocNavComponent from "./UserHomePageDocNavComponents";

class UserHomePageDocNavContainer extends React.Component {
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
      <UserHomePageDocNavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(UserHomePageDocNavContainer);
