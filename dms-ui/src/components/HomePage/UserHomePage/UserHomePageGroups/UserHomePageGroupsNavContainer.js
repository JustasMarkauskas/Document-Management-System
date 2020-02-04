import React from "react";
import { withRouter } from "react-router-dom";
import UserHomePageGroupsNavComponent from './UserHomePageGroupsNavComponents';

class UserHomePageGroupsNavContainer extends React.Component {
    
    
    
    render(){
      return(
        <UserHomePageGroupsNavComponent/>    
      );
    }
  }
  export default withRouter(UserHomePageGroupsNavContainer);