import React from "react";
import { Link } from "react-router-dom";

const LogInAdminComponent = ({
  handleAdminNameChange,
  handleAdminPasswordChange,
  handleAdminLogIn,
  adminName,
  adminPassword,
  incorrectLogin
}) => {
  return (
    <div className="row">
      <div className="d-flex justify-content-center align-items-center mx-auto col-6">
        <div className="shadow-sm p-3 mb-5 bg-light rounded col-6">
          <div className="col-12">
            <ul className="logNav mr-auto mt-2 mt-lg-0 d-flex justify-content-center align-items-center">
              <li className="nav-item">
                <Link className="col-2" to="/">
<<<<<<< HEAD
                  <button
=======
                  <button                    
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
                    type="button"
                    className="btn btn-secondary"
                    id="userLoginSelection"
                  >
                    User
                  </button>
                </Link>
              </li>
              <li className="nav-item">
                <Link className="col-2" to="/admin">
<<<<<<< HEAD
                  <button
=======
                  <button                    
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
                    type="button"
                    className="btn btn-secondary"
                    id="adminLoginSelection"
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
                autoFocus
                type="text"
                className="form-control"
                id="inputAdminNameLogin"
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
                id="inputAdminPasswordLogin"
                onChange={handleAdminPasswordChange}
                value={adminPassword}
              ></input>
            </div>
            <div className="d-flex justify-content-center">
<<<<<<< HEAD
              <button
                onClick={handleAdminLogIn}
                type="submit"
                className="btn btn-danger"
                id="adminLoginButton"
              >
                Log In As Administrator
              </button>
=======
            <Link className="" to="/adminhomepage-users">
                <button
                  onClick={handleAdminLogIn}
                  type="submit"
                  className="btn btn-danger"
                  id="adminLoginButton"
                >
                  Log In As Administrator
                </button>
              </Link>
>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
            </div>
            {incorrectLogin ? (
              <div className="alert alert-danger my-3" role="alert">
                <h5>Login failed. Check username or password</h5>
              </div>
            ) : (
              <div></div>
            )}
          </form>
        </div>
      </div>
    </div>
  );
};
export default LogInAdminComponent;

<<<<<<< HEAD
=======

>>>>>>> b9702dbb969177a2ed673e32523aaea42ffd197e
// Login as administrator mygtukas padarytas su Linku, kad butu patogiau dirbti kuriant admin interface
