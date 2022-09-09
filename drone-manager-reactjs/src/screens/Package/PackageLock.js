import React, { Component } from "react";
import { Link, useParams } from "react-router-dom";
import { getTrackingMission } from "../../api/general";
import PackageTracking from "./PackageTracking";

class PackageLock extends Component {
  constructor(props) {
    super(props);
    this.state = {
      password : "",
      isLock : true,
      packageObj : null,
    }
    this.handleSubmit = this.handleSubmit.bind(this);
    this.hanldeChange = this.hanldeChange.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    getTrackingMission(this.state.password, this.props.params.identifier).then(data => {
      if (data.status == 200 && data.data !== "") {
        localStorage.setItem("passwordObj-"+this.props.params.identifier, JSON.stringify({password : this.state.password, expires : (Date.now() + 1000*60*30)}))
        this.setState({
          isLock: false,
          packageObj : data.data
        })
      }
    })
  }

  componentDidMount() {
    const passwordObjRaw = localStorage.getItem("passwordObj-"+this.props.params.identifier);
    if (passwordObjRaw) {
      try {
        const passwordObj = JSON.parse(passwordObjRaw);
        if (passwordObj.password && passwordObj.expires - new Date() > 0) {
          getTrackingMission(passwordObj.password, this.props.params.identifier).then(data => {
            if (data.status == 200 && data.data !== "") {
              this.setState({
                isLock: false,
                packageObj : data.data,
                password : passwordObj.password,
              })
            }
          })
        } else localStorage.removeItem("passwordObj-"+this.props.params.identifier);
      } catch (error) {  localStorage.removeItem("passwordObj-"+this.props.params.identifier)  }
    }
  }

  hanldeChange(event) {
    this.setState({
      password : event.target.value,
    })
  }


  render() {
    const { identifier } = this.props.params;
    const {password, isLock, packageObj} = this.state;
    return isLock ? (
      <>
        <div
          className="overlay"
          style={{
            zIndex: 4545,
            opacity: 0.6,
            position: "absolute",
            top: 0,
            right: 0,
            width: "100%",
            height: "100%",
            background: "#e9ecef",
          }}
        ></div>
        <div
          style={{ zIndex: 4546, position: "relative", right: 0 }}
          className="d-flex align-items-center justify-content-center"
        >
          <div className="lockscreen-wrapper">
            <div className="lockscreen-logo">
              <b>Drone Delivery</b> System
            </div>
            <div style={{textAlign : "center"}} className="lockscreen-name">Package {identifier}</div>
            <div className="lockscreen-item">
              <div className="lockscreen-image">
                <img src="https://www.svgrepo.com/show/64161/package.svg" alt="User Image" />
              </div>
              <form onSubmit={this.handleSubmit} className="lockscreen-credentials">
                <div className="input-group">
                  <input
                    type="password"
                    className="form-control"
                    placeholder="password"
                    value={password}
                    onChange={this.hanldeChange}
                  />
                  <div className="input-group-append">
                    <button type="submit" className="btn">
                      <i className="fas fa-arrow-right text-muted" />
                    </button>
                  </div>
                </div>
              </form>
            </div>
            <div className="help-block text-center">
              Enter your password to find the package!
            </div>
            <div className="text-center">
              <Link to="/login">Or login as seller/admin</Link>
            </div>
          </div>
        </div>
      </>
    ) : < PackageTracking password={password} packageObj={packageObj}/>;
  }
}

export default (props) => <PackageLock {...props} params={useParams()} />;