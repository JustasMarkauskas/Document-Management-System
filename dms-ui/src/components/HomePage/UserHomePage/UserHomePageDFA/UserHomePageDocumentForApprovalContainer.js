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
        .get(serverUrl + "api/user/user-doctypes-for-approval/" + username)
        .then(response => {
          let docTypesFA = response.data;
          this.setState({
            userDocTypesForApproval: response.data
          });

          axios
            .get(serverUrl + "api/document/page/documents-for-approval", {
              params: {
                documentForApprovalNames: docTypesFA,
                page: this.state.currentPage,
                size: this.state.perPage
              },
              paramsSerializer: params => {
                return qs.stringify(params, { indices: false });
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
      .get(serverUrl + "api/document/page/documents-for-approval", {
        params: {
          documentForApprovalNames: this.state.userDocTypesForApproval,
          page: this.state.currentPage,
          size: this.state.perPage
        },
        paramsSerializer: params => {
          return qs.stringify(params, { indices: false });
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

  getDFAByStatus = status => {
    if (status === "ALL") {
      this.setState({ currentPage: 0 }, () =>
        axios
          .get(serverUrl + "api/document/page/documents-for-approval", {
            params: {
              page: this.state.currentPage,
              size: this.state.perPage,
              documentForApprovalNames: this.state.userDocTypesForApproval
            },
            paramsSerializer: params => {
              return qs.stringify(params, { indices: false });
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
      );
    } else {
      this.setState({ currentPage: 0, status: status }, () => {
        this.setElementsForStatus(status);
      });
    }
  };

  setElementsForStatus = status => {
    axios
      .get(serverUrl + "api/document/page/documents-for-approval/" + status, {
        params: {
          page: this.state.currentPage,
          size: this.state.perPage,
          documentForApprovalNames: this.state.userDocTypesForApproval
        },
        paramsSerializer: params => {
          return qs.stringify(params, { indices: false });
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
          pageClickInfo: "FILTER"
        });
      })

      .catch(error => {
        console.log(error);
      });
  };

  handleStatisticsClick = event => {
    event.preventDefault();
    this.props.history.push("/dfa-statistics/" + this.state.username);
  };

  handleSearchChange = event => {
    this.setState({ inputDocumentTitle: event.target.value });
  };

  setElementsForSearchButton = () => {
    axios
      .get(
        serverUrl +
          "api/document/page/documents-for-approval/containing/" +
          this.state.documentTitleForSearch,
        {
          params: {
            page: this.state.currentPage,
            size: this.state.perPage,
            documentForApprovalNames: this.state.userDocTypesForApproval
          },
          paramsSerializer: params => {
            return qs.stringify(params, { indices: false });
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
