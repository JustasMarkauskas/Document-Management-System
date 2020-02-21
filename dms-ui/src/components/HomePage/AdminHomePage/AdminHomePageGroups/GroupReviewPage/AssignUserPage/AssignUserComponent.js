import React from "react";
import { Modal } from "react-bootstrap";
import axios from "axios";

class AssignUserComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="container">
        <div className="row">
          <div className="col-4">
            <button
              onClick={this.props.onOKClick}
              type="button"
              className="btn btn-primary m-2"
            >
              TE
            </button>
            <button
              onClick={this.props.onOKClick}
              type="button"
              className="btn btn-primary m-2"
            >
              TEST
            </button>
            <button
              onClick={this.props.onOKClick}
              type="button"
              className="btn btn-primary m-2"
            >
              TESST
            </button>
          </div>
          <div className="col-7">
            <form id="groupReviewPageId">
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
          </div>
        </div>
      </div>
    );
  }
}

export default AssignUserComponent;
