import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageDocumentComponent from "./AdminHomePageDocumentComponent";

class AdminHomePageDocumentContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      documents: []
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
