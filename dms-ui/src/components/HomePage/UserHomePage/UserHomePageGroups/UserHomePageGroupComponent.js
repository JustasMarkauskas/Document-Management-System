import React from "react";

class UserHomePageGroupComponent extends React.Component {
  render() {
    return (
      <tr>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.groupName}</td>
        {/* <td>{this.props.size}</td>         */}
        <td>
          <button
            className="btn btn-primary"
            onClick={this.props.handleActionClick}
            id={"userGroupNr" + this.props.rowNr}
          >
            <i className="fas fa-cog"></i>
          </button>
        </td>
      </tr>
    );
  }
}

export default UserHomePageGroupComponent;
