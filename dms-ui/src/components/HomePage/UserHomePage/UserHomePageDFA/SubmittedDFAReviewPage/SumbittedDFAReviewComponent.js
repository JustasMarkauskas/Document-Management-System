import React from "react";
import axios from "axios";
import SubmittedDFAReviewFiles from "./SumbittedDFAReviewFiles";
import serverUrl from "../../../../URL/ServerUrl";
import { store } from "react-notifications-component";

class SubmittedDFAReviewComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      files: [],
      rejectionReason: "",
      username: ""
    };
  }

  getUsername = () => {
    axios
      .get(serverUrl + "api/user/loggedUsername")
      .then(response => {
        this.setState({ username: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };
  componentDidMount() {
    this.getUsername();
  }

  getDocumentFiles = () => {
    axios
      .get(serverUrl + "api/file/" + this.props.id)
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

  handleRejectionReasonChange = event => {
    if (event.target.value.length > 50) {
      document
        .getElementById("rejectionReason")
        .setAttribute("class", "form-control is-invalid");
    } else {
      document
        .getElementById("rejectionReason")
        .setAttribute("class", "form-control");
    }
    this.setState({ rejectionReason: event.target.value });
  };

  handleRejectButton = () => {
    var formIsValid = true;
    if (
      this.state.rejectionReason.length === 0 ||
      this.state.rejectionReason.length > 50
    ) {
      formIsValid = false;
    }
    return formIsValid;
  };

  handleApproveButton = () => {
    var formIsValid = true;
    if (this.state.rejectionReason.length > 0) {
      formIsValid = false;
    }
    return formIsValid;
  };

  closeModal = this.props.onHide;
  updateDocuments = this.props.updateDocuments;

  successRejectNotification = () =>
    store.addNotification({
      title: "Success!",
      message: this.props.id + " rejected successfully",
      type: "success",
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  successApproveNotification = () =>
    store.addNotification({
      title: "Success!",
      message: this.props.id + " approved successfully",
      type: "success",
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  onApproveClick = event => {
    event.preventDefault();
    axios
      .put(serverUrl + "api/document/approve-document", {
        documentReceiver: this.state.username,
        id: this.props.id
      })
      .then(() => {
        this.closeModal();
        this.updateDocuments();
        this.successApproveNotification();
      })
      .catch(error => {
        console.log(error);
      });
  };

  onRejectClick = event => {
    event.preventDefault();
    axios
      .put(serverUrl + "api/document/reject-document", {
        documentReceiver: this.state.username,
        id: this.props.id,
        rejectionReason: this.state.rejectionReason
      })
      .then(() => {
        this.closeModal();
        this.updateDocuments();
        this.successRejectNotification();
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    const documentFiles = this.state.files.map((file, index) => (
      <SubmittedDFAReviewFiles
        key={index}
        id={file.id}
        fileName={file.fileName}
      />
    ));

    return (
      <form className="container" id="SubmittedDFAReview">
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
            <label htmlFor="disabledAuthor">Author</label>
            <input
              type="text"
              id="disableAuthor"
              className="form-control"
              placeholder={this.props.author}
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
            <label htmlFor="disabledDocType">Type</label>
            <input
              type="text"
              id="disabledDocType"
              className="form-control"
              placeholder={this.props.docType}
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
            <label htmlFor="disabledStatus">Status</label>
            <input
              type="text"
              id="disabledStatus"
              className="form-control"
              placeholder={this.props.status}
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
        </fieldset>
        <div className="form-group">
          <label htmlFor="rejectionReason">Rejection reason</label>
          <textarea
            className="form-control"
            type="text"
            id="rejectionReason"
            onChange={this.handleRejectionReasonChange}
            placeholder="Reason"
          />
          <div className="invalid-feedback text-info">
            Must be 50 characters or less
          </div>
        </div>
        {documentFiles}
        <div className="container mt-2">
          <div className="row">
            <button
              disabled={!this.handleApproveButton()}
              onClick={this.onApproveClick}
              type="button"
              className="btn btn-primary mr-2"
            >
              Approve
            </button>

            <button
              disabled={!this.handleRejectButton()}
              onClick={this.onRejectClick}
              type="button"
              className="btn btn-primary mr-2"
            >
              Reject
            </button>

            <button
              onClick={this.props.onHide}
              type="button"
              className="btn btn-secondary"
            >
              Cancel
            </button>
          </div>
        </div>
      </form>
    );
  }
}

export default SubmittedDFAReviewComponent;
