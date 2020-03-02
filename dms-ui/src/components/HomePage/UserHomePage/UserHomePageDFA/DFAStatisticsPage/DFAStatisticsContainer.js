import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import serverUrl from "../../../../URL/ServerUrl";
import DFAStatisticsComponent from "./DFAStatisticsComponent";

class DFAStatisticsContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userDocTypesForApproval: []
    };
  }

  getUserDFA = () => {
    axios
      .get(
        serverUrl +
          "api/user/user-doctypes-for-approval/" +
          this.props.match.params.username
      )
      .then(response => {
        this.setState({ userDocTypesForApproval: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getUserDFA();
  }

  render() {
    return (
      <DFAStatisticsComponent
        userDocTypesForApproval={this.state.userDocTypesForApproval}
      />
    );
  }
}

export default withRouter(DFAStatisticsContainer);
