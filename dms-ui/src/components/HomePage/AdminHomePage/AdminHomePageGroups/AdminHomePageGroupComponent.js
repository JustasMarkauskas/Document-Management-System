import React from "react";

class AdminHomePageGroupComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <tr id={"groupNr" + this.props.rowNr}>
        <th scope="row">{this.props.rowNr}</th>
        <td>{this.props.groupName}</td>
        <td>{this.props.groupSize}</td>
        <td>{this.props.comment}</td>
        <td>
          <button
            className="btn btn-primary"
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
