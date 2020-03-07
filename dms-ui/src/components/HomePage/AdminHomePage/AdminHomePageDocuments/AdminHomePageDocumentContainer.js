import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import { Modal } from "react-bootstrap";
import AdminHomePageDocumentComponent from "./AdminHomePageDocumentComponent";
import NewDocTypeFormComponent from "../../../NewDocTypeForm/NewDocTypeFormComponent";
import serverUrl from "../../../URL/ServerUrl";
import ReactPaginate from "react-paginate";

class AdminHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      show: false,
      documents: [],
      documentName: "",
      offset: 0,
      elements: [],
      perPage: 10,
      currentPage: 0,
      pageCount: 0
    };
  }

  refresh() {
    this.getDocuments();
    window.location.reload();
  }

  handleCloseModal = () => {
    this.setState({ show: false });
  };

  handleCloseModalAfterSubmit = () => {
    this.refresh();
    this.setState({ show: false });
  };

  handleShowModal = () => {
    this.setState({ show: true });
  };

  componentDidMount() {
    this.getDocuments();
  }

  getDocuments = () => {
    axios
      .get(serverUrl + "api/doctype/names-comments")
      .then(response => {
        this.setState(
          {
            documents: response.data,
            pageCount: Math.ceil(response.data.length / this.state.perPage)
          },
          () => this.setElementsForCurrentPage()
        );
      })
      .catch(error => {
        console.log(error);
      });
  };

  handlePageClick = data => {
    const selectedPage = data.selected;
    const offset = selectedPage * this.state.perPage;
    this.setState({ currentPage: selectedPage, offset: offset }, () => {
      this.setElementsForCurrentPage();
    });
  };

  setElementsForCurrentPage = () => {
    let elements = this.state.documents.slice(
      this.state.offset,
      this.state.offset + this.state.perPage
    );

    this.setState({ elements: elements });
  };

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
        this.setState(
          {
            documents: [response.data],
            pageCount: Math.ceil(response.data.length / this.state.perPage)
          },
          () => {
            this.setElementsForCurrentPage();
          }
        );
      })
      .catch(error => {
        console.log(error);
      });
    document.getElementById("adminDocumentSearchInput").value = "";
  };

  render() {
    let paginationElement;
    if (this.state.pageCount > 1) {
      paginationElement = (
        <ReactPaginate
          previousLabel={"← Previous"}
          nextLabel={"Next →"}
          breakLabel={<span className="gap">...</span>}
          pageCount={this.state.pageCount}
          onPageChange={this.handlePageClick}
          forcePage={this.state.currentPage}
          breakClassName={"page-item"}
          breakLinkClassName={"page-link"}
          containerClassName={"pagination"}
          pageClassName={"page-item"}
          pageLinkClassName={"page-link"}
          previousClassName={"page-item"}
          previousLinkClassName={"page-link"}
          nextClassName={"page-item"}
          nextLinkClassName={"page-link"}
          activeClassName={"active"}
        />
      );
    }
    const documentInfo = this.state.elements.map((document, index) => (
      <AdminHomePageDocumentComponent
        key={index}
        rowNr={index + 1 + this.state.currentPage * this.state.perPage}
        documentName={document.id}
        comment={document.comment}
        handleActionClick={this.handleActionClick}
        updateDocTypes={this.getDocuments}
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
            Add new document type
          </button>
          <Modal show={this.state.show} onHide={this.handleCloseModal}>
            <Modal.Header closeButton>
              <Modal.Title>Create new document type</Modal.Title>
            </Modal.Header>
            <Modal.Body>
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
              <th scope="col">Document type name</th>
              <th scope="col">Comment</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>{documentInfo}</tbody>
        </table>
        {paginationElement}
      </div>
    );
  }
}

export default withRouter(AdminHomePageDocumentContainer);
