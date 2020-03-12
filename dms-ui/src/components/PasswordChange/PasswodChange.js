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

const handleSubmit = values => {
  axios({
    method: "PUT",
    url: serverUrl + "api/user/update-password/" + values.username,
    data: values
  }).catch(error => {
    console.log(error);
  });
};

class PasswordChangeComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
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
        validationSchema={schema}
        onSubmit={handleSubmit}
        initialValues={{
          password: "",
          confirmPassword: ""
        }}
      >
        {({
          handleSubmit,
          setFieldValue,
          handleChange,
          values,
          isValid,
          errors
        }) => (
          <div className="NewUserForm">
            <Form noValidate onSubmit={handleSubmit}>
              <div className="row">
                <div className="d-flex justify-content-center col-12">
                  <Form.Group>
                    <Form.Control
                      ref={this.innerRef}
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
                </div>
                <div className="d-flex justify-content-center col-12">
                  <Form.Group>
                    <Form.Control
                      className="NewUserForm"
                      size="lg"
                      name="confirmPassword"
                      type="password"
                      id="confirmPassword"
                      value={values.confirmPassword}
                      onChange={handleChange}
                      placeholder="Confirm password"
                      isInvalid={!!errors.confirmPassword}
                    />
                    <Form.Control.Feedback className="FeedBack" type="invalid">
                      <p className="text-info">{errors.confirmPassword}</p>
                    </Form.Control.Feedback>
                  </Form.Group>
                </div>
                <div className="d-flex justify-content-center col-12">
                  <Button
                    disabled={
                      !values.password || !values.confirmPassword || !isValid
                    }
                    onClick={() => {
                      setFieldValue("username", this.props.username);
                      setFieldValue("firstName", "empty");
                      setFieldValue("lastName", "empty");
                      setFieldValue("comment", "empty");
                      handleSubmit();
                      this.props.onCloseModal();
                    }}
                    variant="primary"
                    className="SubmitButton mr-2 col-4"
                    type="button"
                  >
                    Change
                  </Button>
                  <Button
                    onClick={this.props.onCloseModal}
                    variant="secondary"
                    className="col-4"
                  >
                    Cancel
                  </Button>
                </div>
              </div>
            </Form>
          </div>
        )}
      </Formik>
    );
  }
}

export default withRouter(PasswordChangeComponent);
