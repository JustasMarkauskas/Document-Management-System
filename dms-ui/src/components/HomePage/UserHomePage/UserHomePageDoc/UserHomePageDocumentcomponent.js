import React from "react";
import SubmittedDocReviewContainer from "./SubmittedDocumentReviewPage/SubmittedDocReviewContainer";
import SavedDocReviewContainer from "./SavedDocumentReviewPage/SavedDocReviewContainer";
import { Modal } from "react-bootstrap";

class UserHomePageDocumentComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showSubmitModal: false,
      showSaveModal: false
    };
  }

  handleSubmittedModalClose = () => {
    this.setState({ showSubmitModal: false });
  };

  handleShowSubmitModal = () => {
    this.setState({ showSubmitModal: true });
  };

  handleSaveModalClose = () => {
    this.setState({ showSaveModal: false });
  };

  handleShowSaveModal = () => {
    this.setState({ showSaveModal: true });
  };

  handleActionClick = event => {
    event.preventDefault();
    if (this.props.status === "SAVED") {
      this.handleShowSaveModal();
    } else this.handleShowSubmitModal();
  };

  render() {
    return (
      <tr
        id={"userDocumentNr" + this.props.rowNr}
        className={
          this.props.status === "SAVED"
            ? "table-warning"
            : this.props.status === "SUBMITTED"
            ? "table-primary"
            : this.props.status === "APPROVED"
            ? "table-success"
            : "table-secondary"
        }
      >
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.title}</td>
        <td>{this.props.docType}</td>
        <td>{this.props.status}</td>
        <td>{this.props.submissionDate}</td>
        <td>{this.props.reviewDate}</td>
        <td>
          <button className="btn btn-primary" onClick={this.handleActionClick}>
            <i className="fas fa-cog"></i>
          </button>
        </td>

        <Modal
          show={this.state.showSubmitModal}
          onHide={this.handleSubmittedModalClose}
        >
          <Modal.Header closeButton>
            <Modal.Title>Submitted document info</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <SubmittedDocReviewContainer
              onHide={this.handleSubmittedModalClose}
              docId={this.props.id}
            />
          </Modal.Body>
        </Modal>
        <Modal
          show={this.state.showSaveModal}
          onHide={this.handleSaveModalClose}
        >
          <Modal.Header closeButton>
            <Modal.Title>Saved document info</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <SavedDocReviewContainer
              onHide={this.handleSaveModalClose}
              docId={this.props.id}
              updateDocuments={this.props.updateDocuments}
              userDocTypes={this.props.userDocTypes}
            />
          </Modal.Body>
        </Modal>
      </tr>
    );
  }
}

export default UserHomePageDocumentComponent;
