import React from "react";
import axios from "axios";

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
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getUsers();
  }

  contains(users, groupUser) {
    var i = users.length;
    while (i--) {
      if (users[i] === groupUser) {
        return true;
      }
    }
    return false;
  }

  onSaveClick = event => {
    event.preventDefault();
    axios
      .put(
        "http://localhost:8081/api/user/add-users-to-group/" +
          this.props.groupName,
        {
          username: this.state.users
        }
      )
      .then(res => {
        console.log(res);
        // this.closeModal();
        //  this.updateDocuments();
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    const allUsernames = this.state.users.map((user, index) =>
      this.contains(this.props.groupUsers, user) ? (
        <div className="checkbox" key={index}>
          <label>
            <input type="checkbox" value={user} defaultChecked />
            {"  " + user}
          </label>
        </div>
      ) : (
        <div className="checkbox" key={index}>
          <label>
            <input type="checkbox" value={user} />
            {"  " + user}
          </label>
        </div>
      )
    );

    return (
      <div className="container">
        {allUsernames}
        <div className="container mt-2">
          <div className="row">
            <button
              onClick={this.props.onSaveClick}
              type="button"
              className="btn btn-primary mr-2"
            >
              Save
            </button>
            <button
              onClick={this.props.onHide}
              type="button"
              className="btn btn-primary "
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    );
  }
}

export default AssignUserContainer;
