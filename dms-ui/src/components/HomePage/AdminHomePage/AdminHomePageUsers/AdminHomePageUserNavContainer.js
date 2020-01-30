import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageUserNavComponent from './AdminHomePageUserNavComponent';

class AdminHomePageUserNavContainer extends React.Component {
    
    
    
    render(){
      return(
        <AdminHomePageUserNavComponent/>    
      );
    }
  }
  export default withRouter(AdminHomePageUserNavContainer);