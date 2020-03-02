import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import { Modal } from "react-bootstrap";
import AdminHomePageDocumentComponent from "./AdminHomePageDocumentComponent";
import NewDocTypeFormComponent from "../../../NewDocTypeForm/NewDocTypeFormComponent";
import serverUrl from "../../../URL/ServerUrl";

class AdminHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);

    this.handleShowModal = this.handleShowModal.bind(this);
    this.handleCloseModal = this.handleCloseModal.bind(this);
    this.handleCloseModalAfterSubmit = this.handleCloseModalAfterSubmit.bind(
      this
    );

    this.state = {
      show: false,
      documents: [],
      documentName: ""
    };
  }

  refresh() {
    this.getDocuments();
    window.location.reload();
  }

  handleCloseModal() {
    this.setState({ show: false });
  }

  handleCloseModalAfterSubmit() {
    this.refresh();
    this.setState({ show: false });
  }

  handleShowModal() {
    this.setState({ show: true });
  }

  getDocuments = () => {
    axios
      .get(serverUrl + "api/doctype/names-comments")
      .then(response => {
        this.setState({ documents: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };
  componentDidMount() {
    this.getDocuments();
  }

  handleActionClick = event => {
    event.preventDefault();
    this.props.history.push("/document-info"); //navigacija teisinga padaryti
  };

  handleSearchChange = event => {
    this.setState({ documentName: event.target.value });
  };

  handleSearchButton = event => {
    event.preventDefault();
    axios
      .get(serverUrl + "api/doctype/" + this.state.documentName)
      .then(response => {
        this.setState({ documents: [response.data] });
      })
      .catch(error => {
        console.log(error);
      });
    document.getElementById("adminDocumentSearchInput").value = "";
  };

  render() {
    const documentInfo = this.state.documents.map((document, index) => (
      <AdminHomePageDocumentComponent
        key={index}
        rowNr={index + 1}
        documentName={document.id}
        comment={document.comment}
        handleActionClick={this.handleActionClick}
      />
    ));

    return (
      <div className="container">
        <div className="row">
          <button
            onClick={this.handleShowModal}
            type="button"
            className="btn btn-primary col-lg-4 mb-2"
            id="adminAddNewDocumentButton"
          >
            Add new Document Type
          </button>
          <Modal show={this.state.show} onHide={this.handleCloseModal}>
            <Modal.Header closeButton>
              <Modal.Title>Create New Document Type</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              {" "}
              <NewDocTypeFormComponent
                onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit}
                onHide={this.handleCloseModal}
              />
            </Modal.Body>
          </Modal>
          <div className="input-group mb-3 col-lg-5">
            <input
              onChange={this.handleSearchChange}
              type="text"
              className="form-control"
              placeholder="Document"
              aria-label="Document"
              aria-describedby="button-addon2"
              id="adminDocumentSearchInput"
            ></input>
            <div className="input-group-append">
              <button
                onClick={this.handleSearchButton}
                className="btn btn-primary"
                type="button"
                id="adminDocumentSearchButton"
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
              <th scope="col">Document Name</th>
              <th scope="col">Comment</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>{documentInfo}</tbody>
        </table>
      </div>
    );
  }
}

export default withRouter(AdminHomePageDocumentContainer);
