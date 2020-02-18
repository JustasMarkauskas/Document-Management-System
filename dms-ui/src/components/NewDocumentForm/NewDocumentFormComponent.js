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

// fileSaveUpload = files => {
//   const url = "http://localhost:8081/api/document/save/";
//   const formData = new FormData();

//   var i;
//   for (i = 0; i <= files.length; i++) {
//     formData.append("files", files[i]);
//   }
//   formData.append("description", this.state.description);
//   formData.append("docType", this.state.docType);
//   formData.append("title", this.state.title);
//   const config = {
//     headers: {
//       "content-type": "multipart/form-data"
//     }
//   };
//   return post(url, formData, config);
// };

const handleSubmit = values => {
  axios({
    method: "POST",
    url: "http://localhost:8081/api/document/save",
    data: values
  })
    .then(response => {
      console.log(response);
    })
    .catch(error => {
      console.log(error);
    });
};

const NewDocumentFormComponent = props => {
  return (
    <Formik
      initialValues={{
        files: [],
        docType: "",
        title: "",
        description: ""
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

            <input
              className="NewDocumentForm"
              type="file"
              multiple
              onChange={handleChange}
              name="files"
              id="files"
              value={values.files}
            />

            <Button
              disabled={!values.title || !values.description || !isValid}
              onClick={handleSubmit}
              variant="primary"
              className="SubmitButton mr-2"
              type="submit"
            >
              Submit
            </Button>
            <Button onClick={props.onHide} variant="primary">
              Cancel
            </Button>
          </Form>
        </div>
      )}
    </Formik>
  );
};

export default withRouter(NewDocumentFormComponent);
