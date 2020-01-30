import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageGroupComponent from "./AdminHomePageGroupComponent";

class AdminHomePageGroupContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      groups: []
    };
  }

  getGroups = () => {
    axios
      .get("http://localhost:8081/api/group")
      .then(response => {
        this.setState({ groups: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };
  componentDidMount() {
    this.getGroups();
  }

  handleActionClick = event => {
    event.preventDefault();
    this.props.history.push("/group-info"); //navigacija teisinga padaryti
  };

  render() {
    const groupInfo = this.state.groups.map((group, index) => (
      <AdminHomePageGroupComponent
        key={index}
        rowNr={index + 1}
        groupName={group.groupName}
        groupSize={group.groupSize}        
        comment={group.comment}
        handleActionClick={this.handleActionClick}
      />
    ));

    return (
      <div className="container">
        <table className="table">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Group Name</th>
              <th scope="col">Group Size</th>              
              <th scope="col">Comment</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>{groupInfo}</tbody>
        </table>
      </div>
    );
  }
}

export default withRouter(AdminHomePageGroupContainer);
