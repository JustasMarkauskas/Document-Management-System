import React from "react";
import axios from "axios";
import serverUrl from "../../../../../URL/ServerUrl";

class AssignDFCContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      docTypes: [],
      checkedDFC: []
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
    this.setState({ checkedDFC: this.props.docTypesForCreation });
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
    const checkedDFC = this.state.checkedDFC;
    let index;

    if (e.target.checked) {
      checkedDFC.push(e.target.value);
    } else {
      index = checkedDFC.indexOf(e.target.value);
      checkedDFC.splice(index, 1);
    }
    this.setState({ checkedDFC: checkedDFC });
  };

  closeModal = this.props.onHide;
  updateGroup = this.props.updateGroup;

  onSaveClick = event => {
    event.preventDefault();

    const docTypeData = new FormData();

    if (this.state.checkedDFC.length > 0) {
      var i;
      for (i = 0; i < this.state.checkedDFC.length; i++) {
        docTypeData.append(
          "docTypesForCreationNames",
          this.state.checkedDFC[i]
        );
      }
    } else {
      docTypeData.append("docTypesForCreationNames", "");
    }

    axios
      .put(
        serverUrl +
          "api/group/update-group-doctypes-for-creation/" +
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
      this.contains(this.props.docTypesForCreation, docType) ? (
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
      <div className="container" id="assignDFCContainer">
        {allDocTypes}
        <div className="container mt-2">
          <div className="row">
            <button
              onClick={this.onSaveClick}
              type="button"
              className="btn modals-btn-color mr-2"
            >
              Save
            </button>
            <button
              onClick={this.props.onHide}
              type="button"
              className="btn btn-secondary "
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    );
  }
}

export default AssignDFCContainer;
