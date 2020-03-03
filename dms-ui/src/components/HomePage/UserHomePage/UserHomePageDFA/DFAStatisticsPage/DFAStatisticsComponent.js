import React from "react";
import DFAStatisticsReviewContainer from "./DFAStatisticsReviewPage/DFAStatisticsReviewContainer";
import { Modal } from "react-bootstrap";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import "bootstrap/dist/css/bootstrap.min.css";

class DFAStatisticsComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showStatisticsModal: false,
      selectedDocType: "",
      startDate: "",
      endDate: ""
    };
  }

  handleStatisticsModalClose = () => {
    this.setState({ showStatisticsModal: false });
  };

  handleShowStatisticsModal = docType => {
    this.setState({ showStatisticsModal: true, selectedDocType: docType });
  };

  handleStartDateChange = date => {
    this.setState({
      startDate: date
    });
  };

  handleEndDateChange = date => {
    this.setState({
      endDate: date
    });
  };

  render() {
    return (
      <div className="container">
        <div>
          selectkk
          <form onSubmit={this.handleSubmit}>
            <div className="form-group">
              <label>Start date: </label>
              <DatePicker
                value={this.state.startDate}
                selected={this.state.startDate}
                onChange={this.handleStartDateChange}
                name="startDate"
                dateFormat="yyyy-MM-dd"
              />
            </div>
            <div className="form-group">
              <label>End date: </label>
              <DatePicker
                value={this.state.endDate}
                selected={this.state.endDate}
                onChange={this.handleEndDateChange}
                name="startDate"
                dateFormat="yyyy-MM-dd"
              />
            </div>
          </form>
        </div>
        <div className="card">
          <div className="card-body">
            <h5 className="card-title">Document types </h5>
            <div className="card-text scroll">
              <ul className="list-group mb-2">
                {this.props.userDocTypesForApproval.map((docType, index) => {
                  return (
                    <div key={index}>
                      <button
                        type="button"
                        className="list-group-item list-group-item-action"
                        onClick={event =>
                          this.handleShowStatisticsModal(docType)
                        }
                      >
                        {docType}
                      </button>
                    </div>
                  );
                })}
              </ul>
            </div>
          </div>
        </div>
        <Modal
          show={this.state.showStatisticsModal}
          onHide={this.handleStatisticsModalClose}
        >
          <Modal.Header closeButton>
            <Modal.Title>{this.state.selectedDocType} statistics</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <DFAStatisticsReviewContainer
              onHide={this.handleStatisticsModalClose}
              docType={this.state.selectedDocType}
              startDate={new Intl.DateTimeFormat("sv-SE").format(
                this.state.startDate
              )}
              endDate={new Intl.DateTimeFormat("sv-SE").format(
                this.state.endDate
              )}
            />
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default DFAStatisticsComponent;
