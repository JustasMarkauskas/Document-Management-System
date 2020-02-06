import React from "react";
import { withRouter } from "react-router-dom";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import axios from "axios";

const schema = yup.object({
  id: yup
    .string()
    .min(5, "Must be 5 characters or more")
    .max(20, "Must be 20 characters or less")
    .required("Please enter a group name")
    .matches(/^[A-Za-z\d]+$/, "Only uppercases, lowercases and numbers"),
  comment: yup
    .string()
    .trim()
    .max(50, "Must be 50 characters or less")
});

const handleSubmit = values => {
  axios({
    method: "POST",
    url: "http://localhost:8081/api/role/",
    data: values
  })
    .then(response => {
      console.log(response);
    })
    .catch(error => {
      console.log(error);
    });
};

const NewGroupFormComponent = props => {
  return (
    <Formik
      validationSchema={schema}
      onSubmit={handleSubmit}
      initialValues={{
        id: "",
        comment: ""
      }}
    >
      {({ handleSubmit, handleChange, values, isValid, errors }) => (
        <div className="NewGroupForm">
          <Form noValidate onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Control
                size="lg"
                className="NewGroupForm"
                type="text"
                id="id"
                name="id"
                value={values.id}
                onChange={handleChange}
                placeholder="Group Name"
                isInvalid={!!errors.id}
              />
              <Form.Control.Feedback className="FeedBack" type="invalid">
                {errors.id}
              </Form.Control.Feedback>
              </Form.Group>

              <Form.Group>              
              <Form.Control
                as="textarea"
                rows="2"
                className="NewGroupForm"
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
                {errors.comment}
              </Form.Control.Feedback>
            </Form.Group>            

            <Button
              disabled={!isValid}
              onClick={props.onCloseModal}
              variant="primary"
              className="SubmitButton mr-2"
              type="submit"
            >
              Submit
            </Button>
            <Button onClick={props.onCloseModal} variant="primary">
              Cancel
            </Button>
          </Form>
        </div>
      )}
    </Formik>
  );
};

export default withRouter(NewGroupFormComponent);
