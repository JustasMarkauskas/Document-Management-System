import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDocumentComponent from "./UserHomePageDocumentcomponent";
import NewDocumentFormComponent from "../../../NewDocumentForm/NewDocumentFormComponent";
import { Modal } from "react-bootstrap";

class UserHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showModal: false,
      userDocTypes: [],
      documents: [],
      inputDocumentTitle: "" //paieskai skirtas
    };
  }

  getDocuments = () => {
    axios
      .get("http://localhost:8081/api/user/loggedUsername")
      .then(response => {
        let username = response.data;
        axios
          .get("http://localhost:8081/api/document/" + username)
          .then(response => {
            this.setState({ documents: response.data });
            axios
              .get(
                "http://localhost:8081/api/user/user-doctypes-for-creation/" +
                  username
              )
              .then(response => {
                this.setState({ userDocTypes: response.data });
              });
          })
          .catch(error => {
            console.log(error);
          });
      });
  };

  componentDidMount() {
    this.getDocuments();
  }

  handleModalClose = () => {
    this.setState({ showModal: false });
  };

  handleCloseModalAfterSubmit = () => {
    this.setState({ showModal: false });
  };

  handleShowModal = () => {
    this.setState({ showModal: true });
  };

  handleAddNewDocumentButton = () => {
    this.handleShowModal();
  };

  render() {
    const documentInfo = this.state.documents.map((document, index) => (
      <UserHomePageDocumentComponent
        key={index}
        rowNr={index + 1}
        id={document.id}
        title={document.title}
        docType={document.docType}
        status={document.status}
        submissionDate={document.submissionDate}
        reviewDate={document.reviewDate}
        updateDocuments={this.getDocuments}
        userDocTypes={this.state.userDocTypes}
      />
    ));

    return (
      <div className="container">
        <div className="row ">
          <button
            onClick={this.handleAddNewDocumentButton}
            type="button"
            className="btn btn-primary col-lg-2 mb-2"
            id="userAddNewDocumentButton"
          >
            Create New Document
          </button>
          <div className="input-group mb-3 col-lg-5">
            <input
              onChange={this.handleSearchChange}
              type="text"
              className="form-control"
              placeholder="Document name"
              aria-label="document"
              aria-describedby="button-addon2"
              id="userSearchDocumentInput"
            ></input>
            <div className="input-group-append">
              <button
                className="btn btn-primary"
                type="button"
                id="userDocumentSearchButton"
                onClick={this.handleSearchButton}
              >
                Search
              </button>
            </div>
          </div>
          <button
            onClick={this.handleDownloadDocumentButton}
            type="button"
            className="btn btn-primary col-lg-2 mb-2"
            id="downloadDocumentsButton"
          >
            Download
          </button>
        </div>

        <table className="table">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Title</th>
              <th scope="col">Type</th>
              <th scope="col">Status</th>
              <th scope="col">Submission date</th>
              <th scope="col">Review date</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>{documentInfo}</tbody>
        </table>
        <Modal show={this.state.showModal} onHide={this.handleModalClose}>
          <Modal.Header closeButton>
            <Modal.Title>Create New Document</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <NewDocumentFormComponent
              onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit}
              onHide={this.handleModalClose}
              //docId={this.props.id}
            />
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default withRouter(UserHomePageDocumentContainer);
