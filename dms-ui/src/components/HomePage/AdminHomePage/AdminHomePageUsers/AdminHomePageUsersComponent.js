import React from "react";
import { Modal } from 'react-bootstrap';
import UserInfoComponent from '../../../UserInfo/UserInfoComponent';

class AdminHomePageUsersComponent extends React.Component {
  // Svarbus sitas
  constructor(props) {
    super(props);

    this.handleShowModal = this.handleShowModal.bind(this);
    this.handleCloseModal = this.handleCloseModal.bind(this);
    // this.handleCloseModalAfterSubmit = this.handleCloseModalAfterSubmit.bind(this);

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

  // handleCloseModalAfterSubmit() {    
  //   this.refresh();    
  //   this.setState({ show: false });     
	// }

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
            // onClick={this.props.handleActionClick}
            id={"userNr"+this.props.rowNr}
          >
            <i className="fas fa-cog"></i>
          </button>

          <Modal show={this.state.show} onHide={this.handleCloseModal}>
		        <Modal.Header closeButton>
		          <Modal.Title>User Infomation</Modal.Title>
		        </Modal.Header>
		        <Modal.Body> 
                           
                      <h1>Bandymas</h1>     
                           {/* Cia visa modalo info turetu buti sukelta su validacijomis */}
            </Modal.Body>  
	        </Modal>


          {/* User Info Modal */}
          {/* <div className="modal fade" id="userInfoModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog" role="document">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title" id="exampleModalLabel">Information about: </h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div className="modal-body">
                  ...
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                  <button type="button" className="btn btn-primary">Save changes</button>
                </div>
              </div>
            </div>
          </div> */}
          {/* Modal ends here */}
        </td>
      </tr>
    );
  }
}

export default AdminHomePageUsersComponent;
