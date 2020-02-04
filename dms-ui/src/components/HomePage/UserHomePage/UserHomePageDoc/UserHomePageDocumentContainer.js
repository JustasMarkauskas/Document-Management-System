import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import UserHomePageDocumentComponent from "./UserHomePageDocumentcomponent";

class UserHomePageDocumentContainer extends React.Component {
//   constructor(props) {
//     super(props);
//     this.state = {
//       users: [],
//       inputUsername: ""
//     };
//   }

//   getUsers = () => {
//     axios
//       .get("http://localhost:8081/api/user")
//       .then(response => {
//         this.setState({ users: response.data });
//       })
//       .catch(error => {
//         console.log(error);
//       });
//   };
//   componentDidMount() {
//     this.getUsers();
//   }

//   handleActionClick = event => {
//     event.preventDefault();
//     this.props.history.push("/user-info"); //navigacija teisinga padaryti
//   };

//   handleAddUserButton = event => {
//     event.preventDefault();
//     this.props.history.push("/new-user"); //navigacija teisinga padaryti
//   };

//   handleSearchChange = event => {
//     this.setState({ inputUsername: event.target.value });
//   };

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
    // const userInfo = this.state.users.map((user, index) => (
    //   <UserHomePageDocumentComponent
    //     key={index}
    //     rowNr={index + 1}
    //     firstName={user.firstName}
    //     lastName={user.lastName}
    //     username={user.username}
    //     comment={user.comment}
    //     handleActionClick={this.handleActionClick}
    //   />
    // ));

    return (
      <div className="container">
        <div className="row ">
          <button
            onClick={this.handleAddNewDocumentButton}
            type="button"
            className="btn btn-primary col-lg-2 mb-2"
            id="userAddNewDocumentButton"
          >
            Add New Document
          </button>
          <div className="input-group mb-3 col-lg-5">
            <input
              onChange={this.handleSearchChange}
              type="text"
              className="form-control"
              placeholder="Username"
              aria-label="username"
              aria-describedby="button-addon2"
              id="userSearchDocumentInput"
            ></input>
            <div className="input-group-append">
              <button
                className="btn btn-primary"
                type="button"
                id="userDocumentSearchButton"
                onClick={this.handleSearchButton}
              >
                Search
              </button>
            </div>
          </div>
          <button
            onClick={this.handleDownloadDocumentButton}
            type="button"
            className="btn btn-primary col-lg-2 mb-2"
            id="downloadDocumentsButton"
          >
            Download
          </button>
        </div>

        <table className="table">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Document Name</th>
              <th scope="col">Status</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          {/* <tbody>{userInfo}</tbody> */}
        </table>
      </div>
    );
  }
}

export default withRouter(UserHomePageDocumentContainer);