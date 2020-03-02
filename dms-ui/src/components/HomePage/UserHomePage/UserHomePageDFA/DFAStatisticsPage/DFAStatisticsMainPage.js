import React from "react";
import axios from "axios";
import serverUrl from "../../../../URL/ServerUrl";
import DFAStatisticsContainer from "./DFAStatisticsContainer";
import UserHomePageDFANavContainer from "../UserHomePageDFANavContainer";

class DFAStatisticsMainPage extends React.Component {
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
            <UserHomePageDFANavContainer />
          </div>
          <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">
            <div className="col-12">
              <DFAStatisticsContainer />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default DFAStatisticsMainPage;
