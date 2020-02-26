import React from "react";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import axios from "axios";

const schema = yup.object().shape({
  docType: yup.string().required("Please select document type"),
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
    .max(50, "Must be 1-50 characters long")
});

const handleSubmit = values => {
  const formData = new FormData();
  formData.append("author", values.author);
  formData.append("title", values.title.trim());
  formData.append("description", values.description.trim());
  formData.append("docType", values.docType);

  var i;
  for (i = 0; i <= values.files.length; i++) {
    formData.append("files", values.files[i]);
  }

  var url;
  if (values.isSaveButton === true) {
    url = "save";
  } else {
    url = "submit";
  }
  axios({
    method: "POST",
    url: "http://localhost:8081/api/document/" + url,
    data: formData,
    headers: {
      "content-type": "multipart/form-data"
    }
  }).catch(error => {
    console.log(error);
  });
};

class NewDocumentFormComponent extends React.Component {
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
          files: [],
          docType: "",
          title: "",
          description: "",
          author: this.props.author,
          isSaveButton: false
        }}
        validationSchema={schema}
        onSubmit={handleSubmit}
      >
        {({
          handleSubmit,
          handleChange,
          setFieldValue,
          values,
          isValid,
          errors,
          handleBlur
        }) => (
          <div className="NewDocumentForm" id="userCreateDocumentForm">
            <Form noValidate>
              <Form.Group>
                <Form.Control
                  ref={this.innerRef}
                  size="lg"
                  className="NewDocumentForm"
                  type="text"
                  id="title"
                  name="title"
                  value={values.title}
                  onChange={handleChange}
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
                  value={values.docType}
                  onChange={event => {
                    setFieldValue("docType", event.currentTarget.value);
                  }}
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
                  onChange={handleChange}
                  type="description"
                  id="description"
                  value={values.description}
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
                  onChange={event => {
                    setFieldValue("files", event.currentTarget.files);
                    if (event.target.files.length > 0) {
                      document
                        .getElementById("uploadFileInfo")
                        .setAttribute("class", "text-info small d-none");
                    } else {
                      document
                        .getElementById("uploadFileInfo")
                        .setAttribute("class", "text-info small");
                    }
                  }}
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
                      !values.files.length > 0
                    }
                    onClick={() => {
                      handleSubmit();
                      this.props.onCloseModalAfterSubmit();
                    }}
                    variant="primary"
                    className="SubmitButton mr-2"
                    type="button"
                  >
                    Submit
                  </Button>
                  <Button
                    disabled={!values.title || !values.description || !isValid}
                    onClick={() => {
                      setFieldValue("isSaveButton", true);
                      handleSubmit();
                      this.props.onCloseModalAfterSubmit();
                    }}
                    variant="primary"
                    className="SubmitButton mr-2"
                    type="button"
                  >
                    Save for later
                  </Button>
                  <Button onClick={this.props.onHide} variant="primary">
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
