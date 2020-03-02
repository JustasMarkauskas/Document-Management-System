import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDocumentForApprovalComponent from "./UserHomePageDocumentForApprovalComponent";
import qs from "qs";
import serverUrl from "../../../URL/ServerUrl";

class UserHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userDocTypesForApproval: [],
      documents: [],
      username: "",
      inputDocumentTitle: "" //paieskai skirtas
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
          this.setState({ userDocTypesForApproval: response.data });

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

        <table className="table" id="userDFATable">
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
      </div>
    );
  }
}

export default withRouter(UserHomePageDocumentContainer);
