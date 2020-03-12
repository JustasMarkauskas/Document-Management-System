import React from "react";
import axios from "axios";
import serverUrl from "../../../../URL/ServerUrl";
import { withRouter } from "react-router-dom";

class DocumentTypeReviewContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      comment: ""
    };
  }

  componentDidMount() {
    this.setState({ comment: this.props.comment });
  }

  handleButtonValidation = () => {
    var formIsValid = true;
    if (this.state.comment.length > 50) {
      formIsValid = false;
    }
    return formIsValid;
  };

  handleCommentChange = event => {
    if (event.target.value.length > 50) {
      document
        .getElementById("docTypeCommentId")
        .setAttribute("class", "form-control is-invalid");
    } else {
      document
        .getElementById("docTypeCommentId")
        .setAttribute("class", "form-control");
    }
    this.setState({ comment: event.target.value });
  };

  onSaveClick = event => {
    event.preventDefault();
    axios
      .put(serverUrl + "api/doctype/update-comment/" + this.props.docType, {
        comment: this.state.comment
      })
      .then(this.props.onCloseModalAfterSubmit(this.props.docType))
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <form id="groupReviewPageId">
        <div className="form-group">
          <label htmlFor="docType"> Type</label>
          <input
            id="docType"
            disabled
            type="text"
            className="form-control"
            placeholder={this.props.docType}
          />
        </div>
        <div className="form-group">
          <label htmlFor="docTypeCommentId">Comment</label>
          <input
            id="docTypeCommentId"
            type="text"
            defaultValue={this.props.comment}
            onChange={this.handleCommentChange}
            className="form-control"
            placeholder="Comment"
          />
          <div className="invalid-feedback text-info">
            Must be 50 characters or less
          </div>
        </div>
        <div className="container mt-2">
          <div className="row">
            <button
              disabled={!this.handleButtonValidation()}
              onClick={this.onSaveClick}
              type="button"
              className="btn btn-primary mr-2"
            >
              Save
            </button>
            <button
              onClick={this.props.onHide}
              type="button"
              className="btn btn-secondary "
            >
              Cancel
            </button>
          </div>
        </div>
      </form>
    );
  }
}
export default withRouter(DocumentTypeReviewContainer);
