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
      <div className="container">
        <div className="row">
          <div className="col-lg-12 shadow-sm p-3 mb-5 bg-light rounded text-center">
            <h1>Welcome, {this.state.username}</h1>
          </div>
          <div className="col-lg-12">
            <div className="row">
              <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded">
                <UserHomePageProfileNavContainer />
              </div>
              <div className="col-lg-10 shadow-sm p-3 mb-5 bg-light rounded">
                <div className="col-12">
                  <UserHomePageProfileContainer />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default UserHomePageProfileMainPage;
