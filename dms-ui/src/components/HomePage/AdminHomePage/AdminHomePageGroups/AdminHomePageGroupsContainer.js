import React from "react";
// import axios from "axios";
import { withRouter } from "react-router-dom";


import AdminHomePageGroupsComponent from "./AdminHomePageGroupsComponent"


class AdminHomePageGroupsContainer extends React.Component {



  render (){
    return(
      <AdminHomePageGroupsComponent
        
      />
    );
  }


}


export default withRouter (AdminHomePageGroupsContainer);