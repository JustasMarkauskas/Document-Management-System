import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import ReviewedDFAReviewComponent from "./ReviewedDFAReviewComponent";
import serverUrl from "../../../../URL/ServerUrl";

class ReviewedDFAReviewContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      document: []
    };
  }

  getDocument = () => {
    axios
      .get(serverUrl + "api/document/get/" + this.props.docId + "/")
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
      <ReviewedDFAReviewComponent
        id={this.state.document.id}
        author={this.state.document.author}
        docType={this.state.document.docType}
        title={this.state.document.title}
        description={this.state.document.description}
        submissionDate={this.state.document.submissionDate}
        reviewDate={this.state.document.reviewDate}
        status={this.state.document.status}
        documentReceiver={this.state.document.documentReceiver}
        rejectionReason={this.state.document.rejectionReason}
        onHide={this.props.onHide}
      />
    );
  }
}

export default withRouter(ReviewedDFAReviewContainer);
