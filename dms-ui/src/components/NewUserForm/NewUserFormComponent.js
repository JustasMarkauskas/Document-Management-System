import React from "react";
import { Form } from "react-bootstrap";
import { Button } from 'react-bootstrap';
import { Formik } from "formik";
import * as yup from 'yup';

const schema = yup.object({
    username: yup
        .string()
        .min(5, 'Must be 5 characters or more')
        .max(20, 'Must be 20 characters or less')
        .required('Please Enter a username')
        .matches(/^[A-Za-z\d]+$/, "Only Uppercases, Lowercases And Numbers"),
    firstName: yup
        .string()
        .min(1, 'Must be 1 characters or more')
        .max(30, 'Must be 30 characters or less')
        .required('Please Enter a username')
        .matches(/^[A-Za-z\s-]+$/, "Only Uppercases And Lowercases"),
    lastName: yup
        .string()
        .min(1, 'Must be 1 characters or more')
        .max(30, 'Must be 30 characters or less')
        .required('Please Enter a username')
        .matches(/^[A-Za-z\s-]+$/, "Only Uppercases And Lowercases"),
    comment: yup
      .string(),          
    password: yup
        .string()
        .required('Please Enter your password')
        .matches(
        /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d].{8,20}$/,
        "Must Contain 8 - 20 Characters, One Uppercase, One Lowercase, One Number"
        ),
    confirmPassword: yup
      .string()
      .required()
      .oneOf([yup.ref("password"), null], "Passwords must match")
  });
  
// const NewUserFormComponent = ({
//   handleUsernameChange,
//   handleFirstNameChange,
//   handleLastNameChange,
//   handleCommentChange,
//   handlePasswordChange,
//   handlePasswordConfirmChange,  
//   handleSubmit,
//   handleCancel,
//   username,
//   firstName,
//   lastName,
//   comment,
//   password,
//   passwordConfirm
// }) => {

    const NewUserFormComponent = () => {
        return (
          <Formik
          validationSchema={schema}
          onSubmit={console.log}
          initialValues={{
            username: "",
            firstName: "",
            lastName: "",
            comment: "",           
            password: "",
            confirmPassword : ""
          }}
        >
        {({
          handleUsernameChange,
          handleFirstNameChange,
          handleLastNameChange,
          handleCommentChange,
          handlePasswordChange,  
          handleSubmit,
          handleCancel,
          handleChange,
          values,         
          isValid,
          errors,
        }) => (
          <div className="NewUserForm">      
          <Form noValidate onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Control
                size="lg"
                className="NewUserForm"
                type="text"
                id="username"
                name="username"
                value={values.username}
                onChange={handleChange}
                onPress={handleUsernameChange }
                placeholder="Username"
                isInvalid={!!errors.username}
              />
              <Form.Control.Feedback className="FeedBack" type="invalid">
              {errors.username}
            </Form.Control.Feedback>
            </Form.Group>
    
            <Form.Group>
              <Form.Control
                type="firstname"
                placeholder="First Name"
                value={values.firstName}
                onChange={handleChange}
                onPress={handleFirstNameChange}
                name="firstName"
                id="firstName"
                className="NewUserForm"
                size="lg"
                isInvalid={!!errors.firstName}
              />
              <Form.Control.Feedback className="FeedBack" type="invalid">
              {errors.firstName}
            </Form.Control.Feedback>
            </Form.Group>
    
            <Form.Group>
              <Form.Control
                type="lastname"
                placeholder="Last Name"
                value={values.lastName}
                onChange={handleChange}
                onPress={handleLastNameChange}
                name="lastName"
                id="lastName"
                className="NewUserForm"
                size="lg"
                isInvalid={!!errors.lastName}
              />
              <Form.Control.Feedback className="FeedBack" type="invalid">
              {errors.lastName}
            </Form.Control.Feedback>
            </Form.Group>
    
            <Form.Group>
              <Form.Control
                className="NewUserForm"
                size="lg"
                type="password"
                name="password"
                id="password"
                value={values.password}
                onChange={handleChange}
                onPress={handlePasswordChange}
                placeholder="Password"
                isInvalid={!!errors.password}
              />
              <Form.Control.Feedback className="FeedBack" type="invalid">
              {errors.password}
            </Form.Control.Feedback>
            </Form.Group>
    
            <Form.Group>
              <Form.Control
                className="NewUserForm"
                size="lg"
                name="confirmPassword"            
                type="password"
                id="confirmPassword" 
                value={values.confirmPassword}
                onChange={handleChange}          
                placeholder="Confirm Password"
                isInvalid={!!errors.confirmPassword}
              /><Form.Control.Feedback className="FeedBack" type="invalid">
              {errors.confirmPassword}
            </Form.Control.Feedback>
            </Form.Group>
    
            <Form.Group>
              <Form.Label></Form.Label>
                 <Form.Control as="textarea" rows="2" 
                 className="NewUserForm"
                 size="lg"
                 name="comment"
                 onChange={handleChange}
                 onPress={handleCommentChange}
                 type="comment"
                 id="comment"
                 value={values.comment}
                 placeholder="Comment"
                 isInvalid={!!errors.comment}
               /><Form.Control.Feedback className="FeedBack" type="invalid">
               {errors.comment}
             </Form.Control.Feedback>
           </Form.Group>

        <Button disabled={!isValid} onClick={handleSubmit} variant="primary" className="SubmitButton mr-2" type="submit">
          Submit
        </Button>
        <Button onClick={handleCancel} variant="primary" className="CancelButton" type="cancel">
          Cancel
        </Button>
        
      </Form>
    </div>)}
    </Formik>
  );
};
//   return (
//     <form className="container">
//       <div className="form-group">
//         <label htmlFor="username">User name</label>
//         <input
//           type="text"
//           className="form-control"
//           id="username"
//           placeholder="Enter user name"
//           onChange={handleUsernameChange}
//           value={username}
//         />
//       </div>
//       <div className="form-group">
//         <label htmlFor="firstName">First Name</label>
//         <input
//           type="text"
//           className="form-control"
//           id="firstName"
//           placeholder="Enter first name"
//           onChange={handleFirstNameChange}
//           value={firstName}
//         />
//       </div>
//       <div className="form-group">
//         <label htmlFor="lastName">Last name</label>
//         <input
//           type="text"
//           className="form-control"
//           id="lastName"
//           placeholder="Enter last name"
//           onChange={handleLastNameChange}
//           value={lastName}
//         />
//       </div>

//       <div className="form-group">
//         <label htmlFor="password">Password</label>
//         <input
//           type="password"
//           className="form-control"
//           id="password"
//           placeholder="Enter password"
//           onChange={handlePasswordChange}
//           value={password}
//         />
//       </div>
//       <div className="form-group">
//         <label htmlFor="comment">Comment</label>
//         <textarea 
//             class="form-control" 
//             id="comment" 
//             rows="3"
//             onChange={handleCommentChange}
//             value={comment}>
//         </textarea>        
//       </div>


//       <button
//         type="submit"
//         onClick={handleSubmit}
//         className="btn btn-success mr-2"
//       >
//         Ok
//       </button>
//       <button onClick={handleCancel} className="btn btn-secondary">
//         Cancel
//       </button>
//     </form>
//   );
// };

export default NewUserFormComponent;