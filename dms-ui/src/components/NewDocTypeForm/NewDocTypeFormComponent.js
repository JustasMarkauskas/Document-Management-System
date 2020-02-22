import React from "react";
import { withRouter } from "react-router-dom";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import axios from "axios";

const schema = yup.object().shape({
  id: yup
    .string()
    .min(5, "Must be 5-20 characters long")
    .max(20, "Must be 5-20 characters long")
    .required("Please enter a document type name")
    .matches(/^[0-9A-Za-z\s./-]+$/, "Only uppercase, lowercase letters, numbers and '-/.', space symbols are allowed"),
  comment: yup
    .string()
    .trim()
    .max(50, "Must be 50 characters or less")
});

const handleSubmit = values => {  
  axios({
    method: "POST",
    url: "http://localhost:8081/api/doctype/",
    data: values
  })
    .then(response => {
      console.log(response)      
    })
    .catch(error => {
      console.log(error)      
    });
};

class NewDocTypeFormComponent extends React.Component {
  constructor(props) {
    super(props);
    this.innerRef = React.createRef();
  }
  componentDidMount(){
    setTimeout(() => {
      this.innerRef.current.focus();
    }, 1);
  }
 render(){
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
        <div id="adminCreateDocTypeForm">
          <Form noValidate onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Control
                ref={this.innerRef} 
                size="lg"
                type="text"
                id="id"
                name="id"
                value={values.id}
                onChange={handleChange}
                placeholder="Document Type Name"
                isInvalid={!!errors.id}
              />
              <Form.Control.Feedback className="FeedBack" type="invalid">
              <p className="text-info">{errors.id}</p>
              </Form.Control.Feedback>
            </Form.Group>

            <Form.Group>
              <Form.Control
                as="textarea"
                rows="2"
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
              disabled={!values.id || !isValid}
              onClick={this.props.onCloseModalAfterSubmit}
              variant="primary"
              className="SubmitButton mr-2"
              type="submit"
            >
              Submit
            </Button>
            <Button onClick={this.props.onHide} variant="primary">
              Cancel
            </Button>
          </Form>
        </div>
      )}
    </Formik>
  );
}
};

export default withRouter(NewDocTypeFormComponent);
