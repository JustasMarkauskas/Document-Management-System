import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";

import AdminHomePageGroupsComponent from "./AdminHomePageGroupsComponent";

class AdminHomePageGroupsContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: ""
    };
  }

  getUsername = () => {
    axios
      .get("http://localhost:8081/api/user/loggedUsername")
      .then(response => {
        this.setState({ username: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };
  componentDidMount() {
    this.getUsername();
  }

  render() {
    return <AdminHomePageGroupsComponent username={this.state.username} />;
  }
}

export default withRouter(AdminHomePageGroupsContainer);
