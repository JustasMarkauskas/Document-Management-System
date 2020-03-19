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
      <div className="no-scroll">
        <div className="row full-height">
          <div className="col-lg-2 p-0 nav-color">
            <AdminHomePageGroupNavContainer />
          </div>
          <div className="col-lg-10 p-3 main-color">
            <div className="m-4 text-center">
              <h1>Welcome, {this.state.username}</h1>
            </div>
            <div className="px-5">
              <GroupReviewPageContainer />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default GroupReviewMainPage;
