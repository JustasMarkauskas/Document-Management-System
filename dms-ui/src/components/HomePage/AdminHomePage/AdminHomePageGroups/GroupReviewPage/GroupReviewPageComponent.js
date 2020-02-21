import React from "react";
import axios from "axios";

class GroupReviewPageComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showGroupReviewModal: false
    };
  }

  render() {
    return (
      <form className="container" id="groupReviewPageId">
        <div className="form-group">
          <input
            disabled
            type="text"
            className="form-control"
            placeholder={this.props.groupName}
          />
        </div>
        <div className="form-group">
          <input
            type="text"
            defaultValue={this.props.comment}
            onChange={this.props.handleCommentChange}
            className="form-control"
            placeholder="Comment"
          />
        </div>
        <div className="container mt-2">
          <div className="row">
            <button
              onClick={this.props.onOKClick}
              type="button"
              className="btn btn-primary mr-2"
            >
              OK
            </button>
            <button
              onClick={this.props.onHide}
              type="button"
              className="btn btn-primary "
            >
              Cancel
            </button>
          </div>
        </div>
      </form>
    );
  }
}

export default GroupReviewPageComponent;
