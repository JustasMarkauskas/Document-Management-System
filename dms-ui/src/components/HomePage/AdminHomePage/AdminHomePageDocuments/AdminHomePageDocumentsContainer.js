import React from "react";
// import axios from "axios";
import { withRouter } from "react-router-dom";

import AdminHomePageDocumentsComponent from "./AdminHomePageDocumentsComponent";


class AdminHomePageDocumentsContainer extends React.Component {



  render (){
    return(
      <AdminHomePageDocumentsComponent
        
      />
    );
  }


}


export default withRouter (AdminHomePageDocumentsContainer);