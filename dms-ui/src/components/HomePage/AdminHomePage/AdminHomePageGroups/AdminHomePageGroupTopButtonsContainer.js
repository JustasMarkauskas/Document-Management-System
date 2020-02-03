import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageGroupTopButtonsComponent from "./AdminHomePageGroupTopButtonsComponent";

class AdminHomePageGroupTopButtonsContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      groupName: ""
    };
  }

  handleSearchChange = event => {
    this.setState({ groupName: event.target.value });
  };

  handleSearchButton = event => {
    event.preventDefault();
    axios
      .get("http://localhost:8081/api/role/" + this.state.groupName)
      .then(response => {
        console.log(response);
        this.setState({ groupName: "" });
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <AdminHomePageGroupTopButtonsComponent
        handleSearchChange={this.handleSearchChange}
        handleSearchButton={this.handleSearchButton}
      />
    );
  }
}
export default withRouter(AdminHomePageGroupTopButtonsContainer);
