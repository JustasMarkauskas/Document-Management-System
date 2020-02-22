import React from "react";
import axios from "axios";
import SumbittedDocReviewFiles from "./SumbittedDocReviewFiles";

class SumbittedDocReviewComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      files: []
    };
  }

  getDocumentFiles = () => {
    axios
      .get("http://localhost:8081/api/file/" + this.props.id)
      .then(response => {
        this.setState({ files: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidUpdate(prevProps) {
    if (this.props.id !== prevProps.id) {
      this.getDocumentFiles();
    }
  }

  // componentDidMount() {
  //   this.getDocumentFiles();
  // }

  render() {
    const documentFiles = this.state.files.map((file, index) => (
      <SumbittedDocReviewFiles
        key={index}
        id={file.id}
        fileName={file.fileName}
      />
    ));

    return (
      <form className="container" id="submittedDocReview">
        <fieldset disabled>
          <div className="form-group">
            <label htmlFor="disabledID">Unique ID</label>
            <input
              type="text"
              id="disabledID"
              className="form-control"
              placeholder={this.props.id}
            />
          </div>
          <div className="form-group">
            <label htmlFor="disabledDocType">Doc type</label>
            <input
              type="text"
              id="disabledDocType"
              className="form-control"
              placeholder={this.props.docType}
            />
          </div>
          <div className="form-group">
            <label htmlFor="disabledTitle">Title</label>
            <input
              type="text"
              id="disabledTitle"
              className="form-control"
              placeholder={this.props.title}
            />
          </div>
          <div className="form-group">
            <label htmlFor="disabledDescription">Description</label>
            <input
              type="text"
              id="disabledDescription"
              className="form-control"
              placeholder={this.props.description}
            />
          </div>
          <div className="form-group">
            <label htmlFor="disabledSubmissionDate">Submission date</label>
            <input
              type="text"
              id="disabledSubmissionDate"
              className="form-control"
              placeholder={this.props.submissionDate}
            />
          </div>
          <div className="form-group">
            <label htmlFor="disabledReviewDate">Review date</label>
            <input
              type="text"
              id="disabledReviewDate"
              className="form-control"
              placeholder={this.props.reviewDate}
            />
          </div>
          <div className="form-group">
            <label htmlFor="disabledStatus">Status</label>
            <input
              type="text"
              id="disabledStatus"
              className="form-control"
              placeholder={this.props.status}
            />
          </div>
          <div className="form-group">
            <label htmlFor="disabledDocumentReceiver">Document receiver</label>
            <input
              type="text"
              id="disabledDocumentReceiver"
              className="form-control"
              placeholder={this.props.documentReceiver}
            />
          </div>
          <div className="form-group">
            <label htmlFor="disabledRejectionReason">Rejection Reason</label>
            <input
              type="text"
              id="disabledRejectionReason"
              className="form-control"
              placeholder={this.props.rejectionReason}
            />
          </div>
        </fieldset>
        {documentFiles}

        <button
          onClick={this.props.onHide}
          type="button"
          className="btn btn-primary col-lg-2 mb-2"
        >
          Cancel
        </button>
      </form>
    );
  }
}

export default SumbittedDocReviewComponent;
