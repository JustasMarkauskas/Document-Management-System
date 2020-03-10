import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import serverUrl from "../../../URL/ServerUrl";
import UserHomePageProfileComponent from "./UserHomePageProfileComponent";

class UserHomePageProfileContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      user: [],
      userGroups: []
    };
  }

  getUser = () => {
    axios.get(serverUrl + "api/user/loggedUsername").then(response => {
      let username = response.data;
      this.setState({ username: response.data });
      axios
        .get(serverUrl + "api/user/" + username)
        .then(response => {
          this.setState({
            user: response.data,
            userGroups: response.data.userGroups
          });
        })

        .catch(error => {
          console.log(error);
        });
    });
  };

  componentDidMount() {
    this.getUser();
  }

  render() {
    return (
      <UserHomePageProfileComponent
        username={this.state.user.username}
        firstName={this.state.user.firstName}
        lastName={this.state.user.lastName}
        userGroups={this.state.userGroups}
      />
    );
  }
}

export default withRouter(UserHomePageProfileContainer);
