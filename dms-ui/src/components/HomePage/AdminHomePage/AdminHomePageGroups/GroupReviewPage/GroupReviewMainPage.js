import React from "react";
import AdminHomePageGroupNavContainer from "../AdminHomePageGroupNavContainer";
import GroupReviewPageContainer from "./GroupReviewPageContainer";
import axios from "axios";
import serverUrl from "../../../../URL/ServerUrl";

class GroupReviewMainPage extends React.Component {
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
        <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center">
          <h1>Welcome, {this.state.username}</h1>
        </div>
        <div className="row">
          <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded mr-4">
            <AdminHomePageGroupNavContainer />
          </div>
          <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">
            <div className="col-12">
              <GroupReviewPageContainer />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default GroupReviewMainPage;
