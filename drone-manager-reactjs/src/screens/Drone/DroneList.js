import React, { Component } from "react";
import { Link } from "react-router-dom";
import DroneItem from "./DroneItem";

export default class DroneList extends Component {
  render() {
    const { drones, handleDisplayOnMap, handleHideOnMap, assignMode, selectedDroneId , handleSelectDrone, displayIds, handleStartMission} =
      this.props;
    return (
      <div className="card card-row">
        {assignMode ? (
          ""
        ) : (
          <div className="card-header">
            <h3 className="card-title m-2">List</h3>
            <Link className="btn btn-info float-right" to="add" >+ Add</Link>
          </div>
        )}
        <div className="card-body">
          <div className="card">
            <div className="input-group">
              <input
                type="search"
                className="form-control form-control-lg"
                placeholder="Search"
              />
              <div className="input-group-append">
                <button type="submit" className="btn btn-lg btn-default">
                  <i className="fa fa-search" />
                </button>
              </div>
            </div>
          </div>
          <div style={{
            padding : "5px",
            overflowY : "auto",
            maxHeight : assignMode ? "500px" :"100vh",
          }} >
          {drones.map((d) => (
            <DroneItem
              drone={d}
              key={d.id}
              assignMode={assignMode}
              handleHideOnMap={handleHideOnMap}
              handleDisplayOnMap={handleDisplayOnMap}
              selected={d.id == selectedDroneId}
              handleSelectDrone={handleSelectDrone}
              display={displayIds && displayIds.includes(d.id)}
              handleStartMission={handleStartMission}
            />
          ))}
          </div>
        </div>
      </div>
    );
  }
}
