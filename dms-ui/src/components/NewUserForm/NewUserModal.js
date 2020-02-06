import React from "react";
import { Modal } from 'react-bootstrap';
import NewUserFormContainer from './NewUserFormContainer'


class UserModal extends React.Component {
    constructor(props, context){
      super(props, context);
      this.handleShow = this.handleShow.bind(this);
      this.handleClose = this.handleClose.bind(this);
      this.state = {
          show: false
      }
  }
  
  handleShow() {
      console.log(this.state)
      this.setState({ show: true })
  }
  handleClose(){
      this.setState({ show: false })
  }
  render() {
    return (
        <div>
        <Modal show={this.state.show} onHide={this.handleClose}>
					<Modal.Header closeButton>
						<Modal.Title>Create New User</Modal.Title>
					</Modal.Header>
					<Modal.Body> <NewUserFormContainer/> </Modal.Body>					
		</Modal>
        </div>
      
    );
  }
}
  
  export default UserModal;  