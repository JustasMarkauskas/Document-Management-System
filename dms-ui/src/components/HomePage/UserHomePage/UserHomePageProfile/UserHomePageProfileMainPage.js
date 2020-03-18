import React from "react";
import axios from "axios";
import serverUrl from "../../../URL/ServerUrl";
import UserHomePageProfileNavContainer from "./UserHomePageProfileNavContainer";
import UserHomePageProfileContainer from "./UserHomePageProfileContainer";

class UserHomePageProfileMainPage extends React.Component {
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
    return (
      <div className="no-scroll">
        <div className="row full-height">
          <div className="col-lg-2 p-0 nav-color">
            <UserHomePageProfileNavContainer />
          </div>
          <div className="col-lg-10 ">
            <div className="mb-5 text-center">
              <h1>Welcome, {this.state.username}</h1>
            </div>
            <div className="px-5">
              <UserHomePageProfileContainer />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default UserHomePageProfileMainPage;
