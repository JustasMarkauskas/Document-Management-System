import React from "react";
import { Link } from "react-router-dom";

const AdminHomePageDocumentsNavComponent =({
    handleLogoutButton})=>{
      return(
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
                className="btn btn-primary">
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
      );
    }
    
    export default AdminHomePageDocumentsNavComponent;