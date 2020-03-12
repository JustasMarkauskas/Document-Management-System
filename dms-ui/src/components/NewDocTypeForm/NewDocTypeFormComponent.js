import React from "react";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import axios from "axios";
import serverUrl from "../URL/ServerUrl";
import { store } from "react-notifications-component";

const schema = yup.object().shape({
  id: yup
    .string()
    .min(5, "Must be 5-20 characters long")
    .max(20, "Must be 5-20 characters long")
    .required("Please enter a document type name")
    .matches(
      /^[0-9A-Za-z\s./-]+$/,
      "Only uppercase, lowercase letters, numbers and '-/.', space symbols are allowed"
    ),
  comment: yup
    .string()
    .trim()
    .max(50, "Must be 50 characters or less")
});

class NewDocTypeFormComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      docType: "",
      comment: ""
    };
    this.innerRef = React.createRef();
  }

  componentDidMount() {
    setTimeout(() => {
      this.innerRef.current.focus();
    }, 1);
  }

  handleDocTypeChange = event => {
    this.setState({ docType: event.target.value });
  };
  handleCommentChange = event => {
    this.setState({ comment: event.target.value });
  };

  errorDocTypeNotification = name =>
    store.addNotification({
      title: "Warning!",
      message: "Document type with name " + name + " already exists",
      type: "danger",
      insert: "top",
      container: "top-right",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  submitDocType = event => {
    event.preventDefault();
    axios
      .post(serverUrl + "api/doctype/", {
        id: this.state.docType,
        comment: this.state.comment
      })
      .then(this.props.onCloseModalAfterSubmit)
      .catch(error => {
        this.errorDocTypeNotification(this.state.docType);
        console.log(error);
      });
  };

  render() {
    return (
      <Formik
        validationSchema={schema}
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
                  defaultValue={this.state.docType}
                  onChange={this.handleDocTypeChange}
                  onKeyUp={handleChange}
                  placeholder="Document type name"
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
                  onChange={this.handleCommentChange}
                  onKeyUp={handleChange}
                  type="comment"
                  id="comment"
                  defaultValue={this.state.comment}
                  placeholder="Comment"
                  isInvalid={!!errors.comment}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  <p className="text-info">{errors.comment}</p>
                </Form.Control.Feedback>
              </Form.Group>

              <Button
                disabled={!values.id || !isValid}
                onClick={this.submitDocType}
                variant="primary"
                className="SubmitButton mr-2"
                type="button"
              >
                Submit
              </Button>
              <Button onClick={this.props.onHide} variant="secondary">
                Cancel
              </Button>
            </Form>
          </div>
        )}
      </Formik>
    );
  }
}

export default NewDocTypeFormComponent;
