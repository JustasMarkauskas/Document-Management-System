import React from "react";
import axios from "axios";
import GroupReviewPageComponent from "./GroupReviewPageComponent";
import { withRouter } from "react-router-dom";

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
      .get(
        "http://localhost:8081/api/group/" + this.props.match.params.groupName
      )
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
        this.props.history.push("/adminhomepage-groups");
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
        onOKClick={this.onOKClick}
        onCancelClick={this.onCancelClick}
        updateGroup={this.getGroup}
      />
    );
  }
}

export default withRouter(GroupReviewPageContainer);
