import React from "react";
import AdminHomePageDocumentsNavContainer from "./AdminHomePageDocumentsNavContainer";
import AdminHomePageDocumentContainer from "./AdminHomePageDocumentContainer";

const AdminHomePageDocumentsComponet = ({ username }) => {
  return (
    <div className="container">
      <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center">
        <h1>Welcome, {username}</h1>
      </div>
      <div className="row">
        <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded mr-4">
          <AdminHomePageDocumentsNavContainer />
        </div>
        <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">
          <div className="col-12">
            <AdminHomePageDocumentContainer />
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminHomePageDocumentsComponet;
