import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
<<<<<<< HEAD
import user from "../../../User/User";
import UserHomepageComponents from "./UserHomePageComponents";
import LogInUserContainer from "../../../LogInPage/LogInUserContainer";

class UserHomePageContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: ""
=======

import UserHomepageComponents from "./UserHomePageComponents";


class UserHomePageContainer extends React.Component {
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
      return <UserHomepageComponents username={this.state.username} />;
    } else {
      return <LogInUserContainer />;
    }
  }
}

export default withRouter(UserHomePageContainer);
=======

  render (){
    return(
      <UserHomepageComponents username={this.state.username}/>
    );
  }


}


export default withRouter (UserHomePageContainer);
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
