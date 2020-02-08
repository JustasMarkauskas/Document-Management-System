import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import user from "../../User/User";
import AdminHomePageComponent from "./AdminHomePageComponents";
import LogInUserContainer from "../../LogInPage/LogInUserContainer";

class AdminHomePageContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: ""
    };
  }
  getUsername = () => {
    axios
      .get("http://akademijait.vtmc.lt:8180/dms/api/user/loggedUsername")
      //  .get("http://localhost:8081/api/user/loggedUsername")
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
      return <AdminHomePageComponent username={this.state.username} />;
    } else {
      return <LogInUserContainer />;
    }
  }
}

export default withRouter(AdminHomePageContainer);
