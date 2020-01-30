import React from "react";

import { withRouter } from "react-router-dom";
import AdminHomePageDocumentsNavComponent from './AdminHomePageDocumentsNavComponent';

class AdminHomePageDocumentsNavContainer extends React.Component {
    
    
    
    render(){
      return(
        <AdminHomePageDocumentsNavComponent/>    
      );
    }
  }
  export default withRouter(AdminHomePageDocumentsNavContainer);