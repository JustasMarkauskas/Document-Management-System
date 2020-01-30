import React from "react";
import AdminHomePageDocumentsNavContainer from './AdminHomePageDocumentsNavContainer';
import AdminHomePageDocumentsTopButtonsContainer from './AdminHomePageDocumentsTopButtonsContainer';
import AdminHomePageDocumentContainer from './AdminHomePageDocumentContainer';

const AdminHomePageDocumentsComponet = () => {
    return (
        <div className="container">
            <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center"><h1>You logged in as administrator</h1></div>
            <div className="row">
                <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded mr-4">
                  <AdminHomePageDocumentsNavContainer/>                    
                </div>
                <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">
                    <AdminHomePageDocumentsTopButtonsContainer/>                    
                    <div className="col-12">
                      <AdminHomePageDocumentContainer/>                    
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AdminHomePageDocumentsComponet;