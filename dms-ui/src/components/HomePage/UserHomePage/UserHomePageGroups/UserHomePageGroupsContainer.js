import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import user from "../../../User/User";
import UserHomePageGroupsComponents from "./UserHomePageGroupsComponents";
import LogInUserContainer from "../../../LogInPage/LogInUserContainer";

class UserHomePageGroupsContainer extends React.Component {
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
      return <UserHomePageGroupsComponents username={this.state.username} />;
    } else {
      return <LogInUserContainer />;
    }
  }
}

export default withRouter(UserHomePageGroupsContainer);
