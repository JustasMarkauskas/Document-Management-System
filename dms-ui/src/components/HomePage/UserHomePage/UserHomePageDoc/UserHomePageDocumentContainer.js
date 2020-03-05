import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDocumentComponent from "./UserHomePageDocumentcomponent";
import NewDocumentFormComponent from "../../../NewDocumentForm/NewDocumentFormComponent";
import { Modal } from "react-bootstrap";
import serverUrl from "../../../URL/ServerUrl";

class UserHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showModal: false,
      userDocTypes: [],
      documents: [],
      username: "",
      documentStatus: "ALL",
      inputDocumentTitle: "" //paieskai skirtas
    };
  }

  getDocuments = () => {
    axios.get(serverUrl + "api/user/loggedUsername").then(response => {
      let username = response.data;
      this.setState({ username: response.data });
      axios
        .get(serverUrl + "api/document/" + username)
        .then(response => {
          this.setState({ documents: response.data });
          axios
            .get(serverUrl + "api/user/user-doctypes-for-creation/" + username)
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

  getDocumentsByStatus = status => {
    if (status === "ALL") {
      axios
        .get(serverUrl + "api/document/" + this.state.username)
        .then(response => {
          this.setState({ documents: response.data });
        })
        .catch(error => {
          console.log(error);
        });
    } else {
      axios
        .get(
          serverUrl +
            "api/document/status/" +
            status +
            "/" +
            this.state.username
        )
        .then(response => {
          this.setState({ documents: response.data });
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  handleDownloadButton = () => {
    axios
      .request({
        url:
          serverUrl + "api/file/download-files-csv-zip/" + this.state.username,
        method: "GET",
        responseType: "blob"
      })
      .then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", this.state.username + "_files.zip");
        document.body.appendChild(link);
        link.click();
        link.remove();
      });
  };

  handleModalClose = () => {
    this.setState({ showModal: false });
  };

  handleCloseModalAfterSubmit = () => {
    this.getDocuments();
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
      <div className="row ">
        <button
          onClick={this.handleAddNewDocumentButton}
          type="button"
          className="btn btn-primary col-lg-3 mb-2"
          id="userAddNewDocumentButton"
        >
          Add new document
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
          onClick={this.handleDownloadButton}
          type="button"
          className="btn btn-primary col-lg-2 mb-2"
          id="downloadDocumentsButton"
        >
          Download
        </button>

        <div className="btn-group" role="group">
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => this.getDocumentsByStatus("ALL")}
          >
            All
          </button>
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => this.getDocumentsByStatus("SAVED")}
          >
            Saved
          </button>
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => this.getDocumentsByStatus("SUBMITTED")}
          >
            Submitted
          </button>
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => this.getDocumentsByStatus("REJECTED")}
          >
            Rejected
          </button>
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => this.getDocumentsByStatus("APPROVED")}
          >
            Approved
          </button>
        </div>

        <table className="table" id="userDocumentsTable">
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
              author={this.state.username}
              userDocTypes={this.state.userDocTypes}
            />
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default withRouter(UserHomePageDocumentContainer);
