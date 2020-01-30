import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageUserTopButtonsComponents from './AdminHomePageUserTopButtonsComponents';


class AdminHomePageUserTopButtonsContainer extends React.Component {
    
    
    
  render(){
    return(
      <AdminHomePageUserTopButtonsComponents/>    
    );
  }
}
export default withRouter(AdminHomePageUserTopButtonsContainer);