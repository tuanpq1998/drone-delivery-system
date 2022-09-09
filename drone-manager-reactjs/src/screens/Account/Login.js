import React, { Component } from "react";
import { useNavigate } from "react-router-dom";
import { getMyInfo, login } from "../../api/account";

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      rememberMe: false,
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmitClick = this.handleSubmitClick.bind(this);
    this.toggleChange = this.toggleChange.bind(this);
  }

  handleInputChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  toggleChange() {
    this.setState({
      rememberMe: !this.state.rememberMe,
    });
  }

  handleSubmitClick(event) {
    event.preventDefault();
    const { username, password, rememberMe } = this.state;
    login({ username, password }).then((data1) => {
      getMyInfo().then((data2) => {
        this.props.navigate("../");
      });
    });
  }

  render() {
    const { username, password, rememberMe } = this.state;

    return (
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
          <div className="login-box">
            <div className="login-logo">
              <b>Drone Delivery</b> System
            </div>
            <div className="card">
              <div className="card-body login-card-body">
                <p className="login-box-msg">Sign in to start your session</p>
                <form onSubmit={this.handleSubmitClick}>
                  <div className="input-group mb-3">
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Username"
                      name="username"
                      value={username}
                      onChange={this.handleInputChange}
                    />
                  </div>
                  <div className="input-group mb-3">
                    <input
                      type="password"
                      className="form-control"
                      placeholder="Password"
                      name="password"
                      value={password}
                      onChange={this.handleInputChange}
                    />
                  </div>
                  <div className="row">
                    <div className="col-8">
                      <div className="icheck-primary">
                        <input
                          type="checkbox"
                          id="remember"
                          name="rememberMe"
                          defaultChecked={rememberMe}
                          onChange={this.toggleChange}
                        />
                        <label htmlFor="remember">Remember Me</label>
                      </div>
                    </div>
                    <div className="col-4">
                      <button
                        type="submit"
                        className="btn btn-primary btn-block"
                      >
                        Sign In
                      </button>
                    </div>
                  </div>
                </form>

                {/* <p className="mb-1">
              <a href="forgot-password.html">I forgot my password</a>
            </p> */}
                <p className="mb-0">
                  <a href="register.html" className="text-center">
                    Register a new membership
                  </a>
                </p>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}

export default (props) => <Login {...props} navigate={useNavigate()} />;
