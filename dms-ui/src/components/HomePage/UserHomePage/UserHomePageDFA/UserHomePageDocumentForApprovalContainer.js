import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDocumentForApprovalComponent from "./UserHomePageDocumentForApprovalComponent";
import NewDocumentFormComponent from "../../../NewDocumentForm/NewDocumentFormComponent";
import { Modal } from "react-bootstrap";
import qs from "qs";

class UserHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      //showModal: false,
      userDocTypesForApproval: [],
      documents: [],
      username: "",
      inputDocumentTitle: "" //paieskai skirtas
    };
  }

  getDocuments = () => {
    axios
      .get("http://localhost:8081/api/user/loggedUsername")
      .then(response => {
        let username = response.data;
        this.setState({ username: response.data });
        axios
          .get(
            "http://localhost:8081/api/user/user-doctypes-for-approval/" +
              username
          )
          .then(response => {
            let docTypesFA = response.data;
            this.setState({ userDocTypesForApproval: response.data });

            axios
              .get(
                "http://localhost:8081/api/document/documents-for-approval",
                {
                  params: {
                    documentForApprovalNames: docTypesFA
                  },
                  paramsSerializer: params => {
                    return qs.stringify(params, { indices: false });
                  }
                }
              )
              .then(response => {
                this.setState({ documents: response.data });
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

  // handleModalClose = () => {
  //   this.setState({ showModal: false });
  // };

  // handleCloseModalAfterSubmit = () => {
  //   this.setState({ showModal: false });
  //   this.getDocuments();
  // };

  // handleShowModal = () => {
  //   this.setState({ showModal: true });
  // };

  // handleAddNewDocumentButton = () => {
  //   this.handleShowModal();
  // };

  render() {
    const documentInfo = this.state.documents.map((document, index) => (
      <UserHomePageDocumentForApprovalComponent
        key={index}
        rowNr={index + 1}
        author={document.author}
        id={document.id}
        title={document.title}
        docType={document.docType}
        status={document.status}
        submissionDate={document.submissionDate}
        reviewDate={document.reviewDate}
        updateDocuments={this.getDocuments}
        userDocTypesForApproval={this.state.userDocTypesForApproval}
      />
    ));

    return (
      <div className="container">
        <div className="row ">
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
        </div>

        <table className="table">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Author</th>
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
        {/* <Modal show={this.state.showModal} onHide={this.handleModalClose}>
          <Modal.Header closeButton>
            <Modal.Title>Create New Document</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <NewDocumentFormComponent
              onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit}
              onHide={this.handleModalClose}
              author={this.state.username}
              userDocTypes={this.state.userDocTypes}
            />
          </Modal.Body>
        </Modal> */}
      </div>
    );
  }
}

export default withRouter(UserHomePageDocumentContainer);
