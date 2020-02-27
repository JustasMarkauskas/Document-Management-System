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
    this.handleCloseModalAfterSubmit = this.handleCloseModalAfterSubmit.bind(
      this
    );

    this.state = {
      show: false,
      groups: [],
      groupName: ""
    };
  }

  refresh() {
    this.getGroups();
    window.location.reload();
  }

  handleCloseModal() {
    this.setState({ show: false });
  }

  handleCloseModalAfterSubmit() {
    this.refresh();
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

  handleSearchChange = event => {
    this.setState({ groupName: event.target.value });
  };

  handleActionClick = (event, groupName) => {
    event.preventDefault();
    this.props.history.push("/group-review/" + groupName);
  };

  handleSearchButton = event => {
    event.preventDefault();
    if (this.state.groupName.length < 1) {
      this.getGroups();
    } else {
      axios
        .get(
          "http://localhost:8081/api/group/starting-with/" +
            this.state.groupName
        )
        .then(response => {
          if (response.data.length > 0) {
            this.setState({ groups: response.data, groupName: "" });
          } else {
            this.getGroups();
          }
        })
        .catch(error => {
          console.log(error);
        });
      document.getElementById("adminGroupSearchInput").value = "";
    }
  };

  render() {
    const groupInfo = this.state.groups.map((group, index) => (
      <AdminHomePageGroupComponent
        key={index}
        rowNr={index + 1}
        groupName={group.id}
        groupSize={group.groupSize}
        comment={group.comment}
        updateGroups={this.getGroups}
        handleActionClick={event => this.handleActionClick(event, group.id)}
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
              <NewGroupFormComponent
                onCloseModalAfterSubmit={this.handleCloseModalAfterSubmit}
                onHide={this.handleCloseModal}
              />
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
