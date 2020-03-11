import React from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import { Modal } from "react-bootstrap";
import AdminHomePageGroupComponent from "./AdminHomePageGroupComponent";
import NewGroupFormComponent from "../../../NewGroupForm/NewGroupFormComponent";
import serverUrl from "../../../URL/ServerUrl";
import ReactPaginate from "react-paginate";

class AdminHomePageGroupContainer extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      show: false,
      groups: [],
      groupName: "",
      offset: 0,
      elements: [],
      perPage: 10,
      currentPage: 0,
      pageCount: 0
    };
  }

  handleCloseModal = () => {
    this.setState({ show: false });
  };

  handleCloseModalAfterSubmit = () => {
    this.setState({ show: false });
    this.getGroups();
  };

  handleShowModal = () => {
    this.setState({ show: true });
  };

  componentDidMount() {
    this.getGroups();
  }
  getGroups = () => {
    axios
      .get(serverUrl + "api/group")
      .then(response => {
        this.setState(
          {
            groups: response.data,
            pageCount: Math.ceil(response.data.length / this.state.perPage)
          },
          () => {
            this.setElementsForCurrentPage();
          }
        );
      })
      .catch(error => {
        console.log(error);
      });
  };

  handlePageClick = data => {
    const selectedPage = data.selected;
    const offset = selectedPage * this.state.perPage;
    this.setState({ currentPage: selectedPage, offset: offset }, () => {
      this.setElementsForCurrentPage();
    });
  };

  setElementsForCurrentPage = () => {
    let elements = this.state.groups.slice(
      this.state.offset,
      this.state.offset + this.state.perPage
    );

    this.setState({ elements: elements });
  };

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
        .get(serverUrl + "api/group/starting-with/" + this.state.groupName)
        .then(response => {
          //   if (response.data.length > 0) {
          this.setState(
            {
              groups: response.data,
              groupName: "",
              pageCount: Math.ceil(response.data.length / this.state.perPage)
            },
            () => {
              this.setElementsForCurrentPage();
            }
          );
          // } else {

          //   this.getGroups();
          // }
        })
        .catch(error => {
          console.log(error);
        });
      document.getElementById("adminGroupSearchInput").value = "";
    }
  };

  render() {
    let paginationElement;
    if (this.state.pageCount > 1) {
      paginationElement = (
        <ReactPaginate
          previousLabel={"← Previous"}
          nextLabel={"Next →"}
          breakLabel={<span className="gap">...</span>}
          pageCount={this.state.pageCount}
          onPageChange={this.handlePageClick}
          forcePage={this.state.currentPage}
          breakClassName={"page-item"}
          breakLinkClassName={"page-link"}
          containerClassName={"pagination"}
          pageClassName={"page-item"}
          pageLinkClassName={"page-link"}
          previousClassName={"page-item"}
          previousLinkClassName={"page-link"}
          nextClassName={"page-item"}
          nextLinkClassName={"page-link"}
          activeClassName={"active"}
        />
      );
    }
    const groupInfo = this.state.elements.map((group, index) => (
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
            Add new group
          </button>
          <Modal show={this.state.show} onHide={this.handleCloseModal}>
            <Modal.Header closeButton>
              <Modal.Title>Create new group</Modal.Title>
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
              placeholder="Group name"
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
              <th scope="col">Group name</th>
              <th scope="col">Group size</th>
              <th scope="col">Comment</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>{groupInfo}</tbody>
        </table>
        <div id="groupsTablePagination">{paginationElement}</div>
      </div>
    );
  }
}

export default withRouter(AdminHomePageGroupContainer);
