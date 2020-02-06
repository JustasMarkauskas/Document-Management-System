import React from "react";
<<<<<<< HEAD
import axios from "axios";
import { withRouter } from "react-router-dom";
import user from "../../../User/User";
import LogInAdminContainer from "../../../LogInPage/LogInAdminContainer";

import AdminHomePageDocumentsComponent from "./AdminHomePageDocumentsComponent";

class AdminHomePageDocumentsContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: ""
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

  render() {
    if (user.loggedIn) {
      return <AdminHomePageDocumentsComponent username={this.state.username} />;
    } else {
      return <LogInAdminContainer />;
    }
  }
}

export default withRouter(AdminHomePageDocumentsContainer);
=======
// import axios from "axios";
import { withRouter } from "react-router-dom";

import AdminHomePageDocumentsComponent from "./AdminHomePageDocumentsComponent";


class AdminHomePageDocumentsContainer extends React.Component {



  render (){
    return(
      <AdminHomePageDocumentsComponent
        
      />
    );
  }


}


export default withRouter (AdminHomePageDocumentsContainer);
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
