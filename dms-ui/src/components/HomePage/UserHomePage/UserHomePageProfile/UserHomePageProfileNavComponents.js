import React from "react";
import { Link } from "react-router-dom";

const UserHomePageProfileNavComponent = ({ handleLogoutButton }) => {
  return (
    <ul className="nav row nav-sticky">
      <li className="nav-item  col-lg-12">
        <Link className="text-decoration-none" to="/userhomepage-documents">
          <button
            type="button"
            className="text-left nav-btn color btn-block"
            aria-pressed="true"
            id="userDocumentNav"
          >
            Documents
          </button>
        </Link>
      </li>
      <li className="nav-item  col-lg-12">
        <Link className="text-decoration-none" to="/userhomepage-dfa">
          <button
            type="button"
            className="text-left nav-btn color btn-block"
            id="userDFANav"
          >
            Documents for approval
          </button>
        </Link>
      </li>
      <li className="nav-item mb-2 col-lg-12">
        <Link className="text-decoration-none" to="/userhomepage-profile">
          <button
            type="button"
            className="text-left selected-btn selected-color btn-block"
            id="userGroupsNav"
          >
            Profile
          </button>
        </Link>
      </li>
      <li className="nav-item mt-4 col-lg-12">
        <Link className="text-decoration-none" to="/">
          <button
            onClick={handleLogoutButton}
            type="button"
            className="text-left nav-btn color btn-block"
            id="userLogoutNav"
          >
            Log out
          </button>
        </Link>
      </li>
    </ul>
  );
};

export default UserHomePageProfileNavComponent;
