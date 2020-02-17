import React from "react";
import { withRouter } from "react-router-dom";
import axios from "axios";
import SumbittedDocReviewComponent from "./SumbittedDocReviewComponent";

class SubmittedDocReviewContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      document: [],
      username: ""
    };
  }

  getUsername = () => {
    axios
      .get("http://localhost:8081/api/user/loggedUsername")
      .then(response => {
        this.setState({ username: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  getDocument = () => {
    axios
      .get("http://localhost:8081/api/document/833/tt")
      .then(response => {
        this.setState({ document: response.data });
        //     console.log(this.props.location);
        //console.log(this.state.document.id + this.state.username);
      })
      .catch(error => {
        //  console.log(this.state.document.id + this.state.username);
        console.log(error);
      });
  };

  componentDidMount() {
    this.getUsername();
    this.getDocument();
    // this.getDocument();
  }

  // componentDidUpdate(state) {
  //   if (this.state.username !== state.username) {
  //     this.getDocument();
  //   }
  // }

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
