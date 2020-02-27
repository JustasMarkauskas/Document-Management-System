import React from "react";
import { withRouter } from "react-router-dom";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import { Modal } from 'react-bootstrap';
import * as yup from "yup";
import axios from "axios";
import PasswordChangeComponent from "../PasswordChange/PasswodChange";

// Kolkas neveikia, issiaiskinti kodel...
const schema = yup.object({
  firstName: yup
    .string()
    .trim()
    .min(1, "Must be 1 characters or more")
    .max(30, "Must be 30 characters or less")
    .required("Please enter a first name")
    .matches(/^[A-Za-z\s-]+$/, "Only uppercase, lowercase letters and '-', space symbols are allowed"),
  lastName: yup
    .string()
    .trim()
    .min(1, "Must be 1 characters or more")
    .max(30, "Must be 30 characters or less")
    .required("Please enter a last name")
    .matches(/^[A-Za-z\s-]+$/, "Only uppercase, lowercase letters and '-', space symbols are allowed"),
  comment: yup
    .string()
    .trim()
    .max(50, "Must be 50 characters or less")
});

class UserInfoComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      show: false,
      users: [],
      inputUsername: "",
      firstName: "",
      lastName: "",
      comment:""
    };
  }
  // Priskiriam state reiksmes jei keisim tik viena elementa, priesingu atveju meta klaida, reiksmes ateina is
  // AdminHomePageUsersComponent.js

  componentDidMount(){
    this.setState({ 
      firstName: this.props.firstName, 
      lastName: this.props.lastName, 
      comment: this.props.comment
    })
  }

  // Funkcija atrefreshinti tinklapi po nauju duomenu pateikimo.
  // refresh(){
  //   this.getUsers();
  //   window.location.reload();
  // }

  //Funkcija uzdaryti modala be saugojimo ir atrefreshinti puslapi.
  handleCloseModal=()=> {
    this.setState({ show: false });
    this.refresh();      
  }
 
  // Password change modalo cancel
  handlePassCloseModal=()=>{
    this.setState({ show: false });
  }
  // Password change modalo save
  handlePassSaveCloseModal=()=>{
    this.setState({ show: false });
  }
    
//Funkcija parodyti modala  
  handleShowPasswordChangeModal=()=> {
	this.setState({ show: true });
	}
// situs padarom kad nusiustu i state
  handleFirstNameChange = event => {
    this.setState({firstName: event.target.value });
  };

  handleLastNameChange = event =>{
    this.setState({lastName: event.target.value});
  };

  handleCommentChange = event =>{
    this.setState({comment: event.target.value});
  };
//

// onCloseModalAfterSubmit funkcija ateina is AdminHomePageUsersComponent.js jinai dedama i updateUser funkcija,
// kur po informacijos nusiuntimo i DB modalas uzdaromas ir perkraunamas pagrindinis langas atsinaujinti informacijai
closeModal = this.props.onCloseModalAfterSubmit;
 
