import React from "react";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import axios from "axios";
import serverUrl from "../URL/ServerUrl";

const schema = yup.object().shape({
  title: yup
    .string()
    .trim()
    .min(5, "Must be 5-30 characters long")
    .max(30, "Must be 5-30 characters long")
    .required("Please enter a title"),
  description: yup
    .string()
    .trim()
    .min(5, "Must be 5-50 characters long")
    .max(50, "Must be 5-50 characters long")
    .required("Please enter a description")
});

class NewDocumentFormComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      files: [],
      docType: "",
      title: "",
      description: ""
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
  handleTitleChange = event => {
    this.setState({ title: event.target.value });
  };
  handleDescriptionChange = event => {
    this.setState({ description: event.target.value });
  };
  handleFilesChange = event => {
    this.setState({ files: event.target.files });
    if (event.target.files.length > 0) {
      document
        .getElementById("uploadFileInfo")
        .setAttribute("class", "text-info small d-none");
    } else {
      document
        .getElementById("uploadFileInfo")
        .setAttribute("class", "text-info small");
    }
  };

  submitDocument = (event, button) => {
    event.preventDefault();
    const formData = new FormData();
    formData.append("author", this.props.author);
    formData.append("title", this.state.title.trim());
    formData.append("description", this.state.description.trim());
    formData.append("docType", this.state.docType);

    var i;
    for (i = 0; i <= this.state.files.length; i++) {
      formData.append("files", this.state.files[i]);
    }

    var forSuccessMsg = "submitted";
    if (button === "save") {
      forSuccessMsg = "saved";
    }

    axios({
      method: "POST",
      url: serverUrl + "api/document/" + button,
      data: formData,
      headers: {
        "content-type": "multipart/form-data"
      }
    })
      .then(this.props.onCloseModalAfterSubmit(forSuccessMsg))
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    return (
      <Formik
        initialValues={{
          title: "",
          description: ""
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
          <div className="NewDocumentForm" id="userCreateDocumentForm">
            <Form noValidate onSubmit={handleSubmit}>
              <Form.Group>
                <Form.Control
                  ref={this.innerRef}
                  size="lg"
                  className="NewDocumentForm"
                  type="text"
                  id="title"
                  name="title"
                  defaultValue={this.state.title}
                  onChange={this.handleTitleChange}
                  onKeyUp={handleChange}
                  onBlur={handleBlur}
                  placeholder="Title"
                  isInvalid={!!errors.title}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  <p className="text-info">{errors.title}</p>
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group>
                <select
                  className="form-control"
                  id="docType"
                  defaultValue={this.state.docType}
                  onChange={this.handleDocTypeChange}
                >
                  <option value="" disabled defaultValue>
                    Select document type
                  </option>
                  {this.props.userDocTypes.map((option, index) => {
                    return (
                      <option key={index} value={option}>
                        {option}
                      </option>
                    );
                  })}
                </select>
              </Form.Group>

              <Form.Group>
                <Form.Control
                  as="textarea"
                  rows="2"
                  className="NewDocumentForm"
                  size="lg"
                  name="description"
                  defaultValue={this.state.description}
                  onChange={this.handleDescriptionChange}
                  onKeyUp={handleChange}
                  type="description"
                  id="description"
                  placeholder="Description"
                  isInvalid={!!errors.description}
                />
                <Form.Control.Feedback className="FeedBack" type="invalid">
                  <p className="text-info">{errors.description}</p>
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group>
                <input
                  className="NewDocumentForm"
                  type="file"
                  multiple
                  defaultValue={this.state.files}
                  onChange={this.handleFilesChange}
                />
                <div id="uploadFileInfo" className="text-info small">
                  At least one file has to be selected to Submit the form
                </div>
              </Form.Group>

              <div className="container mt-2">
                <div className="row">
                  <Button
                    disabled={
                      !values.title ||
                      !values.description ||
                      !isValid ||
                      !this.state.docType.length > 0 ||
                      !this.state.files.length > 0
                    }
                    onClick={(event, button) => {
                      this.submitDocument(event, "submit");
                    }}
                    className="SubmitButton mr-2 modals-btn-color"
                    type="button"
                  >
                    Submit
                  </Button>
                  <Button
                    disabled={
                      !values.title ||
                      !values.description ||
                      !this.state.docType.length > 0 ||
                      !isValid
                    }
                    onClick={(event, button) => {
                      this.submitDocument(event, "save");
                    }}
                    className="SubmitButton mr-2 modals-btn-color"
                    type="button"
                  >
                    Save for later
                  </Button>
                  <Button onClick={this.props.onHide} variant="secondary">
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

export default NewDocumentFormComponent;
