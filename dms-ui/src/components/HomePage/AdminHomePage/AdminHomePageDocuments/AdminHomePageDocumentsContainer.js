import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageDocumentsComponent from "./AdminHomePageDocumentsComponent";
import serverUrl from "../../../URL/ServerUrl";

class AdminHomePageDocumentsContainer extends React.Component {
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
    return <AdminHomePageDocumentsComponent username={this.state.username} />;
  }
}

export default withRouter(AdminHomePageDocumentsContainer);
