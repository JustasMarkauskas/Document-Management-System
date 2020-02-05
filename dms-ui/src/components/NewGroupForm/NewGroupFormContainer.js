import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import NewGroupFormComponent from "./NewGroupFormComponent";

class NewGroupFormContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = { groupName: "" };
  }

  handleCancel = event => {
    event.preventDefault();
    this.props.history.push("/adminhomepage-groups");
  };

  handleSubmit = event => {
    event.preventDefault();
    axios      
      .post("http://localhost:8081/api/role/", { // pagal springa ne iki galo supratau kur tos grupes bus
        groupName: this.state.groupName,

      })
      .then(response => {
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });

    this.setState({
        groupName: "",        
    });

    this.props.history.push("/adminhomepage-groups");
  };

  render() {
    return (
      <NewGroupFormComponent        
        handleSubmit={this.handleSubmit}
        handleCancel={this.handleCancel}
        groupName={this.state.groupName}
      />
    );
  }
}

export default withRouter(NewGroupFormContainer);