// duomenu nusiuntimas i DB pagal username.
  updateUser = event => {
    event.preventDefault();
    axios
      .put(
        "http://localhost:8081/api/user/update-user-info/" + this.props.username,
        {
          comment: this.state.comment,
          firstName: this.state.firstName,
          lastName: this.state.lastName,
          password: "Stingaaa1",
          username: "String"  
        }
      )
      .then(() => {
        this.closeModal();
         
      })
      .catch(error => {
        console.log(error);
      });
  };

  render(){
    return(
      <Formik
      validationSchema={schema}
      // onSubmit={handleSubmit}
      initialValues={{        
        firstName: "",
        lastName: "",
        comment: ""       
      }}
    >
      {({ handleSubmit, handleChange, handleFirstNameChange, values, isValid, errors }) => (
        <div className="NewUserForm">
          <Form noValidate onSubmit={handleSubmit}>
          {/* User info */}


          <div className="row" >
            <div className="d-flex  mb-2 justify-content-center  col-12">
              <h2>User Name:{this.props.username}</h2>
            </div>
            <div className="row d-flex justify-content-center  col-12  m-0">                            
              <div className="col-5 d-flex flex-column col-6">
                <div>
                <Form.Group>
                <label htmlFor="input">First Name</label>
                  <Form.Control
                    type="firstname"                    
                    defaultValue={this.props.firstName}                    
                    onChange={this.handleFirstNameChange} 
                    name="firstName"
                    id="firstName"
                    className="NewUserForm"
                    size="lg"
                    isInvalid={!!errors.firstName}
                  />
                  <Form.Control.Feedback className="FeedBack" type="invalid">
                    {errors.firstName}
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group>
                  <label htmlFor="input">Last Name</label>
                  <Form.Control
                    type="lastname"                    
                    defaultValue={this.props.lastName}                    
                    onChange={this.handleLastNameChange}
                    name="lastName"
                    id="lastName"
                    className="NewUserForm"
                    size="lg"
                    isInvalid={!!errors.lastName}
                  />
                  <Form.Control.Feedback className="FeedBack" type="invalid">
                    {errors.lastName}
                  </Form.Control.Feedback>
                </Form.Group>                  
                  <button 
                    type="button" 
                    className="col-12 mb-1 btn btn-primary btn-sm"
                    onClick={this.handleShowModal}
                  >
                    Assign to group
                  </button>
                   
                    {/* Assign to group modal begining */}
                    <Modal show={this.state.show} onHide={this.handleCloseModal}>
                      <Modal.Header closeButton>
                        <Modal.Title>Assign to groups</Modal.Title>
                      </Modal.Header>
                      <Modal.Body>     
                      <h2>bandymas</h2>                    
                      {/* <UserInfoComponent onCloseModal={this.handleCloseModal} onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit}  /> */}
                      </Modal.Body>  
                    </Modal>
                    {/* Assign to group modal end */}

                  <button 
                    type="button" 
                    className="col-12 btn btn-primary btn-sm"
                    onClick={this.handleShowPasswordChangeModal}
                  >
                    Change password
                  </button>                      
                    {/* Change password modal begining */}
                    <Modal show={this.state.show} onHide={this.handleCloseModal}> 
                      <Modal.Header closeButton>
                        <Modal.Title>Change password</Modal.Title>
                      </Modal.Header>
                      <Modal.Body>                                           
                      <PasswordChangeComponent 
                        onCloseModal={this.handlePassCloseModal} 
                        onCloseModalAfterSubmit={this.handlePassSaveCloseModal} 
                        username={this.props.username} />
                      </Modal.Body>  
                    </Modal>
                    {/* Change password modal end */}

                </div>
              </div>
              <div className="col-6  m-0">
                <ul className="list-group">
                  <li className="list-group-item">Group1</li>
                  <li className="list-group-item">Group2</li>
                  <li className="list-group-item">Group3</li>
                  <li className="list-group-item">Group4</li>
                  <li className="list-group-item">Group5</li>
                </ul>
              </div>                            
            </div>
            <div className="row d-flex flex-row justify-content-between align-items-center col-12  m-0">
              <div className="form-check col-4">
                <input className="form-check-input" type="checkbox" value="" id="defaultCheck1"></input>
                <label className="form-check-label" htmlFor="defaultCheck1">
                  Deactivate
                </label>
              </div>
              <Form.Group className="col-6">              
                <Form.Control
                  as="textarea"
                  rows="2"
                  className="NewUserForm col-12"
                  size="lg"
                  name="comment"
                  onChange={this.handleCommentChange}
                  type="comment"
                  id="comment"
                  defaultValue={this.props.comment}
                  placeholder="Comment"
                  isInvalid={!!errors.comment}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  {errors.comment}
                </Form.Control.Feedback>
              </Form.Group>
            </div>
          </div>
        <div class="modal-footer d-flex shadow-sm  bg-light rounded justify-content-center align-items-center col-12">
          <Button
            disabled={!isValid}
            onClick={this.updateUser}
            variant="primary"
            className="SubmitButton mr-2 col-4"
            type="button"
          >
            Submit
          </Button>
          <Button 
           onClick={this.props.onCloseModal} 
           variant="secondary" 
           className="col-4"
          >
             Cancel
          </Button>         
        </div>
        {/* userinfo */} 
        </Form>
        </div>
      )}
    </Formik>
    );
  }



}




export default withRouter(UserInfoComponent);
