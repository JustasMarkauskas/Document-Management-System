import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import SumbittedDocReviewComponent from "./SumbittedDocReviewComponent";

class SubmittedDocReviewContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      document: []
      //   username: ""
    };
  }

  getDocument = () => {
    let docId = this.props.location.state.documentId;

    axios
      .get("http://localhost:8081/api/user/loggedUsername")
      .then(response => {
        let username = response.data;
        axios
          //    .get("http://localhost:8081/api/document/898/" + username)
          .get("http://localhost:8081/api/document/" + docId + "/" + username)
          .then(response => {
            this.setState({ document: response.data });
          })
          .catch(error => {
            console.log(error);
          });
      });
  };

  componentDidMount() {
    this.getDocument();
  }

  render() {
    return (
      <SumbittedDocReviewComponent
        id={this.state.document.id}
        docType={this.state.document.docType}
        title={this.state.document.title}
        description={this.state.document.description}
        submissionDate={this.state.document.submissionDate}
        reviewDate={this.state.document.reviewDate}
        status={this.state.document.status}
        documentReceiver={this.state.document.documentReceiver}
        rejectionReason={this.state.document.rejectionReason}
      />
    );
  }
}

export default withRouter(SubmittedDocReviewContainer);
