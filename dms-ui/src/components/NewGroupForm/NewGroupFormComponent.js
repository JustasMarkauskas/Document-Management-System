import React, { useState } from "react";
// import { Form } from "react-bootstrap";
// import { Button, Alert } from 'react-bootstrap';
// import { Formik } from "formik";
// import * as yup from 'yup';
// import axios from "axios";

// const schema = yup.object({
//   groupName: yup
//       .string()
//       .min(5, 'Must be 5 characters or more')
//       .max(20, 'Must be 20 characters or less')
//       .required('Please Enter a groupName')
//       .matches(/^[A-Za-z\d]+$/, "Only Uppercases, Lowercases And Numbers"),
// });

const NewGroupFormComponent = ({  
  handleSubmit,
  handleCancel,
  handleChange,
  groupName,  
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
          value={groupName}
          onChange={handleChange}
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
