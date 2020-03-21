import React from "react";
import { Link } from "react-router-dom";
import logo from "../../../../resources/logo.png";

const AdminHomePageDocumentsNavComponent = ({ handleLogoutButton }) => {
  return (
    <ul className="nav row nav-sticky">
      <li className="d-none d-lg-block col-lg-12 text-center">
        <img src={logo} alt="" className="img-fluid img-width " />;
      </li>
      <li className="nav-item  col-lg-12">
        <Link className="text-decoration-none" to="/dms/adminhomepage-users">
          <button
            type="button"
            className="text-left nav-btn color btn-block font-weight-bold"
            aria-pressed="true"
            id="adminUserNav"
          >
            Users
          </button>
        </Link>
      </li>
      <li className="nav-item  col-lg-12">
        <Link
          className="text-decoration-none"
          to="/dms/adminhomepage-documents"
        >
          <button
            type="button"
            className="text-left selected-btn selected-color btn-block font-weight-bold"
            id="adminDocumentNav"
          >
            Document types
          </button>
        </Link>
      </li>
      <li className="nav-item mb-2 col-lg-12">
        <Link className="text-decoration-none" to="/dms/adminhomepage-groups">
          <button
            type="button"
            className="text-left nav-btn color btn-block font-weight-bold"
            id="adminGroupNav"
          >
            Groups
          </button>
        </Link>
      </li>
      <li className="nav-item mt-4 col-lg-12">
        <Link className="text-decoration-none" to="/dms/">
          <button
            onClick={handleLogoutButton}
            type="button"
            className="text-left nav-btn color btn-block font-weight-bold"
            id="adminLogoutNav"
          >
            Log out
          </button>
        </Link>
      </li>
    </ul>
  );
};

export default AdminHomePageDocumentsNavComponent;
