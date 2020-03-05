import React from "react";
import axios from "axios";
import serverUrl from "../../../URL/ServerUrl";

class AdminHomePageUsersComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      inputUsername: ""
    };
  }

  getUsers = () => {
    axios
      .get(serverUrl + "api/user")
      .then(response => {
        this.setState({ users: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <tr id={"userNr" + this.props.rowNr}>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.username}</td>
        <td>{this.props.firstName}</td>
        <td>{this.props.lastName}</td>
        <td>{this.props.comment}</td>
        <td>
          <button
            type="button"
            className="btn btn-primary"
            onClick={this.props.handleActionClick}
            data-toggle="modal"
            data-target="#userInfoModal"
            id={"userNr" + this.props.rowNr}
          >
            <i className="fas fa-cog"></i>
          </button>
        </td>
      </tr>
    );
  }
}

export default AdminHomePageUsersComponent;
