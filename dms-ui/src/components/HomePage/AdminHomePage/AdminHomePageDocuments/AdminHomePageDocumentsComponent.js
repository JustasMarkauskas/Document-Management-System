import React from "react";
import AdminHomePageDocumentsNavContainer from "./AdminHomePageDocumentsNavContainer";
import AdminHomePageDocumentContainer from "./AdminHomePageDocumentContainer";

const AdminHomePageDocumentsComponet = ({ username }) => {
  return (
    <div className="no-scroll">
      <div className="row full-height">
        <div className="col-lg-2 p-0 nav-color">
          <AdminHomePageDocumentsNavContainer />
        </div>
        <div className="col-lg-10 p-3 main-color">
          <div className="mb-5 text-center">
            <h1>Welcome, {username}</h1>
          </div>
          <AdminHomePageDocumentContainer />
        </div>
      </div>
    </div>
  );
};

export default AdminHomePageDocumentsComponet;
