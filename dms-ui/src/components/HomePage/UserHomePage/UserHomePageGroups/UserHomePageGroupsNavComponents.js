import React from "react";
import { Link } from "react-router-dom";

const UserHomePageGroupsNavComponent = ({ handleLogoutButton }) => {
  return (
    <ul className="nav flex-column">
      <li className="nav-item mb-2">
        <Link className="col-2" to="/userhomepage-documents">
          <button
            type="button"
            className="btn btn-outline-primary"
            aria-pressed="true"
            id="userDocumentNav"
          >
            Documents
          </button>
        </Link>
      </li>
      <li className="nav-item mb-2">
        <Link className="col-2" to="/userhomepage-dfa">
          <button
            type="button"
            className="btn btn-outline-primary"
            id="userDFANav"
          >
            Documents for approval
          </button>
        </Link>
      </li>
      <li className="nav-item mb-2">
        <Link className="col-2" to="/userhomepage-groups">
          <button type="button" className="btn btn-primary" id="userGroupsNav">
            Profile
          </button>
        </Link>
      </li>
      <li className="nav-item mt-4">
        <Link className="col-2" to="/">
          <button
            onClick={handleLogoutButton}
            type="button"
            className="btn btn-outline-dark"
            id="userLogoutNav"
          >
            Log out
          </button>
        </Link>
      </li>
    </ul>
  );
};

export default UserHomePageGroupsNavComponent;
