import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import { Modal } from 'react-bootstrap';
import AdminHomePageUsersComponent from "./AdminHomePageUsersComponent";
import NewUserFormComponent from '../../../NewUserForm/NewUserFormComponent'

class AdminHomePageUsersContainer extends React.Component {
  
  // Siti butini :D 
  
  constructor(props) {
    super(props);

    this.handleShowModal = this.handleShowModal.bind(this);
    this.handleCloseModal = this.handleCloseModal.bind(this);
    this.handleCloseModalAfterSubmit = this.handleCloseModalAfterSubmit.bind(this);

    this.state = {
      show: false,
      users: [],
      inputUsername: ""
    };
  }
  refresh(){
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

  // handleActionClick = event => {
  //   event.preventDefault();
  //   this.props.history.push("/user-info"); //navigacija teisinga padaryti
  // };

  handleAddUserButton = event => {
    event.preventDefault();
    this.props.history.push("/new-user"); //navigacija teisinga padaryti
  };

  handleSearchChange = event => {
    this.setState({ inputUsername: event.target.value });
  };

  handleSearchButton = event => {
    event.preventDefault();
    axios
      .get("http://localhost:8081/api/user/" + this.state.inputUsername)
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
        handleShowModal={this.handleShowModal}
      />
    ));

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
              <NewUserFormComponent 
                onCloseModal={this.handleCloseModal} 
                onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit}  />             
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
