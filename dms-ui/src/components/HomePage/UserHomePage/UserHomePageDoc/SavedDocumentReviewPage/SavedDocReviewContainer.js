import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import SavedDocReviewComponent from "./SavedDocReviewComponent";
import serverUrl from "../../../../URL/ServerUrl";

class SavedDocReviewContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      documentId: "",
      document: []
    };
  }

  getDocument = () => {
    axios.get(serverUrl + "api/user/loggedUsername").then(response => {
      let username = response.data;
      axios
        .get(serverUrl + "api/document/" + this.props.docId + "/" + username)

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
      <SavedDocReviewComponent
        id={this.state.document.id}
        docType={this.state.document.docType}
        title={this.state.document.title}
        description={this.state.document.description}
        submissionDate={this.state.document.submissionDate}
        reviewDate={this.state.document.reviewDate}
        status={this.state.document.status}
        documentReceiver={this.state.document.documentReceiver}
        rejectionReason={this.state.document.rejectionReason}
        onHide={this.props.onHide}
        updateDocuments={this.props.updateDocuments}
        userDocTypes={this.props.userDocTypes}
      />
    );
  }
}

export default withRouter(SavedDocReviewContainer);
