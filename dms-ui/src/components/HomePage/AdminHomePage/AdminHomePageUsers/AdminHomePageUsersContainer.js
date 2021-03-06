import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import { Modal } from "react-bootstrap";
import AdminHomePageUsersComponent from "./AdminHomePageUsersComponent";
import NewUserFormComponent from "../../../NewUserForm/NewUserFormComponent";
import serverUrl from "../../../URL/ServerUrl";
import ReactPaginate from "react-paginate";
import { store } from "react-notifications-component";

class AdminHomePageUsersContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      show: false,
      users: [],
      inputUsername: "",
      usernameForSearch: "",
      offset: 0,
      elements: [],
      perPage: 10,
      currentPage: 0,
      pageCount: 0,
      totalUsers: 0,
      isSearchElements: false
    };
  }
  successUserNotification = () =>
    store.addNotification({
      title: "Success!",
      message: "User created successfully",
      type: "success",
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  handleCloseModal = () => {
    this.setState({ show: false });
  };

  handleCloseModalAfterSubmit = () => {
    this.setState({ show: false }, this.getUsers());
    this.successUserNotification();
  };

  handleShowModal = () => {
    this.setState({ show: true });
  };

  getUsers = () => {
    axios
      .get(serverUrl + "api/user/page", {
        params: {
          page: this.state.currentPage,
          size: this.state.perPage
        }
      })
      .then(response => {
        let totalUsers = 0;
        if (response.data.length > 0) {
          totalUsers = response.data[0].totalUsers;
        }
        this.setState({
          pageCount: Math.ceil(totalUsers / this.state.perPage),
          elements: response.data,
          isSearchElements: false
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  handlePageClick = data => {
    const selectedPage = data.selected;
    const offset = selectedPage * this.state.perPage;
    this.setState({ currentPage: selectedPage, offset: offset }, () => {
      this.state.isSearchElements
        ? this.setElementsForSearchButton()
        : this.setElementsForCurrentPage();
    });
  };

  setElementsForCurrentPage = () => {
    axios
      .get(serverUrl + "api/user/page", {
        params: {
          page: this.state.currentPage,
          size: this.state.perPage
        }
      })
      .then(response => {
        let totalUsers = 0;
        if (response.data.length > 0) {
          totalUsers = response.data[0].totalUsers;
        }

        this.setState({
          pageCount: Math.ceil(totalUsers / this.state.perPage),
          elements: response.data,
          isSearchElements: false
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getUsers();
  }

  handleActionClick = (event, username) => {
    event.preventDefault();
    this.props.history.push("/user-review/" + username);
  };

  handleSearchChange = event => {
    this.setState({ inputUsername: event.target.value });
  };

  handleSearchButton = event => {
    event.preventDefault();
    if (this.state.inputUsername.length < 1) {
      this.setState({ isSearchElements: true, currentPage: 0 }, () => {
        this.getUsers();
      });
    } else {
      this.setState(
        {
          inputUsername: "",
          usernameForSearch: this.state.inputUsername,
          currentPage: 0
        },
        () => {
          this.setElementsForSearchButton();
        }
      );

      document.getElementById("adminUserSearchInput").value = "";
    }
  };

  setElementsForSearchButton = () => {
    axios
      .get(
        serverUrl + "api/user/page/containing/" + this.state.usernameForSearch,
        {
          params: {
            page: this.state.currentPage,
            size: this.state.perPage
          }
        }
      )
      .then(response => {
        let totalUsers = 0;
        if (response.data.length > 0) {
          totalUsers = response.data[0].totalUsers;
        }
        this.setState({
          pageCount: Math.ceil(totalUsers / this.state.perPage),
          elements: response.data,
          offset: 0,
          isSearchElements: true
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  checkIfEnter = event => {
    var code = event.keyCode || event.which;
    if (code === 13) {
      this.handleSearchButton(event);
    }
  };

  render() {
    let paginationElement;
    if (this.state.pageCount > 1) {
      paginationElement = (
        <ReactPaginate
          previousLabel={"← Previous"}
          nextLabel={"Next →"}
          breakLabel={<span className="gap">...</span>}
          pageCount={this.state.pageCount}
          onPageChange={this.handlePageClick}
          forcePage={this.state.currentPage}
          breakClassName={"page-item"}
          breakLinkClassName={"page-link"}
          containerClassName={"pagination"}
          pageClassName={"page-item"}
          pageLinkClassName={"page-link"}
          previousClassName={"page-item"}
          previousLinkClassName={"page-link"}
          nextClassName={"page-item"}
          nextLinkClassName={"page-link"}
          activeClassName={"active"}
        />
      );
    }

    const userInfo = this.state.elements.map((user, index) => (
      <AdminHomePageUsersComponent
        key={index}
        rowNr={index + 1 + this.state.currentPage * this.state.perPage}
        firstName={user.firstName}
        lastName={user.lastName}
        username={user.username}
        comment={user.comment}
        userGroups={user.userGroups}
        handleActionClick={event =>
          this.handleActionClick(event, user.username)
        }
      />
    ));

    return (
      <div className="container">
        <div className="row d-flex">
          <div className="col-lg-4 col-md-12 mb-3">
            <button
              onClick={this.handleShowModal}
              type="button"
              className="main-btn btn-color col-lg-11 col-md-12 "
              id="adminAddNewUserButton"
            >
              Add new user
            </button>
          </div>
          <Modal show={this.state.show} onHide={this.handleCloseModal}>
            <Modal.Header closeButton
		className="modals-header">
              <Modal.Title>Create New User</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <NewUserFormComponent
                onCloseModal={this.handleCloseModal}
                onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit}
              />
            </Modal.Body>
          </Modal>
          <div className="input-group mb-3 col-lg-4 col-md-12 ml-auto">
            <input
              onChange={this.handleSearchChange}
              onKeyPress={this.checkIfEnter}
              type="text"
              className="form-control"
              placeholder="User info"
              aria-label="username"
              aria-describedby="button-addon2"
              id="adminUserSearchInput"
            ></input>
            <div className="input-group-append">
              <button
                onClick={this.handleSearchButton}
                className="search-btn btn-color"
                type="button"
                id="adminUserSearchButton"
              >
                Search
              </button>
            </div>
          </div>
        </div>
        <div className="table-responsive">
          <table className="table table-striped table-dark">
            <thead className="table-head">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Username</th>
                <th scope="col">First name</th>
                <th scope="col">Last name</th>
                <th scope="col">Comment</th>
                <th className="text-right" scope="col">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody>{userInfo}</tbody>
          </table>
        </div>
        <div id="userTablePagination">{paginationElement}</div>
      </div>
    );
  }
}

export default withRouter(AdminHomePageUsersContainer);
