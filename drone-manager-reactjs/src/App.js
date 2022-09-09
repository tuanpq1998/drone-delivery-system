import './App.css';
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import {
  Routes,
  Route,
} from "react-router-dom";
import DroneView from './screens/Drone/DroneView';
import PackageView from './screens/Package/PackageView';
import DashboardView from './screens/Dashboard/DashboardView';
import Footer from './components/Footer';
import PackgeEdit from './screens/Package/PackgeEdit';
import PackageDetail from './screens/Package/PackageDetail';
import PackgeAdd from './screens/Package/PackgeAdd';
import DroneAdd from './screens/Drone/DroneAdd';
import Login from './screens/Account/Login';

import React, { Component } from 'react'
import RouteGuard from './components/RouteGuard';
import PackageLock from './screens/Package/PackageLock';

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      classNameApp : "App layout-fixed sidebar-mini sidebar-collapse"
    }
    this.handleOpenSidebar = this.handleOpenSidebar.bind(this);
    this.handleCloseSidebar = this.handleCloseSidebar.bind(this);
  }

  handleOpenSidebar() {
    if (this.state.classNameApp == "App sidebar-mini sidebar-open") 
    this.setState({
      classNameApp : "App layout-fixed sidebar-mini sidebar-collapse"
    })
    else
    this.setState({
      classNameApp : "App sidebar-mini sidebar-open"
    })
  }

  handleCloseSidebar() {
    if (this.state.classNameApp == "App sidebar-mini sidebar-open") 
      this.setState({
        classNameApp : "App layout-fixed sidebar-mini sidebar-collapse"
      })
  }

  render() {
    return (
      <div className={this.state.classNameApp}>
      
      <Header handleOpenSidebar={this.handleOpenSidebar}/>
      <Sidebar  />
      <div onClick={this.handleCloseSidebar} className="content-wrapper" style={{minHeight: "85vh"}}>
      <Routes >
        <Route path="/" element={<RouteGuard component={DashboardView}/>} />
        <Route path="login" element={<Login/>} />
        <Route path="drones" element={<RouteGuard hasAnyRole={["ADMIN"]} component={DroneView}/>}/>
        <Route path="drones/add" element={<RouteGuard hasAnyRole={["ADMIN"]} component={DroneAdd}/>}/>
        <Route path="packages" element={<RouteGuard hasAnyRole={["ADMIN", "SELLER"]} component={PackageView} />}/>
        <Route path="packages/add" hasAnyRole={["SELLER"]} element={<RouteGuard component={PackgeAdd}  />}/>
        <Route path="tracking/:identifier" element={<PackageLock/>} />
        <Route path="packages/detail/:id" hasAnyRole={["ADMIN", "SELLER"]} element={<RouteGuard component={PackageDetail} />} />
        <Route path="packages/edit/:id" hasAnyRole={["ADMIN", "SELLER"]} element={<RouteGuard component={PackgeEdit} />} />
      </Routes >
      </div>
      <Footer />
    </div>
    )
  }
}

