import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageDocumentsTopButtonsComponent from './AdminHomePageDocumentsTopButtonsComponent';


class AdminHomePageDocumentsTopButtonsContainer extends React.Component {
    
    
    
  render(){
    return(
      <AdminHomePageDocumentsTopButtonsComponent/>    
    );
  }
}
export default withRouter(AdminHomePageDocumentsTopButtonsContainer);