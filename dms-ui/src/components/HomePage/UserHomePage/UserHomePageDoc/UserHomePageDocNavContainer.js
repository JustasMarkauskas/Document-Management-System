import React from "react";
import { withRouter } from "react-router-dom";
import UserHomePageDocNavComponent from './UserHomePageDocNavComponents';

class UserHomePageDocNavContainer extends React.Component {
    
    
    
    render(){
      return(
        <UserHomePageDocNavComponent/>    
      );
    }
  }
  export default withRouter(UserHomePageDocNavContainer);