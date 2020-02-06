import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
<<<<<<< HEAD
import user from "../../../User/User";
import UserHomePageGroupsComponents from "./UserHomePageGroupsComponents";
import LogInUserContainer from "../../../LogInPage/LogInUserContainer";

class UserHomePageGroupsContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: ""
=======

import UserHomePageGroupsComponents from "./UserHomePageGroupsComponents";


class UserHomePageGroupsContainer extends React.Component {
  constructor(props){
    super(props);
    this.state ={
      username:""
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
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

<<<<<<< HEAD
  render() {
    if (user.loggedIn) {
      return <UserHomePageGroupsComponents username={this.state.username} />;
    } else {
      return <LogInUserContainer />;
    }
  }
}

export default withRouter(UserHomePageGroupsContainer);
=======

  render (){
    return(
      <UserHomePageGroupsComponents username={this.state.username}/>
    );
  }


}


export default withRouter (UserHomePageGroupsContainer);
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
