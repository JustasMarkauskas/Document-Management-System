import React from "react";
import SubmittedDocReviewContainer from "./SubmittedDocumentReviewPage/SubmittedDocReviewContainer";
import { Modal } from "react-bootstrap";

class UserHomePageDocumentComponent extends React.Component {
  constructor(props) {
    super(props);
    this.handleShowModal = this.handleShowModal.bind(this);
    this.handleCloseModal = this.handleCloseModal.bind(this);
    this.handleCloseModalAfterSubmit = this.handleCloseModalAfterSubmit.bind(
      this
    );
    this.state = {
      show: false
    };
  }

  handleCloseModal() {
    this.setState({ show: false });
  }

  handleCloseModalAfterSubmit() {
    this.setState({ show: false });
  }

  handleShowModal() {
    this.setState({ show: true });
  }
  handleActionClick = event => {
    event.preventDefault();
    this.handleShowModal();
  };

  render() {
    return (
      <tr
        className={
          this.props.status === "SAVED"
            ? "table-warning"
            : this.props.status === "SUBMITTED"
            ? "table-primary"
            : "table-secondary"
        }
      >
        <Modal show={this.state.show} onHide={this.handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>Document info</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <SubmittedDocReviewContainer
              onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit}
              onHide={this.handleCloseModal}
              docId={this.props.id}
            />
          </Modal.Body>
        </Modal>
        <th scope="row">{this.props.rowNr}</th>
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
      </tr>
    );
  }
}

export default UserHomePageDocumentComponent;
