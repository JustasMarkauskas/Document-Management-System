import React from "react";
import AdminHomePageUserNavContainer from "./AdminHomePageUsers/AdminHomePageUserNavContainer";
import AdminHomePageUsersContainer from "./AdminHomePageUsers/AdminHomePageUsersContainer";

const AdminHomePageComponent = ({ username }) => {
  return (
    <div className="no-scroll">
      <div className="row full-height">
        <div className="col-lg-2 p-0 nav-color">
          <AdminHomePageUserNavContainer />
        </div>
        <div className="col-lg-10 p-3">
          <div className="mb-5 text-center">
            <h1>Welcome, {username}</h1>
          </div>
          <AdminHomePageUsersContainer />
        </div>
      </div>
    </div>
  );
};

export default AdminHomePageComponent;
