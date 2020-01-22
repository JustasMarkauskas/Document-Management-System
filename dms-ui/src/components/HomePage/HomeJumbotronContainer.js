import React from "react";

class HomeJumbotronContainer extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="container jumbotron">
        <h1 className="display-6">FIRST TEST PAGE</h1>

        <h1 className="display-4">Welcome to DMS</h1>
        <p className="lead">This is a testing page</p>
        <hr className="my-4" />
      </div>
    );
  }
}

export default HomeJumbotronContainer;
