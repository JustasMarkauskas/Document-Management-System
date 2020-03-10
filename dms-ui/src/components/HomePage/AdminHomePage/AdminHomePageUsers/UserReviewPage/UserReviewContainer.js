import React from "react";
import { withRouter } from "react-router-dom";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import { Modal } from "react-bootstrap";
import * as yup from "yup";
import axios from "axios";
import PasswordChangeComponent from "../../../../PasswordChange/PasswodChange";
import AssignGroupsContainer from "../AssignGroupsPage/AssignGroupsContainer";
import serverUrl from "../../../../URL/ServerUrl";

// Kolkas neveikia, issiaiskinti kodel...
const schema = yup.object({
  firstName: yup
    .string()
    .trim()
    .min(1, "Must be 1 characters or more")
    .max(30, "Must be 30 characters or less")
    .required("Please enter a first name")
    .matches(
      /^[A-Za-z\s-]+$/,
      "Only uppercase, lowercase letters and '-', space symbols are allowed"
    ),
  lastName: yup
    .string()
    .trim()
    .min(1, "Must be 1 characters or more")
    .max(30, "Must be 30 characters or less")
    .required("Please enter a last name")
    .matches(
      /^[A-Za-z\s-]+$/,
      "Only uppercase, lowercase letters and '-', space symbols are allowed"
    ),
  comment: yup
    .string()
    .trim()
    .max(50, "Must be 50 characters or less")
});

const handleSubmit = values => {
  axios({
    method: "PUT",
    url: serverUrl + "api/user/update-user-info/" + values.username,
    data: values
  })
    .then(
      // console.log(values.username)
      window.location.reload()
    )
    .catch(error => {
      console.log(error);
    });
};

class UserReviewContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      show: false,
      showAssignToGroupsModal: false,
      users: [],
      inputUsername: "",
      firstName: "",
      lastName: "",
      comment: "",
      userGroups: [],
      user: []
    };
  }

  getUser = () => {
    axios
      .get(serverUrl + "api/user/" + this.props.match.params.username)
      .then(response => {
        this.setState({
          user: response.data,
          userGroups: response.data.userGroups,
          firstName: response.data.firstName,
          lastName: response.data.lastName,
          comment: response.data.comment
        });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getUser();
  }

  //Funkcija uzdaryti modala be saugojimo ir atrefreshinti puslapi.
  handleCloseModal = () => {
    this.setState({ show: false });
  };

  // Password change modalo cancel
  handlePassCloseModal = () => {
    this.setState({ show: false });
  };
  // Password change modalo save
  handlePassSaveCloseModal = () => {
    this.setState({ show: false });
  };

  //Funkcija parodyti modala
  handleShowPasswordChangeModal = () => {
    this.setState({ show: true });
  };

  handleShowAssignToGroupsModal = () => {
    this.setState({ showAssignToGroupsModal: true });
  };

  handleCloseAssignToGroupsModal = () => {
    this.setState({ showAssignToGroupsModal: false });
  };

  handleCloseAssignToGroupsModalAndUpdate = () => {
    this.setState({ showAssignToGroupsModal: false });
    this.getUser();
  };

  // situs padarom kad nusiustu i state
  handleFirstNameChange = event => {
    this.setState({ firstName: event.target.value });
  };

  handleLastNameChange = event => {
    this.setState({ lastName: event.target.value });
  };

  handleCommentChange = event => {
    this.setState({ comment: event.target.value });
  };
  //

  onCancelClick = event => {
    event.preventDefault();
    this.props.history.push("/adminhomepage-users");
  };

  // duomenu nusiuntimas i DB pagal username.
  updateUser = event => {
    event.preventDefault();
    axios
      .put(
        serverUrl + "api/user/update-user-info/" + this.state.user.username,
        {
          comment: this.state.comment,
          firstName: this.state.firstName,
          lastName: this.state.lastName,
          password: "Stingaaa1",
          username: "String"
        }
      )
      .then(() => {
        this.props.history.push("/adminhomepage-users");
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <div className="container">
        <Formik
          validationSchema={schema}
          onSubmit={handleSubmit}
          initialValues={{
            firstName: "",
            lastName: "",
            comment: ""
          }}
        >
          {({
            handleSubmit,
            handleChange,
            setFieldValue,
            values,
            isValid,
            errors
          }) => (
            <div className="NewUserForm">
              <Form noValidate onSubmit={handleSubmit}>
                {/* User info */}

                <div className="row">
                  <div className="d-flex  mb-2 justify-content-center  col-12">
                    <h2>User Name:{this.state.user.username}</h2>
                  </div>
                  <div className="row d-flex justify-content-center  col-12  m-0">
                    <div className="col-5 d-flex flex-column col-6">
                      <div>
                        <Form.Group>
                          <label htmlFor="input">First Name</label>
                          <Form.Control
                            type="firstname"
                            placeholder={this.state.user.firstName}
                            defaultValue={this.state.user.firstName}
                            // value={values.firstName}
                            onChange={handleChange}
                            name="firstName"
                            id="firstName"
                            className="NewUserForm"
                            size="lg"
                            isInvalid={!!errors.firstName}

                            // type="firstname"
                            // defaultValue={this.state.user.firstName}
                            // onChange={this.handleFirstNameChange}
                            // name="firstName"
                            // id="firstName"
                            // className="NewUserForm"
                            // placeholder="First name"
                            // size="lg"
                            // isInvalid={!!errors.firstName}
                          />
                          <Form.Control.Feedback
                            className="FeedBack"
                            type="invalid"
                          >
                            {errors.firstName}
                          </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group>
                          <label htmlFor="input">Last Name</label>
                          <Form.Control
                            type="lastname"
                            placeholder={this.state.user.lastName}
                            defaultValue={this.state.user.lastName}
                            //   value={values.lastName}
                            onChange={handleChange}
                            name="lastName"
                            id="lastName"
                            className="NewUserForm"
                            size="lg"
                            isInvalid={!!errors.lastName}
                            // className="NewUserForm"
                            // name="lastName"
                            // onChange={this.handleLastNameChange}
                            // type="lastname"
                            // id="lastName"
                            // defaultValue={this.state.user.lastName}
                            // placeholder="Last name"
                            // size="lg"
                            // isInvalid={!!errors.lastName}
                          />
                          <Form.Control.Feedback
                            className="FeedBack"
                            type="invalid"
                          >
                            {errors.lastName}
                          </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group>
                          <label htmlFor="input">Comment</label>
                          <Form.Control
                            as="textarea"
                            rows="2"
                            className="NewUserForm"
                            size="lg"
                            name="comment"
                            onChange={handleChange}
                            type="comment"
                            id="comment"
                            defaultValue={this.state.user.comment}
                            //  value={values.comment}
                            placeholder={this.state.user.comment}
                            isInvalid={!!errors.comment}

                            // as="textarea"
                            // rows="2"
                            // className="NewUserForm col-12"
                            // size="lg"
                            // name="comment"
                            // onChange={this.handleCommentChange}
                            // type="comment"
                            // id="comment"
                            // defaultValue={this.state.user.comment}
                            // placeholder="Comment"
                            // isInvalid={!!errors.comment}
                          />
                          <Form.Control.Feedback
                            className="FeedBack"
                            type="invalid"
                          >
                            {errors.comment}
                          </Form.Control.Feedback>
                        </Form.Group>
                        <button
                          type="button"
                          className="col-12 mb-1 btn btn-primary btn-sm"
                          onClick={this.handleShowAssignToGroupsModal}
                        >
                          Assign to group
                        </button>

                        {/* Assign to group modal begining */}
                        <Modal
                          show={this.state.showAssignToGroupsModal}
                          onHide={this.handleCloseAssignToGroupsModalAndUpdate}
                        >
                          <Modal.Header closeButton>
                            <Modal.Title>Assign to groups</Modal.Title>
                          </Modal.Header>
                          <Modal.Body>
                            <AssignGroupsContainer
                              userGroups={this.state.user.userGroups}
                              username={this.state.user.username}
                              onHide={
                                this.handleCloseAssignToGroupsModalAndUpdate
                              }
                            />
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
                        <Modal
                          show={this.state.show}
                          onHide={this.handleCloseModal}
                        >
                          <Modal.Header closeButton>
                            <Modal.Title>Change password</Modal.Title>
                          </Modal.Header>
                          <Modal.Body>
                            <PasswordChangeComponent
                              onCloseModal={this.handlePassCloseModal}
                              onCloseModalAfterSubmit={
                                this.handlePassSaveCloseModal
                              }
                              username={this.state.user.username}
                            />
                          </Modal.Body>
                        </Modal>
                        {/* Change password modal end */}
                      </div>
                    </div>
                    <div className="col-6  m-0">
                      <div className="card">
                        <div className="card-body">
                          <h5 className="card-title">User groups</h5>
                          <div className="card-text scroll">
                            <ul className="list-group mb-2">
                              {this.state.userGroups.map((group, index) => {
                                return (
                                  <div key={index}>
                                    <li className="list-group-item">{group}</li>
                                  </div>
                                );
                              })}
                            </ul>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="row d-flex flex-row justify-content-between align-items-center col-12  m-0">
                    <div className="form-check col-4">
                      {/* <input
                      className="form-check-input"
                      type="checkbox"
                      value=""
                      id="defaultCheck1"
                    ></input>
                    <label className="form-check-label" htmlFor="defaultCheck1">
                      Deactivate
                    </label> */}
                    </div>
                  </div>
                </div>
                <div className="modal-footer d-flex shadow-sm  bg-light rounded justify-content-center align-items-center col-12">
                  <Button
                    disabled={!values.firstName || !values.lastName || !isValid}
                    onClick={() => {
                      setFieldValue("username", this.state.user.username);
                      setFieldValue("password", "qwertyuio");
                      handleSubmit();
                    }}
                    variant="primary"
                    className="SubmitButton mr-2 col-4"
                    type="button"
                  >
                    Submit
                  </Button>
                  <Button
                    onClick={this.onCancelClick}
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
      </div>
    );
  }
}

export default withRouter(UserReviewContainer);
