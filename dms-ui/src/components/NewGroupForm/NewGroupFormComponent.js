import React from "react";
import { withRouter } from "react-router-dom";
import { Form } from "react-bootstrap";
import { Button} from 'react-bootstrap';
import { Formik } from "formik";
import * as yup from 'yup';
import axios from "axios";

const schema = yup.object({
  groupName: yup
      .string()
      .min(5, 'Must be 5 characters or more')
      .max(20, 'Must be 20 characters or less')
      .required('Please Enter a groupName')
      .matches(/^[A-Za-z\d]+$/, "Only Uppercases, Lowercases And Numbers"),  
});

const handleSubmit = (values) => {
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

const NewGroupFormComponent = (props) => {  
      return (
        <Formik
        validationSchema={schema}
        onSubmit={handleSubmit}        
        initialValues={{
          groupName: "",          
        }}
      >
      {({                   
        handleSubmit,        
        handleChange,
        values,         
        isValid,
        errors,
      }) => (
        <div className="NewGroupForm">            
        <Form noValidate onSubmit={handleSubmit}>
        <div class="form-group">
            <Form.Control
              size="lg"
              className="NewGroupForm"
              type="text"
              id="groupName"
              name="groupName"
              value={values.groupName}                
              onChange={handleChange}                
              placeholder="groupName"
              isInvalid={!!errors.groupName}
            />                             
            <Form.Control.Feedback className="FeedBack" type="invalid">
            {errors.groupName}
          </Form.Control.Feedback>
          </div>  
          
         <Button disabled={!isValid} onClick={props.onCloseModal} variant="primary" className="SubmitButton mr-2" type="submit">
        Submit
      </Button>
      <Button onClick={props.onCloseModal} variant="primary">
        Cancel
      </Button>
      
    </Form>
  </div>)}
  </Formik>
);
};

export default withRouter(NewGroupFormComponent);
