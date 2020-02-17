import React from "react";

const SavedDocReviewFiles = ({ id, fileName }) => {
  return (
    <p>
      <a
        href={"http://localhost:8081/api/file/downloadFile/" + id}
        className="text-decoration-none"
      >
        {fileName}
      </a>
    </p>
  );
};

export default SavedDocReviewFiles;
