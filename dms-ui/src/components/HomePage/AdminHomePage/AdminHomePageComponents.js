import React from "react";
import AdminHomePageUserContainer from "./AdminHomePageUsers/AdminHomePageUsersContainer";
import AdminHomePageUserNavContainer from "./AdminHomePageUsers/AdminHomePageUserNavContainer";

<<<<<<< HEAD
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
          <div className="col-12">
            <AdminHomePageUserContainer />
          </div>
=======

const AdminHomePageComponent = (
  
  ) => {
    return (
        <div className="container">
            <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center"><h1>You logged in as: administrator</h1></div>
            <div className="row">
                <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded mr-4">
                  <AdminHomePageUserNavContainer/>                    
                </div>
                <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">
                  <AdminHomePageUserTopButtonsContainer/>                    
                    <div className="col-12">
                      <AdminHomePageUserContainer/>                    
                    </div>
                </div>
            </div>
>>>>>>> ccbdd81f78352329f8a9d28f8f5416715f6a218e
        </div>
      </div>
    </div>
  );
};

export default AdminHomePageComponent;
