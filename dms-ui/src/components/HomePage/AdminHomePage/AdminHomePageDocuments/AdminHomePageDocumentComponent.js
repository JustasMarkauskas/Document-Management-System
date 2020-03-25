import React from "react";
import { Modal } from "react-bootstrap";
import DocumentTypeReviewContainer from "./DocumentTypeReviewPage/DocumentTypeReviewContainer";
import { store } from "react-notifications-component";

class AdminHomePageDocumentComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypeModal: false
    };
  }

  successDocTypeUpdateNotification = name =>
    store.addNotification({
      title: "Success!",
      message: name + " comment updated successfully",
      type: "success",
      insert: "top",
      container: "top-center",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  handleShowDocTypeModal = () => {
    this.setState({ docTypeModal: true });
  };

  handleCloseDocTypeModal = () => {
    this.setState({ docTypeModal: false });
  };

  handleCloseAndUpdateDocTypeModal = name => {
    this.props.updateDocTypes();
    this.successDocTypeUpdateNotification(name);
    this.setState({ docTypeModal: false });
  };

  render() {
    return (
      <tr id={"documentNr" + this.props.rowNr}>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.documentName}</td>
        <td className="comment-width">{this.props.comment}</td>
        <td className="text-right">
          <button
            className="action-btn btn-color"
            onClick={this.handleShowDocTypeModal}
          >
            <i className="fas fa-cog"></i>
          </button>
        </td>
        <Modal
          show={this.state.docTypeModal}
          onHide={this.handleCloseDocTypeModal}
        >
          <Modal.Header closeButton
		className="modals-header">
            <Modal.Title>Document type info</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <DocumentTypeReviewContainer
              onHide={this.handleCloseDocTypeModal}
              comment={this.props.comment}
              docType={this.props.documentName}
              onCloseModalAfterSubmit={name =>
                this.handleCloseAndUpdateDocTypeModal(name)
              }
            />
          </Modal.Body>
        </Modal>
      </tr>
    );
  }
}

export default AdminHomePageDocumentComponent;
