import React from "react";
import { Modal } from "react-bootstrap";
import GroupReviewPageContainer from "./GroupReviewPage/GroupReviewPageContainer";

class AdminHomePageGroupComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showGroupReviewModal: false
    };
  }

  handleShowGroupReviewModal = () => {
    this.setState({ showGroupReviewModal: true });
  };

  handleCloseGroupReviewModal = () => {
    this.setState({ showGroupReviewModal: false });
  };

  render() {
    return (
      <tr>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.groupName}</td>
        <td>{this.props.groupSize}</td>
        <td>{this.props.comment}</td>
        <td>
          <button
            className="btn btn-primary"
            onClick={this.handleShowGroupReviewModal}
            id={"groupNr" + this.props.rowNr}
          >
            <i className="fas fa-cog"></i>
          </button>
        </td>
        <Modal
          show={this.state.showGroupReviewModal}
          onHide={this.handleCloseGroupReviewModal}
        >
          <Modal.Header closeButton>
            <Modal.Title>Group review</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <GroupReviewPageContainer
              onHide={this.handleCloseGroupReviewModal}
              groupName={this.props.groupName}
              updateGroups={this.props.updateGroups}
            />
          </Modal.Body>
        </Modal>
      </tr>
    );
  }
}

export default AdminHomePageGroupComponent;
