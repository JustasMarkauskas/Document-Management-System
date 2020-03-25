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
    .required("Please enter a group name")
    .matches(
      /^[A-Za-z\d]+$/,
      "Only uppercase, lowercase letters and numbers are allowed"
    ),
  comment: yup
    .string()
    .trim()
    .max(50, "Must be 50 characters or less")
});

class NewGroupFormComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      groupName: "",
      comment: ""
    };
    this.innerRef = React.createRef();
  }
  componentDidMount() {
    setTimeout(() => {
      this.innerRef.current.focus();
    }, 1);
  }

  handleGroupNameChange = event => {
    this.setState({ groupName: event.target.value });
  };
  handleCommentChange = event => {
    this.setState({ comment: event.target.value });
  };

  errorGroupNotification = name =>
    store.addNotification({
      title: "Warning!",
      message: "Group with name " + name + " already exists",
      type: "danger",
      insert: "top",
      container: "top-center",
      animationIn: ["animated", "fadeIn"],
      animationOut: ["animated", "fadeOut"],
      dismiss: {
        duration: 3000
      }
    });

  submitGroup = event => {
    event.preventDefault();
    axios
      .post(serverUrl + "api/group", {
        id: this.state.groupName,
        comment: this.state.comment
      })
      .then(this.props.onCloseModalAfterSubmit)
      .catch(error => {
        this.errorGroupNotification(this.state.groupName);
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
          <div className="NewGroupForm" id="adminCreateGroupForm">
            <Form noValidate onSubmit={handleSubmit}>
              <Form.Group>
                <Form.Control
                  ref={this.innerRef}
                  size="lg"
                  className="NewGroupForm"
                  type="text"
                  id="id"
                  name="id"
                  defaultValue={this.state.groupName}
                  onChange={this.handleGroupNameChange}
                  onKeyUp={handleChange}
                  placeholder="Group name"
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
                  className="NewGroupForm"
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
                onClick={this.submitGroup}
                className="SubmitButton mr-2 modals-btn-color"
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

export default NewGroupFormComponent;
