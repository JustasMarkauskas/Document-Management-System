import React from "react";
import axios, { put } from "axios";

class SavedDocReviewComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      downloadFiles: [],
      files: [],
      docType: "",
      title: "",
      description: ""
    };
  }

  getDocumentFiles = () => {
    axios
      .get("http://localhost:8081/api/file/" + this.props.id)
      .then(response => {
        this.setState({
          downloadFiles: response.data,
          docType: this.props.docType,
          title: this.props.title,
          description: this.props.description
        });
        if (this.state.downloadFiles.length > 0) {
          document
            .getElementById("uploadFileInfo")
            .setAttribute("class", "text-info small d-none");
        } else {
          document
            .getElementById("uploadFileInfo")
            .setAttribute("class", "text-info small");
        }
      })
      .catch(error => {
        console.log(error);
      });
  };

  updateFileInfoAfterDelete = () => {
    axios
      .get("http://localhost:8081/api/file/" + this.props.id)
      .then(response => {
        this.setState({
          downloadFiles: response.data
        });
        if (this.state.downloadFiles.length > 0) {
          document
            .getElementById("uploadFileInfo")
            .setAttribute("class", "text-info small d-none");
        } else {
          document
            .getElementById("uploadFileInfo")
            .setAttribute("class", "text-info small");
        }
      })
      .catch(error => {
        console.log(error);
      });
  };

  componentDidUpdate(prevProps) {
    if (this.props.id !== prevProps.id) {
      this.getDocumentFiles();
    }
  }

  handleSubmitButtonValidation = () => {
    var formIsValid = true;
    if (
      this.state.title.trim().length < 5 ||
      this.state.title.trim().length > 30 ||
      this.state.description.trim().length < 5 ||
      this.state.description.trim().length > 50 ||
      (this.state.downloadFiles < 1 && this.state.files < 1)
    ) {
      formIsValid = false;
    }

    return formIsValid;
  };

  handleSaveButtonValidation = () => {
    var formIsValid = true;
    if (
      this.state.title.trim().length < 5 ||
      this.state.title.trim().length > 30 ||
      this.state.description.trim().length < 5 ||
      this.state.description.trim().length > 50
    ) {
      formIsValid = false;
    }

    return formIsValid;
  };

  handleDocTypeChange = event => {
    this.setState({ docType: event.target.value });
  };

  handleTitleChange = event => {
    if (
      event.target.value.trim().length < 5 ||
      event.target.value.trim().length > 30
    ) {
      document
        .getElementById("savedTitle")
        .setAttribute("class", "form-control is-invalid");
    } else {
      document
        .getElementById("savedTitle")
        .setAttribute("class", "form-control");
    }
    this.setState({ title: event.target.value });
  };

  handleDescriptionChange = event => {
    if (
      event.target.value.trim().length < 5 ||
      event.target.value.trim().length > 50
    ) {
      document
        .getElementById("savedDescription")
        .setAttribute("class", "form-control is-invalid");
    } else {
      document
        .getElementById("savedDescription")
        .setAttribute("class", "form-control");
    }
    this.setState({ description: event.target.value });
  };

  onFileChange = event => {
    this.setState({ files: event.target.files });
    if (event.target.files.length > 0) {
      document
        .getElementById("uploadFileInfo")
        .setAttribute("class", "text-info small d-none");
    } else {
      document
        .getElementById("uploadFileInfo")
        .setAttribute("class", "text-info small");
    }
  };

  closeModal = this.props.onHide;
  updateDocuments = this.props.updateDocuments;

  onSaveClick = event => {
    event.preventDefault();
    this.fileSaveUpload(this.state.files).then(() => {
      this.closeModal();
      this.updateDocuments();
    });
  };

  onSubmitClick = event => {
    event.preventDefault();

    this.fileSubmitUpload(this.state.files).then(() => {
      this.closeModal();
      this.updateDocuments();
    });
  };

  onDeleteDocumentClick = event => {
    event.preventDefault();
    axios
      .delete("http://localhost:8081/api/document/" + this.props.id)
      .then(() => {
        this.closeModal();
        this.updateDocuments();
      })
      .catch(error => {
        console.log(error);
      });
  };

  fileSaveUpload = files => {
    const url =
      "http://localhost:8081/api/document/save-after-save/" + this.props.id;
    const formData = new FormData();

    var i;
    for (i = 0; i <= files.length; i++) {
      formData.append("files", files[i]);
    }
    formData.append("description", this.state.description.trim());
    formData.append("docType", this.state.docType);
    formData.append("title", this.state.title.trim());
    const config = {
      headers: {
        "content-type": "multipart/form-data"
      }
    };
    return put(url, formData, config);
  };

  fileSubmitUpload = files => {
    const url =
      "http://localhost:8081/api/document/submit-after-save/" + this.props.id;
    const formData = new FormData();

    var i;
    for (i = 0; i <= files.length; i++) {
      formData.append("files", files[i]);
    }
    formData.append("description", this.state.description);
    formData.append("docType", this.state.docType);
    formData.append("title", this.state.title);
    const config = {
      headers: {
        "content-type": "multipart/form-data"
      }
    };
    return put(url, formData, config);
  };

  deleteFile(fileId) {
    axios
      .delete("http://localhost:8081/api/file/" + fileId)
      .then(() => {
        this.updateFileInfoAfterDelete();
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    const documentFiles = this.state.downloadFiles.map((file, index) => (
      <div key={index}>
        <a
          href={"http://localhost:8081/api/file/downloadFile/" + file.id}
          className="text-decoration-none mr-2"
        >
          {file.fileName}
        </a>
        <button
          className="btn"
          onClick={() => this.deleteFile(file.id)}
          type="button"
        >
          <i className="fas fa-trash"></i>
        </button>
      </div>
    ));

    return (
      <form className="container" id="SavedDocReview">
        <div className="form-group">
          <label htmlFor="savedTitle">Title</label>
          <input
            type="text"
            id="savedTitle"
            className="form-control"
            required
            onChange={this.handleTitleChange}
            defaultValue={this.props.title}
            placeholder="Title"
          />
          <div className="invalid-feedback text-info">
            Must be 5-30 characters long
          </div>
        </div>

        <div className="form-group">
          <label htmlFor="savedDocType">Document type</label>

          <select
            className="form-control"
            id="savedDocType"
            value={this.state.docType}
            onChange={this.handleDocTypeChange}
          >
            {this.props.userDocTypes.map((option, index) => {
              return (
                <option key={index} value={option}>
                  {option}
                </option>
              );
            })}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="savedDescription">Description</label>
          <textarea
            type="text"
            id="savedDescription"
            className="form-control"
            onChange={this.handleDescriptionChange}
            defaultValue={this.props.description}
            placeholder="Description"
          />
          <div className="invalid-feedback text-info small">
            Must be 5-50 characters long
          </div>
        </div>
        <div className="form-group">
          <input type="file" multiple onChange={this.onFileChange} />
          <div id="uploadFileInfo" className="text-info small d-none">
            At least one file has to be selected to Submit the form
          </div>
        </div>
        {documentFiles}

        <div className="container mt-2">
          <div className="row">
            <div className="mr-2">
              <button
                disabled={!this.handleSubmitButtonValidation()}
                onClick={this.onSubmitClick}
                type="button"
                className="btn btn-primary"
              >
                Submit
              </button>
            </div>
            <div className="mr-2 ">
              <button
                disabled={!this.handleSaveButtonValidation()}
                onClick={this.onSaveClick}
                type="button"
                className="btn btn-primary"
              >
                Save
              </button>
            </div>

            <div className="mr-2 ">
              <button
                onClick={this.props.onHide}
                type="button"
                className="btn btn-primary"
              >
                Cancel
              </button>
            </div>

            <div className="col text-right">
              <button
                className="btn text-danger"
                onClick={this.onDeleteDocumentClick}
                type="button"
              >
                <i className="fas fa-trash-alt fa-lg"></i>
              </button>
            </div>
          </div>
        </div>
      </form>
    );
  }
}

export default SavedDocReviewComponent;
