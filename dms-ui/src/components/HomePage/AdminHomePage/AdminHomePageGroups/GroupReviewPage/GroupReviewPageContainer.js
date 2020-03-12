import React from "react";
import axios from "axios";
import GroupReviewPageComponent from "./GroupReviewPageComponent";
import { withRouter } from "react-router-dom";
import serverUrl from "../../../../URL/ServerUrl";
import { store } from "react-notifications-component";

class GroupReviewPageContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      group: [],
      comment: ""
    };
  }

  getGroup = () => {
    axios
      .get(serverUrl + "api/group/" + this.props.match.params.groupName)
      .then(response => {
        this.setState({ group: response.data, comment: response.data.comment });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getGroup();
  }

  handleButtonValidation = () => {
    var formIsValid = true;
    if (this.state.comment.length > 50) {
      formIsValid = false;
    }
    return formIsValid;
  };

  handleCommentChange = event => {
    if (event.target.value.length > 50) {
      document
        .getElementById("groupCommentId")
        .setAttribute("class", "form-control is-invalid");
    } else {
      document
        .getElementById("groupCommentId")
        .setAttribute("class", "form-control");
    }
    this.setState({ comment: event.target.value });
  };

  successGroupUpdateNotification = () =>
    store.addNotification({
      title: "Success!",
      message: this.state.group.id + " comment updated successfully",
      type: "success",
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  onSaveClick = event => {
    event.preventDefault();
    axios
      .put(serverUrl + "api/group/update-comment/" + this.state.group.id, {
        comment: this.state.comment,
        id: "not updated"
      })
      .then(() => {
        this.props.history.push("/adminhomepage-groups");
        this.successGroupUpdateNotification();
      })
      .catch(error => {
        console.log(error);
      });
  };

  onCancelClick = event => {
    event.preventDefault();
    this.props.history.push("/adminhomepage-groups");
  };

  render() {
    return (
      <GroupReviewPageComponent
        groupName={this.state.group.id}
        comment={this.state.group.comment}
        groupSize={this.state.group.groupSize}
        groupUsers={this.state.group.groupUsers}
        docTypesForCreation={this.state.group.docTypesForCreation}
        docTypesForApproval={this.state.group.docTypesForApproval}
        handleCommentChange={this.handleCommentChange}
        handleButtonValidation={this.handleButtonValidation}
        onSaveClick={this.onSaveClick}
        onCancelClick={this.onCancelClick}
        updateGroup={this.getGroup}
        group={this.state.group}
      />
    );
  }
}

export default withRouter(GroupReviewPageContainer);
