import React from "react";
import { Link } from "react-router-dom";
import logo from "../../../../resources/logo.png";

const UserHomePageProfileNavComponent = ({ handleLogoutButton }) => {
  return (
    <ul className="nav row nav-sticky">
      <li className="d-none d-lg-block col-lg-12 text-center">
        <img src={logo} alt="" className="img-fluid img-width " />;
      </li>
      <li className="nav-item  col-lg-12">
        <Link className="text-decoration-none" to="/userhomepage-documents">
          <button
            type="button"
            className="text-left nav-btn color btn-block font-weight-bold"
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
            className="text-left nav-btn color btn-block font-weight-bold"
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
            className="text-left selected-btn selected-color btn-block font-weight-bold"
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
            className="text-left nav-btn color btn-block font-weight-bold"
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
