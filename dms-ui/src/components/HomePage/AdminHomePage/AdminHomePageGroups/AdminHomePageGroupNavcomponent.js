import React from "react";
import { Link } from "react-router-dom";

const AdminHomePageGroupNavComponent = ({ handleLogoutButton }) => {
  return (
    <ul className="nav row nav-sticky">
      <li className="nav-item  col-lg-12">
        <Link className="text-decoration-none" to="/adminhomepage-users">
          <button
            type="button"
            className="text-left nav-btn color btn-block"
            aria-pressed="true"
            id="adminUserNav"
          >
            Users
          </button>
        </Link>
      </li>
      <li className="nav-item  col-lg-12">
        <Link className="text-decoration-none" to="/adminhomepage-documents">
          <button
            type="button"
            className="text-left nav-btn color btn-block"
            id="adminDocumentNav"
          >
            Document types
          </button>
        </Link>
      </li>
      <li className="nav-item mb-2 col-lg-12">
        <Link className="text-decoration-none" to="/adminhomepage-groups">
          <button
            type="button"
            className="text-left selected-btn selected-color btn-block"
            id="adminGroupNav"
          >
            Groups
          </button>
        </Link>
      </li>
      <li className="nav-item mt-4 col-lg-12">
        <Link className="text-decoration-none" to="/">
          <button
            onClick={handleLogoutButton}
            type="button"
            className="text-left nav-btn color btn-block"
            id="adminLogoutNav"
          >
            Log out
          </button>
        </Link>
      </li>
    </ul>
  );
};

export default AdminHomePageGroupNavComponent;
