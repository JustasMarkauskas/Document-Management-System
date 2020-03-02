import React from "react";
import serverUrl from "../../../../URL/ServerUrl";

const SumbittedDFAReviewFiles = ({ id, fileName }) => {
  return (
    <p>
      <a
        href={serverUrl + "api/file/downloadFile/" + id}
        className="text-decoration-none"
      >
        {fileName}
      </a>
    </p>
  );
};

export default SumbittedDFAReviewFiles;
