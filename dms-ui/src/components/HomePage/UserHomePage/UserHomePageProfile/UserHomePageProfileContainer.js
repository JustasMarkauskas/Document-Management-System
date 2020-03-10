import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import serverUrl from "../../../URL/ServerUrl";
import UserHomePageProfileComponent from "./UserHomePageProfileComponent";

class UserHomePageProfileContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      user: [],
      userGroups: ""
    };
  }

  getUser = () => {
    axios.get(serverUrl + "api/user/loggedUsername").then(response => {
      let username = response.data;
      this.setState({ username: response.data });
      axios
        .get(serverUrl + "api/user/" + username)
        .then(response => {
          this.setState({
            user: response.data,
            userGroups: response.data.userGroups
          });
          console.log("user groups: " + this.state.userGroups);
        })

        .catch(error => {
          console.log(error);
        });
    });
  };

  componentDidMount() {
    this.getUser();
  }

  render() {
    return (
      <UserHomePageProfileComponent
      username={this.state.user.username}
      firstName={this.state.user.firstName}
      lastName={this.state.user.lastName}
      userGroups={this.state.user.userGroups} />

      // <div className="my-2">
      //   <div className="row">
      //     <div className="col-lg-3 col-sm-12 my-2 pr-1">
      //       <form id="profileReviewPageId">
      //         <div className="form-group">
      //           <label htmlFor="profileUsername">Username</label>
      //           <input
      //             id="profileUsername"
      //             disabled
      //             type="text"
      //             className="form-control"
      //             placeholder={this.state.user.username}
      //           />
      //         </div>
      //         <div className="form-group">
      //           <label htmlFor="profileFirstName">First name</label>
      //           <input
      //             id="profileFirstName"
      //             disabled
      //             type="text"
      //             className="form-control"
      //             placeholder={this.state.user.firstName}
      //           />
      //         </div>
      //         <div className="form-group">
      //           <label htmlFor="profileLastName">Last name</label>
      //           <input
      //             id="profileLastName"
      //             disabled
      //             type="text"
      //             className="form-control"
      //             placeholder={this.state.user.lastName}
      //           />
      //         </div>
      //         <button
      //           onClick={this.props.onChangePassword}
      //           type="button"
      //           className="btn btn-secondary "
      //         >
      //           Change password
      //         </button>
      //       </form>
      //     </div>
      //     <div className="col-lg-3 col-sm-12 my-2 px-1">
      //       <div className="card">
      //         <div className="card-body">
      //           <h5 className="card-title">Groups</h5>
      //           <div className="card-text scroll">
      //             <ul className="list-group mb-2">
      //               {/* {userGroups} */}
      //               {/* {this.state.userGroups.map((group, index) => {
      //                 return (
      //                   <div key={index}>
      //                     <li className="list-group-item">{group}</li>
      //                   </div>
      //                 );
      //               })} */}
      //             </ul>
      //           </div>
      //         </div>
      //       </div>
      //     </div>
      //   </div>
      // </div>
    );
  }
}

export default withRouter(UserHomePageProfileContainer);
