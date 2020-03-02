import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageComponent from "./AdminHomePageComponents";
import serverUrl from "./../../URL/ServerUrl";

class AdminHomePageContainer extends React.Component {
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
    return <AdminHomePageComponent username={this.state.username} />;
  }
}

export default withRouter(AdminHomePageContainer);
