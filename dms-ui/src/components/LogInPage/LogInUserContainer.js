import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import LogInUserComponent from "./LogInUserComponents";

class LoginUserContainer extends React.Component{

    constructor(props) {
        super(props);
          this.state = {
            User: {
            userName:"",
            userPassword:""
            }
          };
      };

    handleUserNameChange = event =>{
        var userNameValue = event.target.value;
        this.setState(prevState =>{
            let User = Object.assign({}, prevState.User);
            User.userName = userNameValue;
            return{User};
        });
    }

    handleUserPasswordChange = event =>{
        var userPasswordValue = event.target.value;
        this.setState(prevState =>{
            let User = Object.assign({}, prevState.User);
            User.userPassword = userPasswordValue;
            return{User};
        });
    }

    handleUserLogIn = event => {
      event.preventDefault();
        // axios     
        //   .post("http://localhost:8081/api/institutions/", {
        //     userName: this.state.User.userName,
        //     userPassword: this.state.User.userPassword        
        //   })
        //   .then(response => {
        //     console.log(response);
        //   })
        //   .catch(error => {
        //     console.log(error);
        //   });


      this.setState({
        User: {
        userName:"",
        userPassword:""
        }
      });
    };

    render(){
      return(
        <LogInUserComponent
          handleUserNameChange={this.handleUserNameChange}
          handleUserPasswordChange={this.handleUserPasswordChange}
          handleUserLogIn={this.handleUserLogIn}
        />
      );
    }
}
export default withRouter (LoginUserContainer);