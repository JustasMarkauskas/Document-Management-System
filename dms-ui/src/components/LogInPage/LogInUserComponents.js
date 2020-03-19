import React from "react";
import loginLogo from "../../../src/resources/login-logo.png";

const LogInUserComponent = ({
  handleUserNameChange,
  handleUserPasswordChange,
  handleUserLogIn,
  userName,
  userPassword,
  incorrectLogin
}) => {
  return (
    <div className="background-image">
      <div className="row ">
        <div className="d-flex justify-content-center align-items-center mx-auto col-lg-6 col-md-10 col-sm-12 mt-5">
          <div className="p-3 my-5 rounded  bg-white">
            <div className="d-none d-lg-block col-lg-12 text-center">
              <img src={loginLogo} alt="" className="img-fluid" />
            </div>
            <form>
              <div className="form-group">
                <input
                  autoFocus
                  placeholder="Username"
                  type="text"
                  className="form-control mb-4"
                  id="inputUserNameLogin"
                  aria-describedby="emailHelp"
                  onChange={handleUserNameChange}
                  value={userName}
                ></input>
              </div>
              <div className="form-group">
                <input
                  placeholder="Password"
                  type="password"
                  className="form-control"
                  id="inputUserPasswordLogin"
                  onChange={handleUserPasswordChange}
                  value={userPassword}
                ></input>
              </div>
              <div className="d-flex justify-content-center">
                <button
                  onClick={handleUserLogIn}
                  type="submit"
                  className="main-btn btn-color col-lg-12 col-md-12 font-weight-bold"
                  id="userLoginButton"
                >
                  LOGIN
                </button>
              </div>
              {incorrectLogin ? (
                <div className="alert mt-3 alert-color" role="alert">
                  <h5>Login failed. Check username or password</h5>
                </div>
              ) : (
                <div></div>
              )}
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};
export default LogInUserComponent;
