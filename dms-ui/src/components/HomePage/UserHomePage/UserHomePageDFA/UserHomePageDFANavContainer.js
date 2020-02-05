import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDFANavComponent from "./UserHomePageDFANavComponents";

class UserHomePageDFANavContainer extends React.Component {
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
      <UserHomePageDFANavComponent
        handleLogoutButton={this.handleLogoutButton}
      />
    );
  }
}
export default withRouter(UserHomePageDFANavContainer);
