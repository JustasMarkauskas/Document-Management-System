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

  onCheckboxChange = e => {
    const checkedUsers = this.state.checkedUsers;
    let index;

    // check if the check box is checked or unchecked
    if (e.target.checked) {
      // add the numerical value of the checkbox to options array
      checkedUsers.push(+e.target.value);
    } else {
      // or remove the value from the unchecked checkbox from the array
      index = checkedUsers.indexOf(+e.target.value);
      checkedUsers.splice(index, 1);
    }

    // update the state with the new array of options
    this.setState({ checkedUsers: checkedUsers });
  };

  onSaveClick = event => {
    event.preventDefault();
    console.log("state" + this.state.checkedUsers);
    // axios
    //   .put(
    //     "http://localhost:8081/api/user/add-users-to-group/" +
    //       this.props.groupName,
    //     {
    //       username: this.state.checkedUsers
    //     }
    //   )
    //   .then(res => {
    //     console.log(res);
    //     // this.closeModal();
    //     //  this.updateDocuments();
    //   })
    //   .catch(error => {
    //     console.log(error);
    //   });
  };

  render() {
    const allUsernames = this.state.users.map((user, index) =>
      this.contains(this.props.groupUsers, user) ? (
        <div className="input-group" key={index}>
          <label>
            <input
              className="form-check-input"
              type="checkbox"
              value={user}
              defaultChecked
              onChange={this.onCheckboxChange}
            />
            {"  " + user}
          </label>
        </div>
      ) : (
        <div className="input-group" key={index}>
          <label>
            <input
              className="form-check-input"
              type="checkbox"
              value={user}
              onChange={this.onCheckboxChange}
            />
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
