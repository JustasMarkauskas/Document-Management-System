import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
<<<<<<< HEAD
import user from "../../../User/User";
import UserHomePageDFAComponents from "./UserHomePageDFAComponents";
import LogInUserContainer from "../../../LogInPage/LogInUserContainer";

class UserHomePageDFAContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: ""
=======

import UserHomePageDFAComponents from "./UserHomePageDFAComponents";


class UserHomePageDFAContainer extends React.Component {
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
      return <UserHomePageDFAComponents username={this.state.username} />;
    } else {
      return <LogInUserContainer />;
    }
  }
}

export default withRouter(UserHomePageDFAContainer);
=======

  render (){
    return(
      <UserHomePageDFAComponents username={this.state.username}/>
    );
  }


}


export default withRouter (UserHomePageDFAContainer);
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
