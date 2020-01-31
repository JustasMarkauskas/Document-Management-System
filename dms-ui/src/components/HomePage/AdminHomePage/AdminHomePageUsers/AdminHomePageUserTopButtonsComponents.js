import React from "react";

const AdminHomePageTopButtonsComponent = ({
    handleSearchChange, 
    searchValue, 
    handleSearchButton, 
    handleAddUserButton,}) => {
        return(
            <div className="row col-lg-12">
                <button
                onClick={handleAddUserButton} 
                type="button" 
                className="btn btn-primary col-lg-2 mb-2"
                id="adminAddNewUserButton">
                    Add new user
                </button>
                <div className="input-group mb-3 col-lg-5">
                    <input
                    onChange={handleSearchChange}
                    value={searchValue} 
                    type="text" 
                    className="form-control" 
                    placeholder="Username" 
                    aria-label="username" 
                    aria-describedby="button-addon2"
                    id="adminUserSearchInput">
                    </input>
                    <div className="input-group-append">
                    <button 
                        className="btn btn-primary" 
                        type="button" 
                        id="adminUserSearchButton"
                        onClick={handleSearchButton}
                        >
                        Search
                    </button>
                    </div>
                </div>
            </div>
        );
    }

export default AdminHomePageTopButtonsComponent;

