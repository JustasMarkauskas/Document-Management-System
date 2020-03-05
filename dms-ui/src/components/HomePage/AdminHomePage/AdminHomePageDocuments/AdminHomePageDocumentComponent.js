import React from "react";
import { Modal } from "react-bootstrap";
import DocumentTypeReviewContainer from "./DocumentTypeReviewPage/DocumentTypeReviewContainer";

class AdminHomePageDocumentComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypeModal: false
    };
  }

  handleShowDocTypeModal = () => {
    this.setState({ docTypeModal: true });
  };

  handleCloseDocTypeModal = () => {
    this.setState({ docTypeModal: false });
  };

  handleCloseAndUpdateDocTypeModal = () => {
    this.setState({ docTypeModal: false });
    this.props.updateDocTypes();
  };

  render() {
    return (
      <tr id={"documentNr" + this.props.rowNr}>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.documentName}</td>
        <td>{this.props.comment}</td>
        <td>
          <button
            className="btn btn-primary"
            onClick={this.handleShowDocTypeModal}
          >
            <i className="fas fa-cog"></i>
          </button>
        </td>
        <Modal
          show={this.state.docTypeModal}
          onHide={this.handleCloseDocTypeModal}
        >
          <Modal.Header closeButton>
            <Modal.Title>Document type info</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <DocumentTypeReviewContainer
              onHide={this.handleCloseDocTypeModal}
              comment={this.props.comment}
              docType={this.props.documentName}
              onCloseAndUpdate={this.handleCloseAndUpdateDocTypeModal}
            />
          </Modal.Body>
        </Modal>
      </tr>
    );
  }
}

export default AdminHomePageDocumentComponent;
