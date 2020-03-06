import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import { Modal } from "react-bootstrap";
import AdminHomePageUsersComponent from "./AdminHomePageUsersComponent";
import NewUserFormComponent from "../../../NewUserForm/NewUserFormComponent";
import serverUrl from "../../../URL/ServerUrl";

class AdminHomePageUsersContainerPag extends React.Component {
  constructor(props) {
    super(props);

    this.handleShowModal = this.handleShowModal.bind(this);
    this.handleCloseModal = this.handleCloseModal.bind(this);
    this.handleCloseModalAfterSubmit = this.handleCloseModalAfterSubmit.bind(
      this
    );

    this.state = {
      show: false,
      users: [],
      inputUsername: "",
      currentPage: 1,
      todosPerPage: 3,
      todos: ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"]
    };
  }

  handleClick = event => {
    this.setState({
      currentPage: Number(event.target.id)
    });
  };

  refresh() {
    this.getUsers();
    window.location.reload();
  }

  handleCloseModal() {
    this.setState({ show: false });
  }

  handleCloseModalAfterSubmit() {
    this.refresh();
    this.setState({ show: false });
  }

  handleShowModal() {
    this.setState({ show: true });
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
    axios
      .get(serverUrl + "api/user/" + this.state.inputUsername)
      .then(response => {
        this.setState({ users: [response.data] });
      })
      .catch(error => {
        console.log(error);
      });
    document.getElementById("adminUserSearchInput").value = "";
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
        userGroups={user.userGroups}
        handleActionClick={event =>
          this.handleActionClick(event, user.username)
        }
      />
    ));

    const { users, currentPage, todosPerPage } = this.state;

    // Logic for displaying todos
    const indexOfLastTodo = currentPage * todosPerPage;
    const indexOfFirstTodo = indexOfLastTodo - todosPerPage;
    const currentTodos = users.slice(indexOfFirstTodo, indexOfLastTodo);

    const renderTodos = currentTodos.map((user, index) => (
      <AdminHomePageUsersComponent
        key={index}
        rowNr={index + 1}
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

    // Logic for displaying page numbers
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(users.length / todosPerPage); i++) {
      pageNumbers.push(i);
    }

    const renderPageNumbers = pageNumbers.map(number => {
      return (
        <button key={number} id={number} onClick={this.handleClick}>
          {number}
        </button>
      );
    });

    return (
      <div className="container">
        <div className="row ">
          <button
            onClick={this.handleShowModal}
            type="button"
            className="btn btn-primary col-lg-2 mb-2"
            id="adminAddNewUserButton"
          >
            Add new user
          </button>
          <Modal show={this.state.show} onHide={this.handleCloseModal}>
            <Modal.Header closeButton>
              <Modal.Title>Create New User</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              {" "}
              <NewUserFormComponent
                onCloseModal={this.handleCloseModal}
                onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit}
              />
            </Modal.Body>
          </Modal>
          <div className="input-group mb-3 col-lg-5">
            <input
              onChange={this.handleSearchChange}
              type="text"
              className="form-control"
              placeholder="Username"
              aria-label="username"
              aria-describedby="button-addon2"
              id="adminUserSearchInput"
            ></input>
            <div className="input-group-append">
              <button
                className="btn btn-primary"
                type="button"
                id="adminUserSearchButton"
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
              <th scope="col">Username</th>
              <th scope="col">First name</th>
              <th scope="col">Last name</th>
              <th scope="col">Comment</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>{renderTodos}</tbody>
        </table>
        <div>
          <ul>{renderTodos}</ul>
          <ul id="page-numbers">{renderPageNumbers}</ul>
        </div>
      </div>
    );
  }
}

export default withRouter(AdminHomePageUsersContainerPag);
