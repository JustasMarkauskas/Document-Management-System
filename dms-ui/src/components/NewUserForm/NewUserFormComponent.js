import React from "react";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import axios from "axios";
import serverUrl from "../URL/ServerUrl";
import { store } from "react-notifications-component";

const schema = yup.object().shape({
  username: yup
    .string()
    .min(5, "Must be 5-20 characters long")
    .max(20, "Must be 5-20 characters long")
    .required("Please enter a username")
    .matches(
      /^[A-Za-z\d]+$/,
      "Only uppercase, lowercase letters and numbers are allowed"
    ),
  firstName: yup
    .string()
    .trim()
    .min(1, "Must be 1-30 characters long")
    .max(30, "Must be 1-30 characters long")
    .required("Please enter a first name")
    .matches(
      /^[A-Za-z\s-]+$/,
      "Only uppercase, lowercase letters and '-', space symbols are allowed"
    ),
  lastName: yup
    .string()
    .trim()
    .min(1, "Must be 1-30 characters long")
    .max(30, "Must be 1-30 characters long")
    .required("Please enter a last name")
    .matches(
      /^[A-Za-z\s-]+$/,
      "Only uppercase, lowercase letters and '-', space symbols are allowed"
    ),
  comment: yup
    .string()
    .trim()
    .max(50, "Must be 50 characters or less"),
  password: yup
    .string()
    .required("Please enter your password")
    .min(8, "Must be 8-20 characters long")
    .max(20, "Must be 8-20 characters long")
    .matches(
      /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[A-Za-z\d]+$/,
      "Only uppercase, lowercase letters and numbers are allowed. At least one of each must be present."
    ),
  confirmPassword: yup
    .string()
    .required("Please confirm your password")
    .oneOf([yup.ref("password"), null], "Please make sure your passwords match")
});

class NewUserFormComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      firstName: "",
      lastName: "",
      password: "",
      comment: ""
    };
    this.innerRef = React.createRef();
  }
  componentDidMount() {
    setTimeout(() => {
      this.innerRef.current.focus();
    }, 1);
  }

  handleUsernameChange = event => {
    this.setState({ username: event.target.value });
  };
  handleFirstNameChange = event => {
    this.setState({ firstName: event.target.value });
  };

  handleLastNameChange = event => {
    this.setState({ lastName: event.target.value });
  };

  handlePasswordChange = event => {
    this.setState({ password: event.target.value });
  };

  handleCommentChange = event => {
    this.setState({ comment: event.target.value });
  };

  errorUserNotification = name =>
    store.addNotification({
      title: "Warning!",
      message: "User with username " + name + " already exists",
      type: "danger",
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  submitUser = event => {
    event.preventDefault();
    axios
      .post(serverUrl + "api/user", {
        username: this.state.username,
        firstName: this.state.firstName,
        lastName: this.state.lastName,
        password: this.state.password,
        comment: this.state.comment
      })
      .then(this.props.onCloseModalAfterSubmit)
      .catch(error => {
        this.errorUserNotification(this.state.username);
        console.log(error);
      });
  };

  render() {
    return (
      <Formik
        initialValues={{
          username: "",
          firstName: "",
          lastName: "",
          comment: "",
          password: "",
          confirmPassword: ""
        }}
        validationSchema={schema}
      >
        {({
          handleSubmit,
          handleChange,
          values,
          isValid,
          errors,
          handleBlur
        }) => (
          <div className="NewUserForm" id="adminCreateUserForm">
            <Form noValidate onSubmit={handleSubmit}>
              <Form.Group>
                <Form.Control
                  ref={this.innerRef}
                  size="lg"
                  className="NewUserForm"
                  type="text"
                  id="username"
                  name="username"
                  defaultValue={this.state.username}
                  onChange={this.handleUsernameChange}
                  onKeyUp={handleChange}
                  onBlur={handleBlur}
                  placeholder="Username"
                  isInvalid={!!errors.username}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  <p className="text-info">{errors.username}</p>
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group>
                <Form.Control
                  type="firstname"
                  placeholder="First name"
                  defaultValue={this.state.firstName}
                  onChange={this.handleFirstNameChange}
                  onKeyUp={handleChange}
                  name="firstName"
                  id="firstName"
                  className="NewUserForm"
                  size="lg"
                  isInvalid={!!errors.firstName}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  <p className="text-info">{errors.firstName}</p>
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group>
                <Form.Control
                  type="lastname"
                  placeholder="Last name"
                  defaultValue={this.state.lastName}
                  onChange={this.handleLastNameChange}
                  onKeyUp={handleChange}
                  name="lastName"
                  id="lastName"
                  className="NewUserForm"
                  size="lg"
                  isInvalid={!!errors.lastName}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  <p className="text-info">{errors.lastName}</p>
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group>
                <Form.Control
                  className="NewUserForm"
                  size="lg"
                  type="password"
                  name="password"
                  id="password"
                  defaultValue=""
                  onKeyUp={handleChange}
                  placeholder="Password"
                  isInvalid={!!errors.password}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  <p className="text-info">{errors.password}</p>
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group>
                <Form.Control
                  className="NewUserForm"
                  size="lg"
                  name="confirmPassword"
                  type="password"
                  id="confirmPassword"
                  defaultValue={this.state.password}
                  onChange={this.handlePasswordChange}
                  onKeyUp={handleChange}
                  placeholder="Confirm password"
                  isInvalid={!!errors.confirmPassword}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  <p className="text-info">{errors.confirmPassword}</p>
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group>
                <Form.Control
                  as="textarea"
                  rows="2"
                  className="NewUserForm"
                  size="lg"
                  name="comment"
                  defaultValue={this.state.comment}
                  onChange={this.handleCommentChange}
                  onKeyUp={handleChange}
                  type="comment"
                  id="comment"
                  placeholder="Comment"
                  isInvalid={!!errors.comment}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  <p className="text-info">{errors.comment}</p>
                </Form.Control.Feedback>
              </Form.Group>
              <Button
                disabled={
                  !values.username ||
                  !values.firstName ||
                  !values.lastName ||
                  !values.password ||
                  !values.confirmPassword ||
                  !isValid
                }
                onClick={this.submitUser}
                variant="primary"
                className="SubmitButton mr-2"
                type="button"
              >
                Submit
              </Button>
              <Button onClick={this.props.onCloseModal} variant="secondary">
                Cancel
              </Button>
            </Form>
          </div>
        )}
      </Formik>
    );
  }
}

export default NewUserFormComponent;
