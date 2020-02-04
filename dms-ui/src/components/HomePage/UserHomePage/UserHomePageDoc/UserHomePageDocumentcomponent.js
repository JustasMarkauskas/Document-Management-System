import React from "react";

class UserHomePageDocumentComponent extends React.Component {
  render() {
    return (
      <tr>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.documentName}</td>
        <td>{this.props.Status}</td>        
        <td>
          <button
            className="btn btn-primary"
            onClick={this.props.handleActionClick}
            id={"userDocumentNr"+this.props.rowNr}
          >
            <i className="fas fa-cog"></i>
          </button>
        </td>
      </tr>
    );
  }
}

export default UserHomePageDocumentComponent;