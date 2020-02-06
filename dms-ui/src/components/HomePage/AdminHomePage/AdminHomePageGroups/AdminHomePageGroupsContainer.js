import React from "react";
import axios from "axios";
<<<<<<< HEAD
import user from "../../../User/User";
import AdminHomePageGroupsComponent from "./AdminHomePageGroupsComponent";
import LogInAdminContainer from "../../../LogInPage/LogInAdminContainer";
=======
import { withRouter } from "react-router-dom";

import AdminHomePageGroupsComponent from "./AdminHomePageGroupsComponent";
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e

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
<<<<<<< HEAD
    if (user.loggedIn) {
      return <AdminHomePageGroupsComponent username={this.state.username} />;
    } else {
      return <LogInAdminContainer />;
    }
  }
}

export default AdminHomePageGroupsContainer;
=======
    return <AdminHomePageGroupsComponent username={this.state.username} />;
  }
}

export default withRouter(AdminHomePageGroupsContainer);
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
