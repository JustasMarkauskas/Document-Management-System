import React from "react";
import { Modal } from "react-bootstrap";
import AssignUserContainer from "./AssignUserPage/AssignUserContainer";
import AssignDFAContainer from "./AssignDFAPage/AssignDFAContainer";
import AssignDFCContainer from "./AssignDFCPage/AssignDFCContainer";

class GroupReviewPageComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      assignUserModal: false,
      assignDFAModal: false,
      assignDFCModal: false
    };
  }

  handleShowAssignUserModal = () => {
    this.setState({ assignUserModal: true });
  };

  handleCloseAssignUserModal = () => {
    this.setState({ assignUserModal: false });
  };

  handleCloseAssignUserModalAndUpdate = () => {
    this.setState({ assignUserModal: false });
    this.updateGroup();
  };

  handleShowAssignDFAModal = () => {
    this.setState({ assignDFAModal: true });
  };

  handleCloseAssignDFAModal = () => {
    this.setState({ assignDFAModal: false });
  };

  updateGroup = this.props.updateGroup;

  handleCloseAssignDFAModalAndUpdate = () => {
    this.setState({ assignDFAModal: false });
    this.updateGroup();
  };

  handleShowAssignDFCModal = () => {
    this.setState({ assignDFCModal: true });
  };

  handleCloseAssignDFCModal = () => {
    this.setState({ assignDFCModal: false });
  };

  handleCloseAssignDFCModalAndUpdate = () => {
    this.setState({ assignDFCModal: false });
    this.updateGroup();
  };

  //test = Array.from(this.props.groupUsers);

  render() {
    // const groupUsers = this.props.groupUsers.map((user, index) => (
    //   <div key={index}>
    //     <li className="list-group-item">{user}</li>
    //   </div>
    // ));

    return (
      <div className="container my-3">
        <div className="row">
          <div className="col-4">
            <button
              id="assignUserButton"
              onClick={this.handleShowAssignUserModal}
              type="button"
              className="btn btn-primary m-2"
            >
              Assign member
            </button>
            <button
              id="assignDFAButton"
              onClick={this.handleShowAssignDFAModal}
              type="button"
              className="btn btn-primary m-2"
            >
              Assign document types for approval
            </button>
            <button
              id="assignDFCButton"
              onClick={this.handleShowAssignDFCModal}
              type="button"
              className="btn btn-primary m-2"
            >
              Assign document types for creation
            </button>

            <form id="groupReviewPageId">
              <div className="form-group">
                <input
                  disabled
                  type="text"
                  className="form-control"
                  placeholder={this.props.groupName}
                />
              </div>
              <div className="form-group">
                <input
                  type="text"
                  defaultValue={this.props.comment}
                  onChange={this.props.handleCommentChange}
                  className="form-control"
                  placeholder="Comment"
                />
              </div>
              <div className="container mt-2">
                <div className="row">
                  <button
                    onClick={this.props.onOKClick}
                    type="button"
                    className="btn btn-primary mr-2"
                  >
                    OK
                  </button>
                  <button
                    onClick={this.props.onCancelClick}
                    type="button"
                    className="btn btn-primary "
                  >
                    Cancel
                  </button>
                </div>
              </div>
            </form>
          </div>
          <div className="col-7">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Users</h5>
                <div className="card-text scroll">
                  <ul className="list-group mb-2">
                    {/* {this.props.group.map((user, index) => {
                      return (
                        <div key={index}>
                          <li className="list-group-item">{user}</li>
                        </div>
                      );
                    })} */}
                    {/* {this.props.groupUsers.map((user, index) => (
                      <div key={index}>
                        <li className="list-group-item">{user}</li>
                      </div>
                    ))} */}

                    {/* <li className="list-group-item">Cras justo odio</li>
                    <li className="list-group-item">Dapibus ac facilisis in</li>
                    <li className="list-group-item">Morbi leo risus</li>
                    <li className="list-group-item">Porta ac consectetur ac</li>
                    <li className="list-group-item">Vestibulum at eros</li>
                    <li className="list-group-item ">Cras justo odio</li>
                    <li className="list-group-item">Dapibus ac facilisis in</li>
                    <li className="list-group-item">Morbi leo risus</li>
                    <li className="list-group-item">Porta ac consectetur ac</li>
                    <li className="list-group-item">Vestibulum at eros</li>
                    <li className="list-group-item ">Cras justo odio</li>
                    <li className="list-group-item">Dapibus ac facilisis in</li>
                    <li className="list-group-item">Morbi leo risus</li>
                    <li className="list-group-item">Porta ac consectetur ac</li>
                    <li className="list-group-item">Vestibulum at eros</li> */}
                  </ul>
                </div>
                <a href="#" className="btn btn-primary">
                  Go somewhere
                </a>
              </div>
            </div>
          </div>
        </div>
        <Modal
          show={this.state.assignUserModal}
          onHide={this.handleCloseAssignUserModalAndUpdate}
        >
          <Modal.Header closeButton>
            <Modal.Title>Assign user</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <AssignUserContainer
              onHide={this.handleCloseAssignUserModalAndUpdate}
              groupUsers={this.props.groupUsers}
              groupName={this.props.groupName}
            />
          </Modal.Body>
        </Modal>
        <Modal
          show={this.state.assignDFAModal}
          onHide={this.handleCloseAssignDFAModalAndUpdate}
        >
          <Modal.Header closeButton>
            <Modal.Title>Assign document types for approval</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <AssignDFAContainer
              onHide={this.handleCloseAssignDFAModalAndUpdate}
              groupUsers={this.props.groupUsers}
              groupName={this.props.groupName}
              docTypesForApproval={this.props.docTypesForApproval}
              updateGroup={this.props.updateGroup}
            />
          </Modal.Body>
        </Modal>
        <Modal
          show={this.state.assignDFCModal}
          onHide={this.handleCloseAssignDFCModalAndUpdate}
        >
          <Modal.Header closeButton>
            <Modal.Title>Assign document types for creation</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <AssignDFCContainer
              onHide={this.handleCloseAssignDFCModalAndUpdate}
              groupUsers={this.props.groupUsers}
              groupName={this.props.groupName}
              docTypesForCreation={this.props.docTypesForCreation}
              updateGroup={this.props.updateGroup}
            />
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default GroupReviewPageComponent;
