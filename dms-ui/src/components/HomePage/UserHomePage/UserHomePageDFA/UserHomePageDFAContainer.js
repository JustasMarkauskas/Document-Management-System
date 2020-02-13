import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import UserHomePageDFAComponents from "./UserHomePageDFAComponents";

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
    return <UserHomePageDFAComponents username={this.state.username} />;
  }
}

export default withRouter(UserHomePageDFAContainer);
