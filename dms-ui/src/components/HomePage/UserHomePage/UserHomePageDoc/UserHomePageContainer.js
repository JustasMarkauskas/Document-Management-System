import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import UserHomepageComponents from "./UserHomePageComponents";
import serverUrl from "../../../URL/ServerUrl";

class UserHomePageContainer extends React.Component {
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
    return <UserHomepageComponents username={this.state.username} />;
  }
}

export default withRouter(UserHomePageContainer);
