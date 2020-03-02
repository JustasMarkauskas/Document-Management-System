import React from "react";
import axios from "axios";
import AdminHomePageGroupsComponent from "./AdminHomePageGroupsComponent";
import serverUrl from "../../../URL/ServerUrl";

class AdminHomePageGroupsContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: ""
    };
  }

  getUsername = () => {
    axios
      .get(serverUrl + "api/user/loggedUsername")
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

export default AdminHomePageGroupsContainer;
