import React from "react";

import UserHomePageDFANavContainer from './UserHomePageDFANavContainer';
import UserHomePageDocumentFroApprovalContainer from './UserHomePageDocumentForApprovalContainer';

const UserHomePageDFAComponents = (
  {username}
  ) => {
    return (
        <div className="container">
            <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center"><h1>You logged in as: {username}</h1></div>
            <div className="row">
                <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded mr-4">
                  <UserHomePageDFANavContainer/>                    
                </div>
                <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">                  
                    <div className="col-12">
                      <UserHomePageDocumentFroApprovalContainer/>                    
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserHomePageDFAComponents;