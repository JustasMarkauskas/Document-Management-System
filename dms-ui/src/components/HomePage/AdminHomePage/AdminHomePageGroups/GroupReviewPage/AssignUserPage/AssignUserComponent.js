import React from "react";
import { Modal } from "react-bootstrap";
import axios from "axios";

class AssignUserComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="container">
        <div className="checkbox">
          <label>
            <input type="checkbox" value={this.props.username} />
            {"  " + this.props.username}
          </label>
        </div>
      </div>
    );
  }
}

export default AssignUserComponent;
