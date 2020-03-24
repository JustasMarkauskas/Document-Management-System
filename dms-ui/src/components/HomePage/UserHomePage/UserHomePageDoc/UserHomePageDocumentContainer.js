import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDocumentComponent from "./UserHomePageDocumentcomponent";
import NewDocumentFormComponent from "../../../NewDocumentForm/NewDocumentFormComponent";
import { Modal } from "react-bootstrap";
import serverUrl from "../../../URL/ServerUrl";
import ReactPaginate from "react-paginate";
import { store } from "react-notifications-component";

class UserHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showModal: false,
      userDocTypes: [],
      documents: [],
      username: "",
      documentStatus: "ALL",
      inputDocumentTitle: "",
      documentTitleForSearch: "",
      offset: 0,
      elements: [],
      perPage: 10,
      currentPage: 0,
      pageCount: 0,
      totalDocuments: 0,
      status: "",
      pageClickInfo: "DOC" //DOC, SEARCH, FILTER
    };
  }

  getDocuments = () => {
    axios.get(serverUrl + "api/user/loggedUsername").then(response => {
      let username = response.data;
      this.setState({ username: response.data });
      axios
        .get(serverUrl + "api/document/page/" + username, {
          params: {
            page: this.state.currentPage,
            size: this.state.perPage
          }
        })
        .then(response => {
          this.setState({
            pageCount: Math.ceil(
              response.data[0].totalDocuments / this.state.perPage
            ),
            elements: response.data,
            pageClickInfo: "DOC"
          });

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

  handlePageClick = data => {
    const selectedPage = data.selected;
    const offset = selectedPage * this.state.perPage;
    this.setState({ currentPage: selectedPage, offset: offset }, () => {
      this.state.pageClickInfo === "DOC"
        ? this.setElementsForCurrentPage()
        : this.state.pageClickInfo === "SEARCH"
        ? this.setElementsForSearchButton()
        : this.setElementsForStatus(this.state.status);
    });
  };

  setElementsForCurrentPage = () => {
    axios
      .get(serverUrl + "api/document/page/" + this.state.username, {
        params: {
          page: this.state.currentPage,
          size: this.state.perPage
        }
      })
      .then(response => {
        let totalDocuments = 0;
        if (response.data.length > 0) {
          totalDocuments = response.data[0].totalDocuments;
        }

        this.setState({
          pageCount: Math.ceil(totalDocuments / this.state.perPage),
          elements: response.data,
          pageClickInfo: "DOC"
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  getDocumentsByStatus = status => {
    if (status === "ALL") {
      this.setState({ currentPage: 0 }, () =>
        axios
          .get(serverUrl + "api/document/page/" + this.state.username, {
            params: {
              page: this.state.currentPage,
              size: this.state.perPage
            }
          })
          .then(response => {
            let totalDocuments = 0;
            if (response.data.length > 0) {
              totalDocuments = response.data[0].totalDocuments;
            }

            this.setState({
              pageCount: Math.ceil(totalDocuments / this.state.perPage),
              elements: response.data,
              pageClickInfo: "DOC"
            });
          })
          .catch(error => {
            console.log(error);
          })
      );
    } else {
      this.setState({ currentPage: 0, status: status }, () => {
        this.setElementsForStatus(status);
      });
    }
  };

  setElementsForStatus = status => {
    axios
      .get(
        serverUrl +
          "api/document/page/status/" +
          status +
          "/" +
          this.state.username,
        {
          params: {
            page: this.state.currentPage,
            size: this.state.perPage
          }
        }
      )
      .then(response => {
        let totalDocuments = 0;
        if (response.data.length > 0) {
          totalDocuments = response.data[0].totalDocuments;
        }
        this.setState({
          pageCount: Math.ceil(totalDocuments / this.state.perPage),
          elements: response.data,
          pageClickInfo: "FILTER"
        });
      })

      .catch(error => {
        console.log(error);
      });
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

  successDocumentNotification = button =>
    store.addNotification({
      title: "Success!",
      message: "Document " + button + " successfully",
      type: "success",
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  handleModalClose = () => {
    this.setState({ showModal: false });
  };

  handleCloseModalAfterSubmit = button => {
    setTimeout(() => {
      this.getDocuments();
      this.successDocumentNotification(button);
      this.setState({ showModal: false });
    }, 500);
  };

  handleShowModal = () => {
    this.setState({ showModal: true });
  };

  handleAddNewDocumentButton = () => {
    this.handleShowModal();
  };

  handleSearchChange = event => {
    this.setState({ inputDocumentTitle: event.target.value });
  };

  handleSearchButton = event => {
    event.preventDefault();
    if (this.state.inputDocumentTitle.length < 1) {
      this.setState({ pageClickInfo: "SEARCH", currentPage: 0 }, () => {
        this.getDocuments();
      });
    } else {
      this.setState(
        {
          inputDocumentTitle: "",
          documentTitleForSearch: this.state.inputDocumentTitle,
          currentPage: 0
        },
        () => {
          this.setElementsForSearchButton();
        }
      );

      document.getElementById("userSearchDocumentInput").value = "";
    }
  };

  setElementsForSearchButton = () => {
    axios
      .get(
        serverUrl +
          "api/document/page/containing/" +
          this.state.username +
          "/" +
          this.state.documentTitleForSearch,
        {
          params: {
            page: this.state.currentPage,
            size: this.state.perPage
          }
        }
      )
      .then(response => {
        let totalDocuments = 0;
        if (response.data.length > 0) {
          totalDocuments = response.data[0].totalDocuments;
        }
        this.setState({
          pageCount: Math.ceil(totalDocuments / this.state.perPage),
          elements: response.data,
          offset: 0,
          pageClickInfo: "SEARCH"
        });
      })
      .catch(error => {
        console.log(error);
      });
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
      <UserHomePageDocumentComponent
        key={index}
        rowNr={index + 1 + this.state.currentPage * this.state.perPage}
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
      <div>
        <div className="row d-flex">
          <div className="col-lg-9 col-md-12 mb-3">
            <button
              onClick={this.handleAddNewDocumentButton}
              type="button"
              className="main-btn btn-color mr-2"
              id="userAddNewDocumentButton"
            >
              Add new document
            </button>
            <button
              onClick={this.handleDownloadButton}
              type="button"
              className="main-btn btn-color "
              id="downloadDocumentsButton"
            >
              Download
            </button>
          </div>

          <div className="input-group mb-3 col-lg-3 col-md-12 ml-auto">
            <input
              onChange={this.handleSearchChange}
              onKeyPress={this.checkIfEnter}
              type="text"
              className="form-control"
              placeholder="Document title"
              aria-label="document"
              aria-describedby="button-addon2"
              id="userSearchDocumentInput"
            ></input>
            <div className="input-group-append">
              <button
                className="search-btn btn-color"
                type="button"
                id="userDocumentSearchButton"
                onClick={this.handleSearchButton}
              >
                Search
              </button>
            </div>
          </div>
        </div>
        <div className="text-right">
          <div className="btn-group" role="group" id="DocumentsFilterId">
            <button
              type="button"
              className="btn btn-dark"
              onClick={() => this.getDocumentsByStatus("ALL")}
            >
              All
            </button>
            <button
              type="button"
              className="btn btn-dark"
              onClick={() => this.getDocumentsByStatus("SAVED")}
            >
              Saved
            </button>
            <button
              type="button"
              className="btn btn-dark"
              onClick={() => this.getDocumentsByStatus("SUBMITTED")}
            >
              Submitted
            </button>
            <button
              type="button"
              className="btn btn-dark"
              onClick={() => this.getDocumentsByStatus("REJECTED")}
            >
              Rejected
            </button>
            <button
              type="button"
              className="btn btn-dark"
              onClick={() => this.getDocumentsByStatus("APPROVED")}
            >
              Approved
            </button>
          </div>
        </div>
        <div className="table-responsive">
          <table
            className="table table-striped table-dark"
            id="userDocumentsTable"
          >
            <thead className="table-head ">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Title</th>
                <th scope="col">Type</th>
                <th scope="col">Status</th>
                <th scope="col">Submission</th>
                <th scope="col">Review</th>
                <th className="text-right" scope="col">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody>{documentInfo}</tbody>
          </table>
        </div>
        <Modal show={this.state.showModal} onHide={this.handleModalClose}>
          <Modal.Header closeButton>
            <Modal.Title>Create New Document</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <NewDocumentFormComponent
              onCloseModalAfterSubmit={button =>
                this.handleCloseModalAfterSubmit(button)
              }
              onHide={this.handleModalClose}
              author={this.state.username}
              userDocTypes={this.state.userDocTypes}
            />
          </Modal.Body>
        </Modal>
        <div id="documentsTablePagination">{paginationElement}</div>
      </div>
    );
  }
}

export default withRouter(UserHomePageDocumentContainer);
