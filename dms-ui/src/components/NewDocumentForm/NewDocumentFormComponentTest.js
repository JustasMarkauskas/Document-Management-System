import React from "react";
import { withRouter } from "react-router-dom";
import { Form } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import axios from "axios";

const schema = yup.object().shape({
  //docType: yup.required("Please select document type"),
  title: yup
    .string()
    .min(5, "Must be 5-30 characters long")
    .max(30, "Must be 5-30 characters long")
    .required("Please enter a title")
    .matches(
      /^[A-Za-z\d]+$/,
      "Only uppercase, lowercase letters and numbers are allowed"
    ),
  description: yup
    .string()
    .trim()
    .max(50, "Must be 50 characters or less")
});

const handleSubmit = values => {
  const formData = new FormData();
  formData.append("author", values.author);
  formData.append("title", values.title);
  formData.append("description", values.description);

  var i;
  for (i = 0; i <= values.files.length; i++) {
    formData.append("files", values.files[i]);
  }

  axios({
    method: "POST",
    url: "http://localhost:8081/api/document/save",
    data: formData,
    headers: {
      "content-type": "multipart/form-data"
    }
  })
    .then(response => {
      //uzdaryti modala perkrauti psl
      console.log(response);
    })
    .catch(error => {
      console.log(error);
    });
};

class NewDocumentFormComponentTest extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      files: []
    };
  }

  onFileChange = event => {
    this.setState({ files: event.target.files });
  };

  handleSave = values => {
    console.log("title " + values.title);
    const formData = new FormData();
    formData.append("author", values.author);
    formData.append("title", values.title);
    formData.append("description", values.description);

    var i;
    for (i = 0; i <= this.state.files.length; i++) {
      formData.append("files", this.state.files[i]);
    }

    // axios({
    //   method: "POST",
    //   url: "http://localhost:8081/api/document/save",
    //   data: formData,
    //   headers: {
    //     "content-type": "multipart/form-data"
    //   }
    // })
    //   .then(response => {
    //     //uzdaryti modala perkrauti psl
    //     console.log(response);
    //   })
    //   .catch(error => {
    //     console.log(error);
    //   });
  };

  render() {
    return (
      <Formik
        initialValues={{
          files: [],
          docType: "",
          title: "",
          description: "",
          author: this.props.author
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

              <input type="file" multiple onChange={this.onFileChange} />

              <Button
                disabled={!values.title || !values.description || !isValid}
                onClick={this.handleSave}
                variant="primary"
                className="SubmitButton mr-2"
                type="button"
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
}

export default withRouter(NewDocumentFormComponentTest);
