import React from "react";
import axios from "axios";
import serverUrl from "../../../../../URL/ServerUrl";

class DFAStatisticsReviewContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  //   getDocument = () => {
  //     axios
  //       .get(serverUrl + "api/document/get/" + this.props.docId + "/")
  //       .then(response => {
  //         this.setState({ document: response.data });
  //       })
  //       .catch(error => {
  //         console.log(error);
  //       });
  //   };

  //   componentDidMount() {
  //     this.getDocument();
  //   }

  render() {
    return (
      <form className="container" id="DFAStatisticsReview">
        <fieldset disabled>
          <div className="form-group">
            <label htmlFor="submittedDocs">Number of submitted documents</label>
            <input
              type="text"
              id="submittedDocs"
              className="form-control"
              placeholder="{this.props.id}"
            />
          </div>
          <div className="form-group">
            <label htmlFor="approvedDocs">Number of approved documents</label>
            <input
              type="text"
              id="approvedDocs"
              className="form-control"
              placeholder="{this.props.author}"
            />
          </div>
          <div className="form-group">
            <label htmlFor="rejectedDocs">Number of rejected documents</label>
            <input
              type="text"
              id="rejectedDocs"
              className="form-control"
              placeholder="{this.props.docType}"
            />
          </div>
        </fieldset>
        <ul className="list-group">
          <li className="list-group-item d-flex justify-content-between align-items-center">
            Cras justo odio
            <span className="badge badge-primary badge-pill">14</span>
          </li>
          <li className="list-group-item d-flex justify-content-between align-items-center">
            Dapibus ac facilisis in
            <span className="badge badge-primary badge-pill">2</span>
          </li>
          <li className="list-group-item d-flex justify-content-between align-items-center">
            Morbi leo risus
            <span className="badge badge-primary badge-pill">1</span>
          </li>
        </ul>
      </form>
    );
  }
}

export default DFAStatisticsReviewContainer;
