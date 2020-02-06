import React from "react";
import axios from "axios";
import user from "../../../User/User";
import AdminHomePageGroupsComponent from "./AdminHomePageGroupsComponent";
import LogInAdminContainer from "../../../LogInPage/LogInAdminContainer";

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
    if (user.loggedIn) {
      return <AdminHomePageGroupsComponent username={this.state.username} />;
    } else {
      return <LogInAdminContainer />;
    }
  }
}

export default AdminHomePageGroupsContainer;
