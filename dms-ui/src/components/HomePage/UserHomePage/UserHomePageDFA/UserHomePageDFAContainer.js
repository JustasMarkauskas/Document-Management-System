import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import user from "../../../User/User";
import UserHomePageDFAComponents from "./UserHomePageDFAComponents";
import LogInUserContainer from "../../../LogInPage/LogInUserContainer";

class UserHomePageDFAContainer extends React.Component {
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
      return <UserHomePageDFAComponents username={this.state.username} />;
    } else {
      return <LogInUserContainer />;
    }
  }
}

export default withRouter(UserHomePageDFAContainer);
