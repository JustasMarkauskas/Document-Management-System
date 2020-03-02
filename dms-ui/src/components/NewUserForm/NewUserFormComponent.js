import React from "react";
import { withRouter } from "react-router-dom";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import axios from "axios";
import serverUrl from "../URL/ServerUrl";

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
    .matches(/^[A-Za-z\s-]+$/, "Only uppercases And lowercases"),
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

const handleSubmit = values => {
  axios({
    method: "POST",
    url: serverUrl + "api/user/",
    data: values
  })
    .then(response => {
      console.log(response);
    })
    .catch(error => {
      console.log(error);
    });
};

class NewUserFormComponent extends React.Component {
  constructor(props) {
    super(props);
    this.innerRef = React.createRef();
  }
  componentDidMount() {
    setTimeout(() => {
      this.innerRef.current.focus();
    }, 1);
  }
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
        onSubmit={handleSubmit}
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
                  value={values.username}
                  onChange={handleChange}
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
                  placeholder="First Name"
                  value={values.firstName}
                  onChange={handleChange}
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
                  placeholder="Last Name"
                  value={values.lastName}
                  onChange={handleChange}
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
                  value={values.password}
                  onChange={handleChange}
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
                  value={values.confirmPassword}
                  onChange={handleChange}
                  placeholder="Confirm Password"
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
                  onChange={handleChange}
                  type="comment"
                  id="comment"
                  value={values.comment}
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
                onClick={this.props.onCloseModalAfterSubmit}
                variant="primary"
                className="SubmitButton mr-2"
                type="submit"
              >
                Submit
              </Button>
              <Button onClick={this.props.onCloseModal} variant="primary">
                Cancel
              </Button>
            </Form>
          </div>
        )}
      </Formik>
    );
  }
}

export default withRouter(NewUserFormComponent);
