import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import AdminHomePageUsersComponent from "./AdminHomePageUsersComponent";

class AdminHomePageUsersContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: []
    };
  }

  getUsers = () => {
    axios
      .get("http://localhost:8081/api/user")
      .then(response => {
        this.setState({ users: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };
  componentDidMount() {
    this.getUsers();
  }

  handleActionClick = event => {
    event.preventDefault();
    this.props.history.push("/user-info"); //navigacija teisinga padaryti
  };

  render() {
    const userInfo = this.state.users.map((user, index) => (
      <AdminHomePageUsersComponent
        key={index}
        rowNr={index + 1}
        firstName={user.firstName}
        lastName={user.lastName}
        username={user.username}
        comment={user.comment}
        handleActionClick={this.handleActionClick}
      />
    ));

    return (
      <div className="container">
        <table className="table">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Name</th>
              <th scope="col">Surname</th>
              <th scope="col">Username</th>
              <th scope="col">Comment</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>{userInfo}</tbody>
        </table>
      </div>
    );
  }
}

export default withRouter(AdminHomePageUsersContainer);
