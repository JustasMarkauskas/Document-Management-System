import React from "react";
import axios from "axios";
import AssignUserComponent from "./AssignUserComponent";

class AssignUserContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: []
    };
  }

  getUsers = () => {
    axios
      .get("http://localhost:8081/api/user/usernames/")
      .then(response => {
        this.setState({ users: response.data });
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getUsers();
  }

  render() {
    const allUsernames = this.state.users.map((user, index) => (
      <AssignUserComponent username={user} key={index} />
    ));

    return <div>{allUsernames}</div>;
  }
}

export default AssignUserContainer;
