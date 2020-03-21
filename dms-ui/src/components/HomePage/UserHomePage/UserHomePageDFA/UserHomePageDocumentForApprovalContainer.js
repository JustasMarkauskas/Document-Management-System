import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDocumentForApprovalComponent from "./UserHomePageDocumentForApprovalComponent";
import qs from "qs";
import serverUrl from "../../../URL/ServerUrl";
import ReactPaginate from "react-paginate";

class UserHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userDocTypesForApproval: [],
      documents: [],
      username: "",
      inputDocumentTitle: "",
      offset: 0,
      elements: [],
      perPage: 10,
      currentPage: 0,
      pageCount: 0
    };
  }

  getDocuments = () => {
    axios.get(serverUrl + "api/user/loggedUsername").then(response => {
      let username = response.data;
      this.setState({ username: response.data });
      axios
        .get(serverUrl + "api/user/user-doctypes-for-approval/" + username)
        .then(response => {
          let docTypesFA = response.data;
          this.setState({
            userDocTypesForApproval: response.data
          });

          axios
            .get(serverUrl + "api/document/documents-for-approval", {
              params: {
                documentForApprovalNames: docTypesFA
              },
              paramsSerializer: params => {
                return qs.stringify(params, { indices: false });
              }
            })
            .then(response => {
              this.setState(
                {
                  documents: response.data,
                  pageCount: Math.ceil(
                    response.data.length / this.state.perPage
                  )
                },
                () => this.setElementsForCurrentPage()
              );
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

  setFirstElementsForFilterPage = () => {
    this.setState({ currentPage: 0 });
    let elements = this.state.documents.slice(0, this.state.perPage);

    this.setState({ elements: elements });
  };

  getDFAByStatus = status => {
    if (status === "ALL") {
      axios
        .get(serverUrl + "api/document/documents-for-approval", {
          params: {
            documentForApprovalNames: this.state.userDocTypesForApproval
          },
          paramsSerializer: params => {
            return qs.stringify(params, { indices: false });
          }
        })
        .then(response => {
          this.setState(
            {
              documents: response.data,
              pageCount: Math.ceil(response.data.length / this.state.perPage)
            },
            () => this.setFirstElementsForFilterPage()
          );
        });
    } else {
      axios
        .get(serverUrl + "api/document/documents-for-approval/" + status, {
          params: {
            documentForApprovalNames: this.state.userDocTypesForApproval
          },
          paramsSerializer: params => {
            return qs.stringify(params, { indices: false });
          }
        })
        .then(response => {
          this.setState(
            {
              documents: response.data,
              pageCount: Math.ceil(response.data.length / this.state.perPage)
            },
            () => this.setFirstElementsForFilterPage()
          );
        });
    }
  };

  handleStatisticsClick = event => {
    event.preventDefault();
    this.props.history.push("/dms/dfa-statistics/" + this.state.username);
  };

  handleSearchChange = event => {
    this.setState({ inputDocumentTitle: event.target.value });
  };

  setElementsForSearchButton = () => {
    this.setState({ currentPage: 0 });
    let elements = this.state.documents.slice(0, this.state.perPage);
    this.setState({ elements: elements, offset: 0 });
  };

  handleSearchButton = event => {
    event.preventDefault();
    if (this.state.inputDocumentTitle.length < 1) {
      this.getDocuments();
    } else {
      axios
        .get(
          serverUrl +
            "api/user/user-doctypes-for-approval/" +
            this.state.username
        )
        .then(response => {
          let docTypesFA = response.data;
          this.setState({
            userDocTypesForApproval: response.data
          });

          axios
            .get(
              serverUrl +
                "api/document/documents-for-approval/containing/" +
                this.state.inputDocumentTitle,
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
              this.setState(
                {
                  documents: response.data,
                  inputDocumentTitle: "",
                  pageCount: Math.ceil(
                    response.data.length / this.state.perPage
                  )
                },
                () => this.setElementsForSearchButton()
              );
            });
        })
        .catch(error => {
          console.log(error);
        });
      document.getElementById("userSearchDocumentInput").value = "";
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
      <UserHomePageDocumentForApprovalComponent
        key={index}
        rowNr={index + 1 + this.state.currentPage * this.state.perPage}
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
      <div>
        <div className="row d-flex">
          <div className="col-lg-3 col-md-12 mb-3">
            <button
              className="main-btn btn-color col-lg-7 col-md-12 "
              type="button"
              id="statisticsButton"
              onClick={this.handleStatisticsClick}
            >
              Statistics
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
          <div className="btn-group" role="group" id="DFAFilterId">
            <button
              type="button"
              className="btn btn-dark"
              onClick={() => this.getDFAByStatus("ALL")}
            >
              All
            </button>
            <button
              type="button"
              className="btn btn-dark"
              onClick={() => this.getDFAByStatus("SUBMITTED")}
            >
              Submitted
            </button>
            <button
              type="button"
              className="btn btn-dark"
              onClick={() => this.getDFAByStatus("REJECTED")}
            >
              Rejected
            </button>
            <button
              type="button"
              className="btn btn-dark"
              onClick={() => this.getDFAByStatus("APPROVED")}
            >
              Approved
            </button>
          </div>
        </div>
        <div className="table-responsive">
          <table className="table table-striped table-dark" id="userDFATable">
            <thead className="table-head">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Author</th>
                <th scope="col">Title</th>
                <th scope="col">Type</th>
                <th scope="col">Status</th>
                <th scope="col">Submission </th>
                <th scope="col">Review </th>
                <th className="text-right" scope="col">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody>{documentInfo}</tbody>
          </table>
        </div>
        <div id="DFATablePagination">{paginationElement}</div>
      </div>
    );
  }
}

export default withRouter(UserHomePageDocumentContainer);
