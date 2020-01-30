import React from "react";
// import axios from "axios";
import { withRouter } from "react-router-dom";

import AdminHomePageDocumentComponent from "./AdminHomePageDocumentComponent";


class AdminHomePageDocumentContainer extends React.Component {



  render (){
    return(
      <AdminHomePageDocumentComponent
        
      />
    );
  }


}


export default withRouter (AdminHomePageDocumentContainer);