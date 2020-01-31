import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageGroupTopButtonsComponent from './AdminHomePageGroupTopButtonsComponent';


class AdminHomePageGroupTopButtonsContainer extends React.Component {
    
    
    
  render(){
    return(
      <AdminHomePageGroupTopButtonsComponent/>    
    );
  }
}
export default withRouter(AdminHomePageGroupTopButtonsContainer);