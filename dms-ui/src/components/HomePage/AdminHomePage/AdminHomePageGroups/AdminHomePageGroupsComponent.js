import React from "react";
import { Link } from "react-router-dom";
import AdminHomePageGroupNavContainer from "./AdminHomePageGroupNavContainer";
import AdminHomePageGroupTopButtonsContainer from "./AdminHomePageGroupTopButtonsContainer";
import AdminHomePageGroupContainer from "./AdminHomePageGroupcontainer";

const AdminHomePageGroupsComponent = ({ username }) => {
  return (
    <div className="container">
      <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center">
        <h1>You logged in as administrator. Username: {username}</h1>
      </div>
      <div className="row">
        <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded mr-4">
          <AdminHomePageGroupNavContainer />
        </div>
        <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">
          <AdminHomePageGroupTopButtonsContainer />
          <div className="col-12">
            <AdminHomePageGroupContainer />
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminHomePageGroupsComponent;
