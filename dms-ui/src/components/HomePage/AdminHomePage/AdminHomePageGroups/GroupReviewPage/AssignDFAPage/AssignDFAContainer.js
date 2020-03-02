import React from "react";
import axios from "axios";
import serverUrl from "../../../../../URL/ServerUrl";

class AssignDFAContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypes: [],
      checkedDFA: []
    };
  }

  getUsers = () => {
    axios
      .get(serverUrl + "api/doctype/doc-types")
      .then(response => {
        this.setState({ docTypes: response.data });
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.getUsers();
    this.setState({ checkedDFA: this.props.docTypesForApproval });
  }

  contains(docTypes, docType) {
    var i = docTypes.length;
    while (i--) {
      if (docTypes[i] === docType) {
        return true;
      }
    }
    return false;
  }

  onCheckboxClick = e => {
    const checkedDFA = this.state.checkedDFA;
    let index;

    if (e.target.checked) {
      checkedDFA.push(e.target.value);
    } else {
      index = checkedDFA.indexOf(e.target.value);
      checkedDFA.splice(index, 1);
    }
    this.setState({ checkedDFA: checkedDFA });
  };

  closeModal = this.props.onHide;
  updateGroup = this.props.updateGroup;

  onSaveClick = event => {
    event.preventDefault();

    const docTypeData = new FormData();

    if (this.state.checkedDFA.length > 0) {
      var i;
      for (i = 0; i < this.state.checkedDFA.length; i++) {
        docTypeData.append(
          "docTypesForApprovalNames",
          this.state.checkedDFA[i]
        );
      }
    } else {
      docTypeData.append("docTypesForApprovalNames", "");
    }

    axios
      .put(
        serverUrl +
          "api/group/update-group-doctypes-for-approval/" +
          this.props.groupName,
        docTypeData
      )
      .then(() => {
        this.closeModal();
        this.updateGroup();
      })
      .catch(error => {
        console.log(error);
      });
  };

  render() {
    const allDocTypes = this.state.docTypes.map((docType, index) =>
      this.contains(this.props.docTypesForApproval, docType) ? (
        <div className="form-check" key={index}>
          <input
            className="form-check-input"
            type="checkbox"
            value={docType}
            defaultChecked
            onClick={this.onCheckboxClick}
            id={"docType" + index}
          />

          <label className="form-check-label" htmlFor={"docType" + index}>
            {docType}
          </label>
        </div>
      ) : (
        <div className="form-check" key={index}>
          <input
            className="form-check-input"
            type="checkbox"
            value={docType}
            onClick={this.onCheckboxClick}
            id={"docType" + index}
          />

          <label className="form-check-label" htmlFor={"docType" + index}>
            {docType}
          </label>
        </div>
      )
    );

    return (
      <div className="container">
        {allDocTypes}
        <div className="container mt-2">
          <div className="row">
            <button
              onClick={this.onSaveClick}
              type="button"
              className="btn btn-primary mr-2"
            >
              Save
            </button>
            <button
              onClick={this.props.onHide}
              type="button"
              className="btn btn-primary "
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    );
  }
}

export default AssignDFAContainer;
