import React from "react";
import { Link } from "react-router-dom";

const AdminHomePageGroupsComponent = ({
    handleSearchField, 
    handleSearchButton,
    HandleLogoutButton,
}) => {
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
                              className="btn btn-outline-primary" aria-pressed="true">
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
                              className="btn btn-primary">
                                Groups
                            </button>
                          </Link>
                        </li>
                        <li className="nav-item mt-4">
                          <Link className="col-2" to="/">
                            <button 
                              onClick={HandleLogoutButton} 
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
                      <button type="button" className="btn btn-primary col-lg-3 mb-2">Add new document</button>
                        <div className="input-group mb-3 col-lg-5">
                          <input type="text" className="form-control" placeholder="Document" aria-label="Document" aria-describedby="button-addon2"></input>
                          <div className="input-group-append">
                            <button className="btn btn-primary" type="button" id="button-addon2">Search</button>
                          </div>
                      </div>
                    </div>
                    <div className="col-12">
                    <table className="table">
                        <thead>
                            <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Group name</th>
                            <th scope="col">Group size</th>                            
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                            <th scope="row">1</th>
                            <td>CEO</td>
                            <td>1</td>                           
                            </tr>
                            <tr>
                            <th scope="row">2</th>
                            <td>HR</td>
                            <td>3</td>
                            </tr>
                            <tr>
                            <th scope="row">3</th>
                            <td>Team Lead</td>
                            <td>12</td>
                            </tr>
                            <tr>
                            <th scope="row">3</th>
                            <td>Slaves</td>
                            <td>120</td>
                            </tr>                            
                        </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AdminHomePageGroupsComponent;