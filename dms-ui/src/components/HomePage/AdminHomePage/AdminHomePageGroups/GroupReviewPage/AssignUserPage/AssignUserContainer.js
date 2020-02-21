import React from "react";
import axios from "axios";
import AssignUserComponent from "./AssignUserComponent";

class AssignUserContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: []
    };
  }

  getUsers = () => {
    axios
      .get("http://localhost:8081/api/user/")
      .then(response => {
        this.setState({ users: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getUsers();
    console.log(this.state.users + "s");
  }

  //   handleCommentChange = event => {
  //     this.setState({ comment: event.target.value });
  //   };

  //   onOKClick = event => {
  //     event.preventDefault();
  //     axios
  //       .put(
  //         "http://localhost:8081/api/group/update-comment/" + this.state.group.id,
  //         {
  //           comment: this.state.comment,
  //           id: "not updated"
  //         }
  //       )
  //       .then(() => {})
  //       .catch(error => {
  //         console.log(error);
  //       });
  //   };

  render() {
    return (
      <AssignUserComponent
      // groupName={this.state.group.id}
      // comment={this.state.group.comment}
      // groupSize={this.state.group.groupSize}
      // groupUsers={this.state.group.groupUsers}
      // docTypesForCreation={this.state.group.docTypesForCreation}
      // docTypesForApproval={this.state.group.docTypesForApproval}
      // handleCommentChange={this.handleCommentChange}
      // onOKClick={this.onOKClick}
      // onHide={this.props.onHide}
      />
    );
  }
}

export default AssignUserContainer;
