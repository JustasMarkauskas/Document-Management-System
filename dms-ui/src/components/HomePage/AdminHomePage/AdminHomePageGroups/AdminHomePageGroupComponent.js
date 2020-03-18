import React from "react";

class AdminHomePageGroupComponent extends React.Component {
  render() {
    return (
      <tr id={"groupNr" + this.props.rowNr}>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.groupName}</td>
        <td>{this.props.groupSize}</td>
        <td className="comment-width">{this.props.comment}</td>
        <td className="text-right">
          <button
            className="action-btn btn-color"
            onClick={this.props.handleActionClick}
          >
            <i className="fas fa-cog"></i>
          </button>
        </td>
      </tr>
    );
  }
}

export default AdminHomePageGroupComponent;
