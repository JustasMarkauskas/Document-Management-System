import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageGroupComponent from "./UserHomePageGroupComponent";

class UserHomePageGroupContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      groupNames: []
      //     inputUsername: ""
    };
  }

  getGroupNames = () => {
    axios
      .get(
        "http://localhost:8081/api/user/user-roles/" +
          this.props.match.params.username
      )
      .then(response => {
        this.setState({ groupNames: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };
  componentDidMount() {
    this.getGroupNames();
  }

  // handleActionClick = event => {
  //   event.preventDefault();
  //   this.props.history.push("/user-info"); //navigacija teisinga padaryti
  // };

  // handleSearchChange = event => {
  //   this.setState({ inputUsername: event.target.value });
  // };

  //   handleSearchButton = event => {
  //     event.preventDefault();
  //     axios
  //       .get("http://localhost:8081/api/user/" + this.state.inputUsername)
  //       .then(response => {
  //         this.setState({ users: [response.data] });
  //       })
  //       .catch(error => {
  //         console.log(error);
  //       });
  //     document.getElementById("adminUserSearchInput").value = "";
  //   };

  render() {
    const userInfo = this.state.groupNames.map((groupName, index) => (
      <UserHomePageGroupComponent
        key={index}
        rowNr={index + 1}
        groupName={groupName}
        handleActionClick={this.handleActionClick}
      />
    ));

    return (
      <div className="container">
        <div className="row ">
          <div className="input-group mb-3 col-lg-5">
            <input
              onChange={this.handleSearchChange}
              type="text"
              className="form-control"
              placeholder="Group name"
              aria-label="groupName"
              aria-describedby="button-addon2"
              id="userSearchGroupInput"
            ></input>
            <div className="input-group-append">
              <button
                className="btn btn-primary"
                type="button"
                id="userGroupSearchButton"
                onClick={this.handleSearchButton}
              >
                Search
              </button>
            </div>
          </div>
        </div>

        <table className="table">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Group Name</th>
              {/* <th scope="col">Size</th> */}
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>{userInfo}</tbody>
        </table>
      </div>
    );
  }
}

export default withRouter(UserHomePageGroupContainer);
