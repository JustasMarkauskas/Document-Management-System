import React from "react";
import axios from "axios";
import GroupReviewPageComponent from "./GroupReviewPageComponent";

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
      .get("http://localhost:8081/api/group/" + this.props.groupName)
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

  handleCommentChange = event => {
    this.setState({ comment: event.target.value });
  };

  closeModal = this.props.onHide;
  updateGroups = this.props.updateGroups;

  onOKClick = event => {
    event.preventDefault();
    axios
      .put(
        "http://localhost:8081/api/group/update-comment/" + this.state.group.id,
        {
          comment: this.state.comment,
          id: "not updated"
        }
      )
      .then(() => {
        this.closeModal();
        this.updateGroups();
      })
      .catch(error => {
        console.log(error);
      });
  };

  onCancelClick = () => {
    this.closeModal();
    this.updateGroups();
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
        onOKClick={this.onOKClick}
        onHide={this.props.onHide}
        onCancelClick={this.onCancelClick}
        updateGroup={this.getGroup}
      />
    );
  }
}

export default GroupReviewPageContainer;
