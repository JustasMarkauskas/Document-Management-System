import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageDocumentComponent from "./AdminHomePageDocumentComponent";

class AdminHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      documents: [],
      documentName: ""
    };
  }

  getDocuments = () => {
    axios
      .get("http://localhost:8081/api/document")
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

  handleAddDocumentButton = event => {
    event.preventDefault();
    this.props.history.push("/new-document"); //navigacija teisinga padaryti
  };

  handleSearchChange = event => {
    this.setState({ documentName: event.target.value });
  };

  handleSearchButton = event => {
    event.preventDefault();
    axios
      .get("http://localhost:8081/api/document/" + this.state.documentName)
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
        firstName={document.firstName}
        comment={document.comment}
        handleActionClick={this.handleActionClick}
      />
    ));

    return (
      <div className="container">
        <div className="row col-lg-12">
          <button
            onClick={this.handleAddDocumentButton}
            type="button"
            className="btn btn-primary col-lg-3 mb-2"
            id="adminAddNewDocumentButton"
          >
            Add new Document
          </button>
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
