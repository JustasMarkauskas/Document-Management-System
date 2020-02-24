import React from "react";
import axios from "axios";

class AssignUserContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      checkedUsers: []
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
    this.setState({ checkedUsers: this.props.groupUsers });
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

  onCheckboxClick = e => {
    const checkedUsers = this.state.checkedUsers;
    let index;

    if (e.target.checked) {
      checkedUsers.push(e.target.value);
    } else {
      index = checkedUsers.indexOf(e.target.value);
      checkedUsers.splice(index, 1);
    }
    this.setState({ checkedUsers: checkedUsers });
  };

  onSaveClick = event => {
    event.preventDefault();

    const userData = new FormData();
    var i;
    for (i = 0; i < this.state.checkedUsers.length; i++) {
      userData.append("usernames", this.state.checkedUsers[i]);
    }

    axios
      .put(
        "http://localhost:8081/api/user/add-users-to-group/" +
          this.props.groupName,
        userData
      )
      .then(response => {
        console.log(response);
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
        <div className="form-check" key={index}>
          <input
            className="form-check-input"
            type="checkbox"
            value={user}
            defaultChecked
            onClick={this.onCheckboxClick}
            id={"user" + index}
          />

          <label className="form-check-label" htmlFor={"user" + index}>
            {user}
          </label>
        </div>
      ) : (
        <div className="form-check" key={index}>
          <input
            className="form-check-input"
            type="checkbox"
            value={user}
            onClick={this.onCheckboxClick}
            id={"user" + index}
          />

          <label className="form-check-label" htmlFor={"user" + index}>
            {user}
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
              onClick={this.onSaveClick}
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
