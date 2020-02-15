import React from "react";

const SumbittedDocReviewComponent = ({
  id,
  docType,
  title,
  description,
  submissionDate,
  reviewDate,
  status,
  documentReceiver,
  rejectionReason
}) => {
  return (
    <form className="container">
      <fieldset disabled>
        <div className="form-group">
          <label htmlFor="disabledID">Unique ID</label>
          <input
            type="text"
            id="disabledID"
            className="form-control"
            placeholder={id}
          />
        </div>
        <div className="form-group">
          <label htmlFor="disabledDocType">Doc type</label>
          <input
            type="text"
            id="disabledDocType"
            className="form-control"
            placeholder={docType}
          />
        </div>
        <div className="form-group">
          <label htmlFor="disabledTitle">Title</label>
          <input
            type="text"
            id="disabledTitle"
            className="form-control"
            placeholder={title}
          />
        </div>
        <div className="form-group">
          <label htmlFor="disabledDescription">Description</label>
          <input
            type="text"
            id="disabledDescription"
            className="form-control"
            placeholder={description}
          />
        </div>
        <div className="form-group">
          <label htmlFor="disabledSubmissionDate">Submission date</label>
          <input
            type="text"
            id="disabledSubmissionDate"
            className="form-control"
            placeholder={submissionDate}
          />
        </div>
        <div className="form-group">
          <label htmlFor="disabledReviewDate">Review date</label>
          <input
            type="text"
            id="disabledReviewDate"
            className="form-control"
            placeholder={reviewDate}
          />
        </div>
        <div className="form-group">
          <label htmlFor="disabledStatus">Status</label>
          <input
            type="text"
            id="disabledStatus"
            className="form-control"
            placeholder={status}
          />
        </div>
        <div className="form-group">
          <label htmlFor="disabledDocumentReceiver">Document receiver</label>
          <input
            type="text"
            id="disabledDocumentReceiver"
            className="form-control"
            placeholder={documentReceiver}
          />
        </div>
        <div className="form-group">
          <label htmlFor="disabledRejectionReason">Rejection Reason</label>
          <input
            type="text"
            id="disabledRejectionReason"
            className="form-control"
            placeholder={rejectionReason}
          />
        </div>
      </fieldset>
    </form>
  );
};

export default SumbittedDocReviewComponent;
