import React from "react";

import UserHomePageDocNavContainer from './UserHomePageDocNavContainer';

const UserHomePageComponents = (
  
  ) => {
    return (
        <div className="container">
            <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center"><h1>You logged in as: (User Name)</h1></div>
            <div className="row">
                <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded mr-4">
                  <UserHomePageDocNavContainer/>                    
                </div>
                <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">
                  {/* <AdminHomePageUserTopButtonsContainer/>                     */}
                    <div className="col-12">
                      {/* <AdminHomePageUserContainer/>                     */}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserHomePageComponents;