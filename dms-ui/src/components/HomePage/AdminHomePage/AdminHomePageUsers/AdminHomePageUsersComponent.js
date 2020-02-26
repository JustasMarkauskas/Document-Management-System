import React from "react";
import { Modal } from 'react-bootstrap';
import UserInfoComponent from '../../../UserInfo/UserInfoComponent';
import axios from "axios";

class AdminHomePageUsersComponent extends React.Component {
  // Svarbus sitas
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
  


  render() {
    return (
      <tr>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.firstName}</td> 
        <td>{this.props.lastName}</td>
        <td>{this.props.username}</td>
        <td>{this.props.comment}</td>
        <td>
          <button
            type="button"
            className="btn btn-primary"
            onClick={this.handleShowModal}
            data-toggle="modal" data-target="#userInfoModal"
            id={"userNr"+this.props.rowNr}
          >
            <i className="fas fa-cog"></i>
          </button>
          <Modal show={this.state.show} onHide={this.handleCloseModal}>
		        <Modal.Header closeButton>
		          <Modal.Title>User Infomation</Modal.Title>
		        </Modal.Header>
		        <Modal.Body>                         
            <UserInfoComponent 
              onCloseModal={this.handleCloseModal} 
              refresh={this.refresh} 
              onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit} 
              username={this.props.username} 
              firstName={this.props.firstName} 
              lastName={this.props.lastName} 
              comment={this.props.comment}
            />
            </Modal.Body>  
	        </Modal>          
        </td>
      </tr>
    );
  }
}

export default AdminHomePageUsersComponent;
