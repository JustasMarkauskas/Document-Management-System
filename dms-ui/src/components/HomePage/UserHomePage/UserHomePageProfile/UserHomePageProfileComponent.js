import React from "react";

class UserHomePageProfileComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userGroups: []
    };
  }

  componentDidUpdate(prevProps) {
    if (this.props.userGroups !== prevProps.userGroups) {
      this.setState({
        userGroups: this.props.userGroups
      });
    }
  }
  render() {
    return (
      <div className="my-2">
        <div className="row">
          <div className="col-lg-3 col-sm-12 my-2 pr-1">
            <form id="profileReviewPageId">
              <div className="form-group">
                <label htmlFor="profileUsername">Username</label>
                <input
                  id="profileUsername"
                  disabled
                  type="text"
                  className="form-control"
                  placeholder={this.props.username}
                />
              </div>
              <div className="form-group">
                <label htmlFor="profileFirstName">First name</label>
                <input
                  id="profileFirstName"
                  disabled
                  type="text"
                  className="form-control"
                  placeholder={this.props.firstName}
                />
              </div>
              <div className="form-group">
                <label htmlFor="profileLastName">Last name</label>
                <input
                  id="profileLastName"
                  disabled
                  type="text"
                  className="form-control"
                  placeholder={this.props.lastName}
                />
              </div>
              <button
                onClick={this.props.onChangePassword}
                type="button"
                className="btn btn-secondary "
              >
                Change password
              </button>
            </form>
          </div>
          <div className="col-lg-3 col-sm-12 my-2 px-1">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Groups</h5>
                <div className="card-text scroll">
                  <ul className="list-group mb-2">
                    {/* {userGroups} */}
                    {this.state.userGroups.map((group, index) => {
                      return (
                        <div key={index}>
                          <li className="list-group-item">{group}</li>
                        </div>
                      );
                    })}
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default UserHomePageProfileComponent;
