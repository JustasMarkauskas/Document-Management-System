import React from "react";
import { Button } from 'react-bootstrap';
import { Modal } from 'react-bootstrap';
import NewUserFormContainer from './NewUserFormContainer'


class AddNewUserContainer extends React.Component {
  constructor(props, context) {
		super(props, context);

		this.handleShow = this.handleShow.bind(this);
		this.handleClose = this.handleClose.bind(this);

		this.state = {
			show: false,
		};
	}

	handleClose() {
		this.setState({ show: false });
	}

	handleShow() {
		this.setState({ show: true });
	}
  render(){  
    return (
      <div className="container">
      <Button variant="primary" onClick={this.handleShow}>
					Create New User
        </Button>
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
  
  

// const AddNewUserComponent = ({ handleAddNewUserClick }) => {
//   return (
//     <button className="btn btn-primary" onClick={handleAddNewUserClick}>
//       Create new user
//     </button>
//   );
// };

export default AddNewUserContainer;