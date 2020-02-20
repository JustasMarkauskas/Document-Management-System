import React from "react";
import SubmittedDocReviewContainer from "./../UserHomePageDoc/SubmittedDocumentReviewPage/SubmittedDocReviewContainer";
import { Modal } from "react-bootstrap";
import ReviewedDFAReviewContainer from "./ReviewedDFAReviewPage/ReviewedDFAReviewContainer";

class UserHomePageDocumentForApprovalComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showSubmitModal: false,
      showReviewModal: false
    };
  }

  handleSubmittedModalClose = () => {
    this.setState({ showSubmitModal: false });
  };

  handleShowSubmitModal = () => {
    this.setState({ showSubmitModal: true });
  };

  handleReviewModalClose = () => {
    this.setState({ showReviewModal: false });
  };

  handleShowReviewModal = () => {
    this.setState({ showReviewModal: true });
  };

  handleActionClick = event => {
    event.preventDefault();
    if (this.props.status === "SUBMITTED") {
      this.handleShowSubmitModal();
    } else this.handleShowReviewModal();
  };

  render() {
    return (
      <tr
        className={
          this.props.status === "SUBMITTED"
            ? "table-primary"
            : this.props.status === "APPROVED"
            ? "table-secondary"
            : "table-error"
        }
      >
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.author}</td>
        <td>{this.props.title}</td>
        <td>{this.props.docType}</td>
        <td>{this.props.status}</td>
        <td>{this.props.submissionDate}</td>
        <td>{this.props.reviewDate}</td>
        <td>
          <button
            className="btn btn-primary"
            onClick={this.handleActionClick}
            id={"userDocumentNr" + this.props.rowNr}
          >
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
          show={this.state.showReviewModal}
          onHide={this.handleReviewModalClose}
        >
          <Modal.Header closeButton>
            <Modal.Title>Reviewed document info</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <ReviewedDFAReviewContainer
              onHide={this.handleReviewModalClose}
              docId={this.props.id}
              updateDocuments={this.props.updateDocuments}
            />
          </Modal.Body>
        </Modal>
      </tr>
    );
  }
}

export default UserHomePageDocumentForApprovalComponent;
