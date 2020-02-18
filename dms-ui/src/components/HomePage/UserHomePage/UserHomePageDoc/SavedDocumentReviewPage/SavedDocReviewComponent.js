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
    });
  };

  onSubmitClick = event => {
    event.preventDefault();
    this.fileSubmitUpload(this.state.files).then(response => {
      console.log(response.data);
    });
    this.closeModal();
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
      .then(response => {
        this.getDocumentFiles();
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    const documentFiles = this.state.downloadFiles.map((file, index) => (
      <p key={index}>
        <a
          href={"http://localhost:8081/api/file/downloadFile/" + file.id}
          className="text-decoration-none mr-3"
        >
          {file.fileName}
        </a>
        <button
          className="btn"
          onClick={() => this.deleteFile(file.id)}
          type="button"
        >
          <i className="fas fa-trash-alt"></i>
        </button>
      </p>
    ));

    return (
      <form className="container">
        <div className="form-group">
          <label htmlFor="savedDocType">Doc type</label>
          <input
            type="text"
            id="savedDocType"
            className="form-control"
            placeholder={this.props.docType}
            onChange={this.handleDocTypeChange}
            //value={this.props.docType}
          />
        </div>
        <div className="form-group">
          <label htmlFor="savedTitle">Title</label>
          <input
            type="text"
            id="savedTitle"
            className="form-control"
            onChange={this.handleTitleChange}
            /// value={this.state.title}
            placeholder={this.props.title}
          />
        </div>
        <div className="form-group">
          <label htmlFor="savedDescription">Description</label>
          <input
            type="text"
            id="savedDescription"
            className="form-control"
            onChange={this.handleDescriptionChange}
            //  value={this.state.description}
            placeholder={this.props.description}
          />
        </div>
        <input type="file" multiple onChange={this.onFileChange} />

        {documentFiles}

        <button
          onClick={this.props.onHide}
          type="button"
          className="btn btn-primary col-lg-2 mb-2"
        >
          Cancel
        </button>

        <button
          onClick={this.onSaveClick}
          type="button"
          className="btn btn-primary col-lg-2 mb-2"
        >
          SAVE
        </button>
        <button
          onClick={this.onSubmitClick}
          type="button"
          className="btn btn-primary col-lg-2 mb-2"
        >
          SUBMIT
        </button>

        <button
          className="btn"
          onClick={this.onDeleteDocumentClick}
          type="button"
        >
          <i className="fas fa-trash-alt"></i>
        </button>
      </form>
    );
  }
}

export default SavedDocReviewComponent;
