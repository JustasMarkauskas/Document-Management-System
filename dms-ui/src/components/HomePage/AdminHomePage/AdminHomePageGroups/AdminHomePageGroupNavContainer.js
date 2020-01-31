import React from "react";

import { withRouter } from "react-router-dom";
import AdminHomePageGroupNavComponent from './AdminHomePageGroupNavcomponent';

class AdminHomePageGroupNavContainer extends React.Component {
    
    
    
    render(){
      return(
        <AdminHomePageGroupNavComponent/>    
      );
    }
  }
  export default withRouter(AdminHomePageGroupNavContainer);