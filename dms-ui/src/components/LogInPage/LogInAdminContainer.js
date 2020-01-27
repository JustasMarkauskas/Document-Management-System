import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import LogInAdminComponent from "./LogInAdminComponent";


class LogInAdminContainer extends React.Component {
  constructor(props) {
    super(props);
      this.state = {
        Admin: {
        adminName:"",
        adminPassword:""
        }
      };
  }

  handleAdminNameChange = event =>{
      var adminNameValue = event.target.value;
      this.setState(prevState =>{
          let Admin = Object.assign({}, prevState.Admin);
          Admin.adminName = adminNameValue;
          return{Admin};
      });
  }

  handleAdminPasswordChange = event =>{
    var adminPasswordValue = event.target.value;
    this.setState(prevState =>{
        let Admin = Object.assign({}, prevState.Admin);
        Admin.adminPassword = adminPasswordValue;
        return{Admin};
    });
  }

        //   handleUserButton = event => {
        //     event.preventDefault();
        //     this.props.history.push("/");
        //   };

  handleAdminLogIn = event => {
    event.preventDefault();
        // axios     
        //   .post("http://localhost:8081/api/institutions/", {
        //     adminName: this.state.Admin.adminName,
        //     adminPassword: this.state.Admin.adminPassword        
        //   })
        //   .then(response => {
        //     console.log(response);
        //   })
        //   .catch(error => {
        //     console.log(error);
        //   });


    this.setState({
      Admin: {
        adminName:"",
        adminPassword:""
      }
    });
  };

  render (){
    return(
      <LogInAdminComponent
        handleAdminNameChange={this.handleAdminNameChange}
        handleAdminPasswordChange={this.handleAdminPasswordChange}
        handleAdminLogIn={this.handleAdminLogIn}
      />
    );
  }
}
export default withRouter (LogInAdminContainer);