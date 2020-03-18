import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import { Modal } from "react-bootstrap";
import AdminHomePageDocumentComponent from "./AdminHomePageDocumentComponent";
import NewDocTypeFormComponent from "../../../NewDocTypeForm/NewDocTypeFormComponent";
import serverUrl from "../../../URL/ServerUrl";
import ReactPaginate from "react-paginate";
import { store } from "react-notifications-component";

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

  successDocTypeNotification = () =>
    store.addNotification({
      title: "Success!",
      message: "Document type created successfully",
      type: "success",
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  handleCloseModal = () => {
    this.setState({ show: false });
  };

  handleCloseModalAfterSubmit = () => {
    this.setState({ show: false }, this.getDocuments());
    this.successDocTypeNotification();
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

  handleSearchChange = event => {
    this.setState({ documentName: event.target.value });
  };

  setElementsForSearchButton = () => {
    this.setState({ currentPage: 0 });
    let elements = this.state.documents.slice(0, this.state.perPage);
    this.setState({ elements: elements, offset: 0 });
  };

  handleSearchButton = event => {
    event.preventDefault();
    if (this.state.documentName.length < 1) {
      this.getDocuments();
    } else {
      axios
        .get(serverUrl + "api/doctype/containing/" + this.state.documentName)
        .then(response => {
          this.setState(
            {
              documents: response.data,
              documentName: "",
              pageCount: Math.ceil(response.data.length / this.state.perPage)
            },
            () => {
              this.setElementsForSearchButton();
            }
          );
        })
        .catch(error => {
          console.log(error);
        });
      document.getElementById("adminDocumentSearchInput").value = "";
    }
  };

  checkIfEnter = event => {
    var code = event.keyCode || event.which;
    if (code === 13) {
      this.handleSearchButton(event);
    }
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
        updateDocTypes={this.getDocuments}
      />
    ));

    return (
      <div className="container">
        <div className="row d-flex">
          <div className="col-lg-4 col-md-12 mb-3">
            <button
              onClick={this.handleShowModal}
              type="button"
              className="main-btn btn-color col-lg-11 col-md-12"
              id="adminAddNewDocumentButton"
            >
              Add new document type
            </button>
          </div>
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
          <div className="input-group mb-3 col-lg-4 col-md-12 ml-auto">
            <input
              onChange={this.handleSearchChange}
              onKeyPress={this.checkIfEnter}
              type="text"
              className="form-control"
              placeholder="Document type"
              aria-label="Document"
              aria-describedby="button-addon2"
              id="adminDocumentSearchInput"
            ></input>
            <div className="input-group-append">
              <button
                onClick={this.handleSearchButton}
                className="search-btn btn-color"
                type="button"
                id="adminDocumentSearchButton"
              >
                Search
              </button>
            </div>
          </div>
        </div>
        <div className="table-responsive">
          <table className="table table-striped">
            <thead className="table-head">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Document type name</th>
                <th scope="col">Comment</th>
                <th className="text-right" scope="col">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody>{documentInfo}</tbody>
          </table>
        </div>
        <div id="docTypesTablePagination">{paginationElement}</div>
      </div>
    );
  }
}

export default withRouter(AdminHomePageDocumentContainer);
