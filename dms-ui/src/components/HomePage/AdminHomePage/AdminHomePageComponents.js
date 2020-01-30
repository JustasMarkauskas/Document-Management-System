import React from "react";
import { Link } from "react-router-dom";

const AdminHomePageComponent = ({
  handleSearchChange, 
  searchValue, 
  handleSearchButton, 
  handleAddUserButton,
  handleLogoutButton}) => {
    return (
        <div className="container">
            <div className="row col-12 shadow-sm p-3 mb-5 bg-light rounded justify-content-center"><h1>You loged in as administrator</h1></div>
            <div className="row">
                <div className="col-lg-2 shadow-sm p-3 mb-5 bg-light rounded mr-4">
                    <ul className="nav flex-column">
                        <li className="nav-item mb-2">
                          <Link className="col-2" to="/adminhomepage-users">
                            <button                               
                              type="button" 
                              className="btn btn-primary" 
                              aria-pressed="true">
                                Users
                            </button>
                          </Link>
                        </li>                        
                        <li className="nav-item mb-2">
                          <Link className="col-2" to="/adminhomepage-documents">
                            <button                               
                              type="button" 
                              className="btn btn-outline-primary">
                                Documents
                            </button>
                          </Link>
                        </li>
                        <li className="nav-item mb-2">
                          <Link className="col-2" to="/adminhomepage-groups">
                            <button                               
                              type="button" 
                              className="btn btn-outline-primary">
                                Groups
                            </button>
                          </Link>
                        </li>
                        <li className="nav-item mt-4">
                          <Link className="col-2" to="/">
                            <button
                              onClick={handleLogoutButton}                               
                              type="button" 
                              className="btn btn-outline-dark">
                                Log Out
                            </button>
                          </Link>
                        </li>
                    </ul>
                </div>
                <div className="row col-lg-9 shadow-sm p-3 mb-5 bg-light rounded">
                    <div className="row col-lg-12">
                      <button
                        onClick={handleAddUserButton} 
                        type="button" 
                        className="btn btn-primary col-lg-2 mb-2">
                          Add new user
                      </button>
                        <div className="input-group mb-3 col-lg-5">
                          <input 
                            type="text" 
                            className="form-control" 
                            placeholder="Username" 
                            aria-label="username" 
                            aria-describedby="button-addon2">
                          </input>
                          <div className="input-group-append">
                            <button 
                              className="btn btn-primary" 
                              type="button" 
                              id="button-addon2"
                              onClick={handleSearchButton}
                              onChange={handleSearchChange}
                              value={searchValue}>
                                Search
                            </button>
                          </div>
                      </div>
                    </div>
                    <div className="col-12">
                    <table className="table">
                        <thead>
                            <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Name</th>
                            <th scope="col">Surname</th>
                            <th scope="col">User name</th>
                            <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                            <th scope="row">1</th>
                            <td>Al</td>
                            <td>Bundy</td>
                            <td>Phycho dad</td>
                            <td>
                              <button 
                                className="btn btn-primary" 
                                type="button" 
                                id="button-addon2"
                                onClick={handleSearchButton}
                                onChange={handleSearchChange}
                                value={searchValue}>
                                  <i className="fas fa-cog"></i>
                              </button></td>
                            </tr>
                            <tr>
                            <th scope="row">2</th>
                            <td>Bud</td>
                            <td>Bundy</td>
                            <td>Big B</td>
                            </tr>
                            <tr>
                            <th scope="row">3</th>
                            <td>Kelly</td>
                            <td>Bundy</td>
                            <td>Pumpkin</td>
                            </tr>
                            <tr>
                            <th scope="row">4</th>
                            <td>Peggy</td>
                            <td>Bundy</td>
                            <td>Devil</td>
                            </tr>
                        </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AdminHomePageComponent;