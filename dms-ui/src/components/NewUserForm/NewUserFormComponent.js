import React, { useState } from "react";

const NewUserFormComponent = ({
  handleUsernameChange,
  handleFirstNameChange,
  handleLastNameChange,
  handleCommentChange,
  handlePasswordChange,
  handleSubmit,
  handleCancel,
  username,
  firstName,
  lastName,
  comment,
  password,
  confirmPassword
}) => {
  return (
    <div className="container">
      <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center">
        <h1>You logged in as administrator</h1>
      </div>
      <h2 className="text-center"> Create New Username</h2>
      <div className="row">
        <form className="container bg-light rounded col-md-8 offset-md-2 py-3">
          <div className="form-group">
            <label htmlFor="username">User name</label>
            <input
              autoFocus
              type="text"
              className="form-control"
              id="username"
              placeholder="Enter user name"
              onChange={handleUsernameChange}
              value={username}
            />
          </div>

          <div className="form-group">
            <label htmlFor="firstName">First Name</label>
            <input
              type="text"
              className="form-control"
              id="firstName"
              placeholder="Enter first name"
              onChange={handleFirstNameChange}
              value={firstName}
            />
          </div>

          <div className="form-group">
            <label htmlFor="lastName">Last name</label>
            <input
              type="text"
              className="form-control"
              id="lastName"
              placeholder="Enter last name"
              onChange={handleLastNameChange}
              value={lastName}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              className="form-control"
              id="password"
              placeholder="Enter password"
              onChange={handlePasswordChange}
              value={password}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Confirm Password</label>
            <input
              type="password"
              className="form-control"
              id="confirmPassword"
              placeholder="Confirm password"
              value={confirmPassword}
            />
          </div>

          <div className="form-group">
            <label htmlFor="comment">Comment</label>
            <textarea
              className="form-control"
              id="comment"
              rows="3"
              onChange={handleCommentChange}
              value={comment}
            ></textarea>
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

export default NewUserFormComponent;
