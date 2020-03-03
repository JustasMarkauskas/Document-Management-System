import React from "react";
import axios from "axios";
import serverUrl from "../../../../../URL/ServerUrl";

class DFAStatisticsReviewContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = { documentCount: [], topAuthors: [] };
  }

  getDocumentStatistics = () => {
    axios
      .get(serverUrl + "api/document/count/" + this.props.docType, {
        params: {
          startDate: this.props.startDate,
          endDate: this.props.endDate
        }
      })
      .then(response => {
        this.setState({ documentCount: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  getTopAuthors = () => {
    axios
      .get(serverUrl + "api/document/topAuthors/" + this.props.docType, {
        params: {
          startDate: this.props.startDate,
          endDate: this.props.endDate
        }
      })
      .then(response => {
        this.setState({ topAuthors: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getDocumentStatistics();
    this.getTopAuthors();
  }

  //this.props.docType
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
              placeholder={this.state.documentCount.submittedCount}
            />
          </div>
          <div className="form-group">
            <label htmlFor="approvedDocs">Number of approved documents</label>
            <input
              type="text"
              id="approvedDocs"
              className="form-control"
              placeholder={this.state.documentCount.approvedCount}
            />
          </div>
          <div className="form-group">
            <label htmlFor="rejectedDocs">Number of rejected documents</label>
            <input
              type="text"
              id="rejectedDocs"
              className="form-control"
              placeholder={this.state.documentCount.rejectedCount}
            />
          </div>
        </fieldset>
        <ul className="list-group">
          {this.state.topAuthors.map((author, index) => {
            return (
              <li
                key={index}
                className="list-group-item d-flex justify-content-between align-items-center"
              >
                {author.author}
                <span className="badge badge-primary badge-pill">
                  {author.authorSubmittedDocuments}
                </span>
              </li>
            );
          })}
        </ul>
      </form>
    );
  }
}

export default DFAStatisticsReviewContainer;
