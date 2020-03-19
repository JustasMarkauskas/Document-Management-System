import React from "react";

import UserHomePageDocNavContainer from "./UserHomePageDocNavContainer";
import UserHomePageDocumentContainer from "./UserHomePageDocumentContainer";

const UserHomePageComponents = ({ username }) => {
  return (
    <div className="no-scroll">
      <div className="row full-height">
        <div className="col-lg-2 p-0 nav-color">
          <UserHomePageDocNavContainer />
        </div>
        <div className="col-lg-10 main-color">
          <div className="m-4 text-center">
            <h1>Welcome, {username}</h1>
          </div>
          <div className="px-5">
            <UserHomePageDocumentContainer />
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserHomePageComponents;
