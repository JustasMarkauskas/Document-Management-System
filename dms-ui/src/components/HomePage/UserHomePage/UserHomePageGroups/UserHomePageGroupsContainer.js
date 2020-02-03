import React from "react";
import { withRouter } from "react-router-dom";
import UserHomepageComponents from "./UserHomePageComponents";


class UserHomePageContainer extends React.Component {



  render (){
    return(
      <UserHomepageComponents
        
      />
    );
  }


}


export default withRouter (UserHomePageContainer);