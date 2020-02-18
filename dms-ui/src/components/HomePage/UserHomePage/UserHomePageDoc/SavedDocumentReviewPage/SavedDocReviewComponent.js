import React from "react";
import axios, { post, put } from "axios";

class SavedDocReviewComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      downloadFiles: [],
      files: [],
      document: {
        docType: "",
        title: "",
        description: ""
      }
    };
  }

  getDocumentFiles = () => {
    axios
      .get("http://localhost:8081/api/file/" + this.props.id)
      .then(response => {
        this.setState({ downloadFiles: response.data });
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

  handleDocTypeChange = event => {
    this.setState({ docType: event.target.value });
  };

  handleTitleChange = event => {
    this.setState({ title: event.target.value });
  };

  handleDescriptionChange = event => {
    this.setState({ description: event.target.value });
  };

  onFileChange = event => {
    this.setState({ files: event.target.files });
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
        this.getDocumentFiles();
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
      <form className="container">
        <div className="form-group">
          <label htmlFor="savedDocType">Doc type</label>
          <input
            type="text"
            id="savedDocType"
            className="form-control"
            placeholder="select doc type"
            onChange={this.handleDocTypeChange}
            defaultValue={this.props.docType}
          />
        </div>
        <div className="form-group">
          <label htmlFor="savedTitle">Title</label>
          <input
            type="text"
            id="savedTitle"
            className="form-control"
            onChange={this.handleTitleChange}
            defaultValue={this.props.title}
            placeholder="enter title"
          />
        </div>
        <div className="form-group">
          <label htmlFor="savedDescription">Description</label>
          <input
            type="text"
            id="savedDescription"
            className="form-control"
            onChange={this.handleDescriptionChange}
            defaultValue={this.props.description}
            placeholder="enter description"
          />
        </div>
        <input type="file" multiple onChange={this.onFileChange} />

        {documentFiles}

        <div className="container mt-2">
          <div className="row">
            <div className="mr-2">
              <button
                onClick={this.onSubmitClick}
                type="button"
                className="btn btn-primary"
              >
                Submit
              </button>
            </div>
            <div className="mr-2 ">
              <button
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