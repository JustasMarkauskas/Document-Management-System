import React from "react";

class UserHomePageDocumentComponent extends React.Component {
  render() {
    return (
      <tr
        className={
          this.props.status === "SAVED"
            ? "table-warning"
            : this.props.status === "SUBMITTED"
            ? "table-primary"
            : "table-secondary"
        }
      >
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.title}</td>
        <td>{this.props.docType}</td>
        <td>{this.props.status}</td>
        <td>{this.props.submissionDate}</td>
        <td>{this.props.reviewDate}</td>
        <td>
          <button
            className="btn btn-primary"
            onClick={this.props.handleActionClick}
            id={"userDocumentNr" + this.props.rowNr}
          >
            <i className="fas fa-cog"></i>
          </button>
        </td>
      </tr>
    );
  }
}

export default UserHomePageDocumentComponent;
