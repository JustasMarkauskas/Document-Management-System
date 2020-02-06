import React, { useState } from "react";

const NewGroupFormComponent = ({
  handleSubmit,
  handleCancel,
  handleGroupNameChange,
  handleCommentChange,
  comment,
  groupName
}) => {
  return (
    <div className="container">
      <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center">
        <h1>You logged in as administrator</h1>
      </div>
      <h2 className="text-center"> Create New Group</h2>
      <div className="row">
        <form className="container bg-light rounded col-md-8 offset-md-2 py-3">
          <div className="form-group">
            <label htmlFor="groupName">Group name</label>
            <input
              autoFocus
              type="text"
              className="form-control"
              id="groupName"
              placeholder="Enter Group name"
              onChange={handleGroupNameChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="groupName">Comment</label>
            <input
              type="text"
              className="form-control"
              id="comment"
              placeholder="Enter comment"
              onChange={handleCommentChange}
            />
          </div>
          <button
            type="submit"
            onClick={handleSubmit}
            className="btn btn-primary mx-2"
          >
            Submit
          </button>
          <button onClick={handleCancel} className="btn btn-secondary">
            Cancel
          </button>
        </form>
      </div>
    </div>
  );
};

export default NewGroupFormComponent;
