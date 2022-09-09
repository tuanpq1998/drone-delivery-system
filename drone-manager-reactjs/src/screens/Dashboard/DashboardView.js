import React, { Component } from "react";
import Heading from "../components/Heading";

export default class DashboardView extends Component {
  render() {
    const { user } = this.props;
    return (
      <>
        <Heading title="Dashboard" />
        <div className="content">
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-12">
                <div className="card card-info">
                  <div className="card-header">Profile</div>
                  <div className="card-body row">
                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Username</label>
                        <input
                          type="text"
                          className="form-control"
                          readOnly
                          value={user.username}
                        />
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Fullname</label>
                        <input
                          type="text"
                          className="form-control"
                          readOnly
                          value={user.fullname}
                        />
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Telephone</label>
                        <input
                          type="text"
                          className="form-control"
                          readOnly
                          value={user.telephone}
                        />
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Email</label>
                        <input
                          type="text"
                          className="form-control"
                          readOnly
                          value={user.email}
                        />
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Address</label>
                        <input
                          type="text"
                          className="form-control"
                          readOnly
                          value={user.address}
                        />
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Role</label>
                        <input
                          type="text"
                          className="form-control"
                          readOnly
                          value={user.role}
                        />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}
