import React, { Component } from "react";
import { NavLink } from "react-router-dom";

export default class Sidebar extends Component {
  render() {
    return (
      <aside className="main-sidebar sidebar-dark-primary elevation-4">
        
          {/* <img src="../../dist/img/AdminLTELogo.png" alt="AdminLTE Logo" className="brand-image img-circle elevation-3" style={{opacity: '.8'}} /> */}
          <span className="brand-text font-weight-light">Pham Quang Tuan</span>
        
        <div className="sidebar">
          
          
          
              
                <div className="user-panel mt-3 pb-3 mb-3 d-flex">
                  <div className="image">
                  <i className='fas fa-cloud-download-alt'></i>


                    {/* <img src="../../dist/img/user2-160x160.jpg" className="img-circle elevation-2" alt="User Image" /> */}
                  </div>
                  <div className="info">
                    <a className="d-block">Drone Delivery System</a>
                  </div>
                </div>
                <div className="form-inline">
                  <div className="input-group" data-widget="sidebar-search">
                    <input
                      className="form-control form-control-sidebar"
                      type="search"
                      placeholder="Search"
                      aria-label="Search"
                    />
                    <div className="input-group-append">
                      <button className="btn btn-sidebar">
                        <i className="fas fa-search fa-fw" />
                      </button>
                    </div>
                  </div>
                  <div className="sidebar-search-results">
                    <div className="list-group">
                      <a href="#" className="list-group-item">
                        <div className="search-title">
                          <strong className="text-light" />N
                          <strong className="text-light" />o
                          <strong className="text-light" />{" "}
                          <strong className="text-light" />e
                          <strong className="text-light" />l
                          <strong className="text-light" />e
                          <strong className="text-light" />m
                          <strong className="text-light" />e
                          <strong className="text-light" />n
                          <strong className="text-light" />t
                          <strong className="text-light" />{" "}
                          <strong className="text-light" />f
                          <strong className="text-light" />o
                          <strong className="text-light" />u
                          <strong className="text-light" />n
                          <strong className="text-light" />d
                          <strong className="text-light" />!
                          <strong className="text-light" />
                        </div>
                        <div className="search-path" />
                      </a>
                    </div>
                  </div>
                </div>
                <nav className="mt-2">
                  <ul
                    className="nav nav-pills nav-sidebar flex-column"
                    data-widget="treeview"
                    role="menu"
                    data-accordion="false"
                  >
                    <li className="nav-item">
                      <NavLink to="/" className="nav-link">
                        <i className="nav-icon fas fa-tachometer-alt" />
                        <p>Dashboard</p>
                      </NavLink>
                    </li>
                    {/* <li className="nav-item">
                      <a href="../widgets.html" className="nav-link">
                        <i className="nav-icon fas fa-th" />
                        <p>
                          Widgets
                          <span className="right badge badge-danger">New</span>
                        </p>
                      </a>
                    </li> */}
                    {/* <li className="nav-header">DRONES</li> */}
                    <li className="nav-item">
                      <NavLink to="/drones" className="nav-link">
                      <i className="nav-icon fas fa-paper-plane" />
                        <p>
                          Drones
                          {/* <span className="badge badge-info right">2</span> */}
                        </p>
                      </NavLink>
                    </li>
                    {/* <li className="nav-header">PACKAGES</li> */}
                    <li className="nav-item">
                      <NavLink to="/packages" className="nav-link">
                      <i className="nav-icon fas fa-box" />
                        <p>
                          Packages
                          {/* <span className="badge badge-info right">2</span> */}
                        </p>
                      </NavLink>
                    </li>
                  </ul>
                </nav>
          
          
        </div>
      </aside>
    );
  }
}
