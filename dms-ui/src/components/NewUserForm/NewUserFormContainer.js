import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import NewUserFormComponent from "./NewUserFormComponent";

class NewUserFormContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      user: {
        username: "",
        firstName: "",
        lastName: "",
        comment: "",
        password: "",
        confirmPassword: ""        
      }
    };
  }

  handleUsernameChange = event => {
    var usernameValue = event.target.value;
    this.setState(prevState => {
      let user = Object.assign({}, prevState.user);
      user.username = usernameValue;
      return { user };
    });
  };

  handleNameChange = event => {
    var firstNameValue = event.target.value;
    this.setState(prevState => {
      let user = Object.assign({}, prevState.user);
      user.firstName = firstNameValue;
      return { user };
    });
  };
  handleSurnameChange = event => {
    var lastNameValue = event.target.value;
    this.setState(prevState => {
      let user = Object.assign({}, prevState.user);
      user.lastName = lastNameValue;
      return { user };
    });
  };

  handleCommentChange = event => {
    var commentValue = event.target.value;
    this.setState(prevState => {
      let user = Object.assign({}, prevState.user);
      user.comment = commentValue;
      return { user };
    });
  };

  handlePasswordChange = event => {
    var passwordValue = event.target.value;
    this.setState(prevState => {
      let user = Object.assign({}, prevState.user);
      user.password = passwordValue;
      return { user };
    });
  };

  handleCancel = event => {
    event.preventDefault();
    this.props.history.push("/adminhomepage-users");
  };

  handleSubmit = event => {
    event.preventDefault();
    axios      
      .post("http://localhost:8081/api/user/", {
        username: this.state.user.username,
        fiestName: this.state.user.firstName,
        lastName: this.state.user.lastName,
        comment: this.state.user.comment,
        password: this.state.user.password,   

      })
      .then(response => {
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });

    this.setState({
      user: {
        username: "",
        firstName: "",
        lastName: "",
        comment: "",
        password: "",
        passwordConfirm: ""
      }
    });

    this.props.history.push("/adminhomepage-users");
  };

  render() {
    return (
      <NewUserFormComponent
        handleUsernameChange={this.handleUsernameChange}
        handleFirstNameChange={this.handleFirstNameChange}
        handleLastNameChange={this.handleLastNameChange}
        handleCommentChange={this.handleCommentChange}
        handlePasswordChange={this.handlePasswordChange}       
        handleSubmit={this.handleSubmit}
        handleCancel={this.handleCancel}
        username={this.state.user.username}
        firstName={this.state.user.firstName}
        lastName={this.state.user.lastName}
        comment={this.state.user.comment}
        password={this.state.user.password}        
      />
    );
  }
}

export default withRouter(NewUserFormContainer);