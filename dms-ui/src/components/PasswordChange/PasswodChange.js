import React from "react";
import { withRouter } from "react-router-dom";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import axios from "axios";
import serverUrl from "../URL/ServerUrl";

const schema = yup.object({
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

class PasswordChangeComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      password: ""
    };
    this.innerRef = React.createRef();
  }

  componentDidMount() {
    setTimeout(() => {
      this.innerRef.current.focus();
    }, 1);
  }

  handlePasswordChange = event => {
    this.setState({ password: event.target.value });
  };

  submitPasswordChange = event => {
    event.preventDefault();
    console.log("at");
    axios
      .put(serverUrl + "api/user/update-password/" + this.props.username, {
        password: this.state.password,
        username: this.props.username,
        firstName: "test",
        lastName: "test",
        comment: "tests"
      })
      .then(this.props.onCloseModalAfterSubmit)
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <Formik
        validationSchema={schema}
        initialValues={{
          password: "",
          confirmPassword: ""
        }}
      >
        {({ handleSubmit, handleChange, values, isValid, errors }) => (
          <div className="PasswordForm password-modal-width">
            <Form noValidate onSubmit={handleSubmit} id="passwordChangeForm">
              <Form.Group>
                <Form.Control
                  ref={this.innerRef}
                  className="PasswordForm"
                  size="lg"
                  type="password"
                  name="password"
                  id="password"
                  defaultValue=""
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
                  className="PasswordForm"
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
              <div className="d-flex justify-content-center col-12">
                <Button
                  disabled={
                    !values.password || !values.confirmPassword || !isValid
                  }
                  onClick={this.submitPasswordChange}
                  className="SubmitButton mr-2 col-6 modals-btn-color"
                  type="button"
                >
                  Change
                </Button>
                <Button
                  onClick={this.props.onCloseModal}
                  variant="secondary"
                  className="col-6"
                >
                  Cancel
                </Button>
              </div>
            </Form>
          </div>
        )}
      </Formik>
    );
  }
}

export default withRouter(PasswordChangeComponent);
