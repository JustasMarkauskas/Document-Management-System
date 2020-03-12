import React from "react";
import { Modal } from "react-bootstrap";
import PasswordChange from "../../../PasswordChange/PasswodChange";
import { store } from "react-notifications-component";

class UserHomePageProfileComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      changePasswordModal: false
    };
  }

  successPasswordChangeNotification = () =>
    store.addNotification({
      title: "Success!",
      message: "Password changed successfully",
      type: "success",
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  handleShowPasswordModal = () => {
    this.setState({ changePasswordModal: true });
  };

  handleClosePasswordModal = () => {
    this.setState({ changePasswordModal: false });
  };

  handleClosePasswordModalAfterSubmit = () => {
    this.setState({ changePasswordModal: false });
    this.successPasswordChangeNotification();
  };

  render() {
    return (
      <div className="my-2">
        <div className="row">
          <div className="col-lg-6 col-sm-12 my-2 pr-1">
            <form id="profileReviewPageId">
              <div className="form-group">
                <label htmlFor="profileUsername">Username</label>
                <input
                  id="profileUsername"
                  disabled
                  type="text"
                  className="form-control"
                  placeholder={this.props.username}
                />
              </div>
              <div className="form-group">
                <label htmlFor="profileFirstName">First name</label>
                <input
                  id="profileFirstName"
                  disabled
                  type="text"
                  className="form-control"
                  placeholder={this.props.firstName}
                />
              </div>
              <div className="form-group">
                <label htmlFor="profileLastName">Last name</label>
                <input
                  id="profileLastName"
                  disabled
                  type="text"
                  className="form-control"
                  placeholder={this.props.lastName}
                />
              </div>
              <button
                onClick={this.handleShowPasswordModal}
                type="button"
                className="btn btn-primary "
              >
                Change password
              </button>
            </form>
          </div>
          <div className="col-lg-6 col-sm-12 my-2 px-1">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Groups</h5>
                <div className="card-text scroll">
                  <ul className="list-group mb-2">
                    {this.props.userGroups.map((group, index) => {
                      return (
                        <div key={index}>
                          <li className="list-group-item">{group}</li>
                        </div>
                      );
                    })}
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <Modal
          show={this.state.changePasswordModal}
          onHide={this.handleClosePasswordModal}
        >
          <Modal.Header closeButton>
            <Modal.Title>Change password</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <PasswordChange
              onCloseModal={this.handleClosePasswordModal}
              onCloseModalAfterSubmit={this.handleClosePasswordModalAfterSubmit}
              username={this.props.username}
            />
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default UserHomePageProfileComponent;
