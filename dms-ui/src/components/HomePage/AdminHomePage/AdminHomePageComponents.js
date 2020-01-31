import React from "react";
import AdminHomePageUserContainer from "./AdminHomePageUsers/AdminHomePageUsersContainer";
import AdminHomePageUserTopButtonsContainer from "./AdminHomePageUsers/AdminHomePageUserTopButtonsContainer";
import AdminHomePageUserNavContainer from "./AdminHomePageUsers/AdminHomePageUserNavContainer";

const AdminHomePageComponent = ({ username }) => {
  return (
    <div className="container">
      <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center">
        <h1>You logged in as administrator. Username: {username}</h1>
      </div>
      <div className="row">
        <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded mr-4">
          <AdminHomePageUserNavContainer />
        </div>
        <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">
          <AdminHomePageUserTopButtonsContainer />
          <div className="col-12">
            <AdminHomePageUserContainer />
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminHomePageComponent;
