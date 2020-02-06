import React from "react";
import { Link } from "react-router-dom";

const LogInUserComponent = ({
  handleUserNameChange,
  handleUserPasswordChange,
  handleUserLogIn,
  userName,
  userPassword,
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
                  <button
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
                  <button
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
              <label htmlFor="exampleInputEmail1">User name:</label>
              <input
                autoFocus
                type="text"
                className="form-control"
                id="inputUserNameLogin"
                aria-describedby="emailHelp"
                onChange={handleUserNameChange}
                value={userName}
              ></input>
            </div>
            <div className="form-group">
              <label htmlFor="exampleInputPassword1">Password:</label>
              <input
                type="password"
                className="form-control"
                id="inputUserPasswordLogin"
                onChange={handleUserPasswordChange}
                value={userPassword}
              ></input>
            </div>
<<<<<<< HEAD
            <div className="d-flex justify-content-center">
              <button
                onClick={handleUserLogIn}
                type="submit"
                className="btn btn-secondary"
                id="userLoginButton"
              >
                Log In
              </button>
=======
            <div className="d-flex justify-content-center">            
              <Link className="" to="/userhomepage-documents">
                <button
                  onClick={handleUserLogIn}
                  type="submit"
                  className="btn btn-secondary"
                  id="userLoginButton"
                >
                  Log In
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
export default LogInUserComponent;
