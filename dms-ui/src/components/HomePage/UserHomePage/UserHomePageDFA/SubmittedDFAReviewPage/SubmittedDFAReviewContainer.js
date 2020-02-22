import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import SubmittedDFAReviewComponent from "./SumbittedDFAReviewComponent";

class SubmittedDFAReviewContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      document: []
    };
  }

  getDocument = () => {
    axios
      .get("http://localhost:8081/api/document/get/" + this.props.docId + "/")
      .then(response => {
        this.setState({ document: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getDocument();
  }

  render() {
    return (
      <SubmittedDFAReviewComponent
        id={this.state.document.id}
        author={this.state.document.author}
        docType={this.state.document.docType}
        title={this.state.document.title}
        description={this.state.document.description}
        submissionDate={this.state.document.submissionDate}
        status={this.state.document.status}
        onHide={this.props.onHide}
        updateDocuments={this.props.updateDocuments}
      />
    );
  }
}

export default withRouter(SubmittedDFAReviewContainer);
