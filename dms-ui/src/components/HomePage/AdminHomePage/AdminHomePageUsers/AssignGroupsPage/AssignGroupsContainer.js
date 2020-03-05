import React from "react";
import axios from "axios";
import serverUrl from "../../../../URL/ServerUrl";

class AssignGroupsContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      groups: [],
      checkedGroups: []
    };
  }

  getGroups = () => {
    axios
      .get(serverUrl + "api/group/group-names")
      .then(response => {
        this.setState({ groups: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getGroups();
    this.setState({ checkedGroups: this.props.userGroups });
  }

  contains(groups, userGroups) {
    var i = groups.length;
    while (i--) {
      if (groups[i] === userGroups) {
        return true;
      }
    }
    return false;
  }

  onCheckboxClick = e => {
    const checkedGroups = this.state.checkedGroups;
    let index;

    if (e.target.checked) {
      checkedGroups.push(e.target.value);
    } else {
      index = checkedGroups.indexOf(e.target.value);
      checkedGroups.splice(index, 1);
    }
    this.setState({ checkedGroups: checkedGroups });
  };

  closeModal = this.props.onHide;

  onSaveClick = event => {
    event.preventDefault();
    const userData = new FormData();

    if (this.state.checkedGroups.length > 0) {
      var i;
      for (i = 0; i < this.state.checkedGroups.length; i++) {
        userData.append("groups", this.state.checkedGroups[i]);
      }
    } else {
      userData.append("groups", "");
    }

    axios
      .put(
        serverUrl + "api/user/update-user-groups/" + this.props.username,
        userData
      )
      .then(() => {
        this.closeModal();
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    const allUsernames = this.state.groups.map((group, index) =>
      this.contains(this.props.userGroups, group) ? (
        <div className="form-check" key={index}>
          <input
            className="form-check-input"
            type="checkbox"
            value={group}
            defaultChecked
            onClick={this.onCheckboxClick}
            id={"group" + index}
          />

          <label className="form-check-label" htmlFor={"group" + index}>
            {group}
          </label>
        </div>
      ) : (
        <div className="form-check" key={index}>
          <input
            className="form-check-input"
            type="checkbox"
            value={group}
            onClick={this.onCheckboxClick}
            id={"group" + index}
          />

          <label className="form-check-label" htmlFor={"group" + index}>
            {group}
          </label>
        </div>
      )
    );

    return (
      <div className="container" id="assignGroupsContainer">
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
              className="btn btn-secondary "
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    );
  }
}

export default AssignGroupsContainer;
