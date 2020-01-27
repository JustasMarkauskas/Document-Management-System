import React from "react";
import { Link } from "react-router-dom";

const LogInAdminComponent = ({
  handleAdminNameChange,
  handleAdminPasswordChange,
  // handleAdminButton,
  // handleUserButton,
  handleAdminLogIn,
  adminName,
  adminPassword
}) => {
  return (
    <div className="row">
      <div className="d-flex justify-content-center align-items-center mx-auto col-6">
        <div className="shadow-sm p-3 mb-5 bg-light rounded col-6">
          <div className="col-12">
            <ul className="logNav mr-auto mt-2 mt-lg-0 d-flex justify-content-center align-items-center">
              <li className="nav-item">
                <Link className="col-2" to="/">
                  <button
                    // onClick={handleUserButton}
                    type="button"
                    className="btn btn-secondary"
                  >
                    User
                  </button>
                </Link>
              </li>
              <li className="nav-item">
                <Link className="col-2" to="/admin">
                  <button
                    // onClick={handleAdminButton}
                    type="button"
                    className="btn btn-secondary"
                  >
                    Admin
                  </button>
                </Link>
              </li>
            </ul>
          </div>
          <form>
            <div className="form-group">
              <label htmlFor="exampleInputEmail1">Administrator name:</label>
              <input
                type="text"
                className="form-control"
                id="adminName"
                aria-describedby="emailHelp"
                onChange={handleAdminNameChange}
                value={adminName}
              ></input>
            </div>
            <div className="form-group">
              <label htmlFor="exampleInputPassword1">
                Administrator Password:
              </label>
              <input
                type="password"
                className="form-control"
                id="adminPassword"
                onChange={handleAdminPasswordChange}
                value={adminPassword}
              ></input>
            </div>
            <div className="d-flex justify-content-center">
              <button
                onClick={handleAdminLogIn}
                type="submit"
                className="btn btn-danger"
              >
                Log In As Administrator
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};
export default LogInAdminComponent;
