import React, { Component } from "react";

export default class DroneItem extends Component {
  render() {
    const {
      drone,
      handleDisplayOnMap,
      handleHideOnMap,
      assignMode,
      selected,
      handleSelectDrone,
      display,
      handleStartMission,
    } = this.props;

    return (
      <div
        className={
          ((assignMode && selected) || (!assignMode && drone.serverActive)
            ? "card-success"
            : "card-light") + " card card-outline"
        }
      >
        <div className="card-header">
          <h3 className="card-title">
            {drone.droneName} - <mark>{drone.serialNo}</mark>
          </h3>
          <div className="card-tools">
            <span className="btn btn-tool btn-link">#{drone.id}</span>
            {/* <span className="btn btn-tool">
              <i className="fas fa-pen" />
            </span> */}
          </div>
        </div>
        <div className="card-body">
          <dl className="row">
            <dd className="col-md-12">Model name: {drone.modelName}</dd>
            <dd className="col-md-4">
              Max weight: {drone.maxWeightPackageDelivery}
            </dd>
            <dd className="col-md-4">Max speed: {drone.maxSpeed}</dd>
            <dd className="col-md-4">Max height: {drone.maxHeight}</dd>
            <dd className="col-md-8">Created at: {drone.createdAt}</dd>
            <dd className="col-md-12">
              Status:{" "}
              {drone.serverActive ? (
                <span className="badge badge-success">active</span>
              ) : (
                <span className="badge badge-danger">inactive</span>
              )}{" "}
              {drone.serverActive ? (
                drone.activeMissionId == null ? (
                  <span className="badge badge-success">assignable</span>
                ) : (
                  <span className="badge badge-info">in mission</span>
                )
              ) : null}{" "}
              <span className="badge badge-dark">
                {drone.ip} - {drone.port}
              </span>
            </dd>
          </dl>
          {assignMode ? (
            <div className="btn-group">
              {selected ? (
                <button type="button" className="btn btn-success">
                  Selected
                </button>
              ) : (
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => handleSelectDrone(drone.id)}
                >
                  Select
                </button>
              )}
            </div>
          ) : 
            (drone.serverActive ? (
              <div className="btn-group">
                {display ? (
                  <button
                    type="button"
                    onClick={() => handleHideOnMap(drone.id)}
                    className="btn btn-secondary"
                  >
                    Hide
                  </button>
                ) : (
                  <button
                    type="button"
                    className="btn btn-primary"
                    onClick={() => handleDisplayOnMap(drone.id)}
                  >
                    Show
                  </button>
                )}
        
                {drone.activeMission != null && !drone.activeMission.started ? (
                  <button type="button" className="btn btn-success" onClick={() => handleStartMission(drone.id)}>
                    Start
                  </button>
                ) : (
                  null
                )}
              </div>
            ) : null)
          }
        </div>
      </div>
    );
  }
}
