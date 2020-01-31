import React from "react";

class AdminHomePageDocumentComponent extends React.Component {
  render() {
    return (
      <tr>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.documentName}</td>
        <td>{this.props.comment}</td>        
        <td>
          <button
            className="btn btn-primary"
            onClick={this.props.handleActionClick}>
              <i className="fas fa-cog"></i>
          </button>
        </td>
      </tr>
    );
  }
}

export default AdminHomePageDocumentComponent;