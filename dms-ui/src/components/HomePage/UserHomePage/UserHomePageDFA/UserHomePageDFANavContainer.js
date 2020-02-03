import React from "react";
import { withRouter } from "react-router-dom";
import UserHomePageDFANavComponent from './UserHomePageDFANavComponents';

class UserHomePageDFANavContainer extends React.Component {
    
    
    
    render(){
      return(
        <UserHomePageDFANavComponent/>    
      );
    }
  }
  export default withRouter(UserHomePageDFANavContainer);