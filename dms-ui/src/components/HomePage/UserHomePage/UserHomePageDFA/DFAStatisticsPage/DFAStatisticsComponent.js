import React from "react";
import DFAStatisticsReviewContainer from "./DFAStatisticsReviewPage/DFAStatisticsReviewContainer";
import { Modal } from "react-bootstrap";

class DFAStatisticsComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showStatisticsModal: false,
      selectedDocType: ""
    };
  }

  handleStatisticsModalClose = () => {
    this.setState({ showStatisticsModal: false });
  };

  handleShowStatisticsModal = docType => {
    this.setState({ showStatisticsModal: true, selectedDocType: docType });
  };

  render() {
    return (
      <div className="container">
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
            />
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default DFAStatisticsComponent;
