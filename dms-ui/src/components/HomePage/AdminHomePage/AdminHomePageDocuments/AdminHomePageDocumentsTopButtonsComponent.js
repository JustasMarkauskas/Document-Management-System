import React from "react";

const AdminHomePageDocumentsTopButtonsComponent = ({
    handleSearchChange, 
    searchValue, 
    handleSearchButton, 
    handleAddDocumentButton,}) => {
        return(
            <div className="row col-lg-12">
              <button 
                onClick={handleAddDocumentButton}
                type="button" 
                className="btn btn-primary col-lg-3 mb-2">
                  Add new Document
              </button>
              <div className="input-group mb-3 col-lg-5">
                <input
                  onChange={handleSearchChange}
                  value={searchValue} 
                  type="text" 
                  className="form-control" 
                  placeholder="Document" 
                  aria-label="Document" 
                  aria-describedby="button-addon2">
                </input>
                <div className="input-group-append">
                  <button
                    onClick={handleSearchButton} 
                    className="btn btn-primary" 
                    type="button" 
                    id="button-addon2">
                    Search
                  </button>
                </div>
              </div>
            </div>
        );
    }

export default AdminHomePageDocumentsTopButtonsComponent;

