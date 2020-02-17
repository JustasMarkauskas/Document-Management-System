import React from "react";
import axios from "axios";
import SavedDocReviewFiles from "./SavedDocReviewFiles";

class SavedDocReviewComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
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
        this.setState({ files: response.data });
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

  onSaveClick = () => {
    console.log("ss" + this.state.docType);
  };

  onSubmitClick = () => {
    console.log("ss" + this.state.docType);
  };

  render() {
    const documentFiles = this.state.files.map((file, index) => (
      <SavedDocReviewFiles key={index} id={file.id} fileName={file.fileName} />
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
      </form>
    );
  }
}

export default SavedDocReviewComponent;
