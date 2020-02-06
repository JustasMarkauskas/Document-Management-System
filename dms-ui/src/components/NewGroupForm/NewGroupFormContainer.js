import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import NewGroupFormComponent from "./NewGroupFormComponent";

class NewGroupFormContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = { groupName: "", comment: "" };
  }

  handleCancel = event => {
    event.preventDefault();
    this.props.history.push("/adminhomepage-groups");
  };

  handleGroupNameChange = event => {
    this.setState({ groupName: event.target.value });
  };

  handleCommentChange = event => {
    this.setState({ comment: event.target.value });
  };

  handleSubmit = event => {
    event.preventDefault();
    axios
      .post("http://localhost:8081/api/role/", {
        id: this.state.groupName,
        comment: this.state.comment
      })
      .then(() => {
        this.props.history.push("/adminhomepage-groups");
      })
      .catch(error => {
        console.log(error);
      });

    this.setState({
      groupName: ""
    });
  };

  render() {
    return (
      <NewGroupFormComponent
        handleSubmit={this.handleSubmit}
        handleCancel={this.handleCancel}
        handleCommentChange={this.handleCommentChange}
        handleGroupNameChange={this.handleGroupNameChange}
      />
    );
  }
}

export default withRouter(NewGroupFormContainer);
