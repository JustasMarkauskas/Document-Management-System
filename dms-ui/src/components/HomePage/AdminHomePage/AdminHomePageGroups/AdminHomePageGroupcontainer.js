import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import { Modal } from "react-bootstrap";
import AdminHomePageGroupComponent from "./AdminHomePageGroupComponent";
import NewGroupFormComponent from "../../../NewGroupForm/NewGroupFormComponent";

class AdminHomePageGroupContainer extends React.Component {
  constructor(props) {
    super(props);

    this.handleShowModal = this.handleShowModal.bind(this);
    this.handleCloseModal = this.handleCloseModal.bind(this);

    this.state = {
      show: false,
      groups: [],
      groupName: ""
    };
  }

  handleCloseModal() {
    this.setState({ show: false });
  }

  handleShowModal() {
    this.setState({ show: true });
  }

  getGroups = () => {
    axios
      .get("http://localhost:8081/api/group")
      .then(response => {
        this.setState({ groups: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };
  componentDidMount() {
    this.getGroups();
  }

  handleActionClick = event => {
    event.preventDefault();
    this.props.history.push("/group-info"); //navigacija teisinga padaryti
  };

  handleAddGroupButton = event => {
    event.preventDefault();
    this.props.history.push("/new-group"); //navigacija teisinga padaryti
  };

  handleSearchChange = event => {
    this.setState({ groupName: event.target.value });
  };

  handleSearchButton = event => {
    event.preventDefault();
    axios
      .get("http://localhost:8081/api/group/" + this.state.groupName)
      .then(response => {
        this.setState({ groups: [response.data] });
      })
      .catch(error => {
        console.log(error);
      });
    document.getElementById("adminGroupSearchInput").value = "";
  };

  render() {
    const groupInfo = this.state.groups.map((group, index) => (
      <AdminHomePageGroupComponent
        key={index}
        rowNr={index + 1}
        groupName={group.id}
        groupSize={group.groupSize}
        comment={group.comment}
        handleActionClick={this.handleActionClick}
      />
    ));

    return (
      <div className="container">
        <div className="row col-lg-12">
          <button
            onClick={this.handleShowModal}
            type="button"
            className="btn btn-primary col-lg-3 mb-2"
            id="adminAddNewGroupButton"
          >
            Add new Group
          </button>
          <Modal show={this.state.show} onHide={this.handleCloseModal}>
            <Modal.Header closeButton>
              <Modal.Title>Create New Group</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              {" "}
              <NewGroupFormComponent onCloseModal={this.handleCloseModal} />
            </Modal.Body>
          </Modal>
          <div className="input-group mb-3 col-lg-5">
            <input
              onChange={this.handleSearchChange}
              type="text"
              className="form-control"
              placeholder="Group"
              aria-label="Group"
              aria-describedby="button-addon2"
              id="adminGroupSearchInput"
            ></input>
            <div className="input-group-append">
              <button
                onClick={this.handleSearchButton}
                className="btn btn-primary"
                type="button"
                id="adminGroupSearchButton"
              >
                Search
              </button>
            </div>
          </div>
        </div>

        <table className="table">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Group Name</th>
              <th scope="col">Group Size</th>
              <th scope="col">Comment</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>{groupInfo}</tbody>
        </table>
      </div>
    );
  }
}

export default withRouter(AdminHomePageGroupContainer);
