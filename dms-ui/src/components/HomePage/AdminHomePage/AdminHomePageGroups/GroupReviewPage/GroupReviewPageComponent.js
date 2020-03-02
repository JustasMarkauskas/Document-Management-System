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
      assignDFCModal: false,
      groupUsers: [],
      docTypesForApproval: [],
      docTypesForCreation: []
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

  componentDidUpdate(prevProps) {
    if (this.props.groupUsers !== prevProps.groupUsers) {
      this.setState({
        groupUsers: this.props.groupUsers,
        docTypesForApproval: this.props.docTypesForApproval,
        docTypesForCreation: this.props.docTypesForCreation
      });
    }
  }

  render() {
    return (
      <div className="my-2">
        <div className="row">
          <div className="col-lg-3 col-sm-12 my-2 pr-1">
            <form id="groupReviewPageId">
              <div className="form-group">
                <label htmlFor="groupName">Group name</label>
                <input
                  id="groupName"
                  disabled
                  type="text"
                  className="form-control"
                  placeholder={this.props.groupName}
                />
              </div>
              <div className="form-group">
                <label htmlFor="groupCommentId">Comment</label>
                <input
                  id="groupCommentId"
                  type="text"
                  defaultValue={this.props.comment}
                  onChange={this.props.handleCommentChange}
                  className="form-control"
                  placeholder="Comment"
                />
                <div className="invalid-feedback text-info">
                  Must be 50 characters or less
                </div>
              </div>

              <div className="container mt-2">
                <div className="row">
                  <button
                    disabled={!this.props.handleButtonValidation()}
                    onClick={this.props.onSaveClick}
                    type="button"
                    className="btn btn-primary mr-2"
                  >
                    Save
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
          <div className="col-lg-3 col-sm-12 my-2 px-1">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Users</h5>
                <div className="card-text scroll">
                  <ul className="list-group mb-2">
                    {this.state.groupUsers.map((user, index) => {
                      return (
                        <div key={index}>
                          <li className="list-group-item">{user}</li>
                        </div>
                      );
                    })}
                  </ul>
                </div>
                <button
                  id="assignUserButton"
                  onClick={this.handleShowAssignUserModal}
                  type="button"
                  className="btn btn-info m-2"
                >
                  Assign
                </button>
              </div>
            </div>
          </div>
          <div className="col-lg-3 col-sm-12 my-2 px-1">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Document types for approval</h5>
                <div className="card-text scroll">
                  <ul className="list-group mb-2">
                    {this.state.docTypesForApproval.map((docType, index) => {
                      return (
                        <div key={index}>
                          <li className="list-group-item">{docType}</li>
                        </div>
                      );
                    })}
                  </ul>
                </div>
                <button
                  id="assignDFAButton"
                  onClick={this.handleShowAssignDFAModal}
                  type="button"
                  className="btn btn-info m-2"
                >
                  Assign
                </button>
              </div>
            </div>
          </div>
          <div className="col-lg-3 col-sm-12 my-2 px-1">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Document types for creation</h5>
                <div className="card-text scroll">
                  <ul className="list-group mb-2">
                    {this.state.docTypesForCreation.map((docType, index) => {
                      return (
                        <div key={index}>
                          <li className="list-group-item">{docType}</li>
                        </div>
                      );
                    })}
                  </ul>
                </div>
                <button
                  id="assignDFCButton"
                  onClick={this.handleShowAssignDFCModal}
                  type="button"
                  className="btn btn-info m-2"
                >
                  Assign
                </button>
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
