import React from "react";
// import axios from "axios";
import { withRouter } from "react-router-dom";

import AdminHomePageComponent from "./AdminHomePageComponents";


class AdminHomePageContainer extends React.Component {



  render (){
    return(
      <AdminHomePageComponent
        
      />
    );
  }


}


export default withRouter (AdminHomePageContainer);