import React, { Component } from 'react'

export default class DroneInfo extends Component {
  constructor(props) {
    super(props);
  
    this.handleClickStopAndLand = this.handleClickStopAndLand.bind(this)
    this.handleClickStopAndReturn = this.handleClickStopAndReturn.bind(this);
    this.state = {
      clickELand : false,
      clickEReturn : false,
    }
  }

  handleClickStopAndLand(droneId) {
    console.log(droneId);
    this.setState({
      clickELand : true,
    })
    this.props.handleStopAndLand(droneId);
  }

  handleClickStopAndReturn(droneId) {
    console.log(droneId);
    this.setState({
      clickEReturn : true,
    })
    this.props.handleStopAndReturn(droneId);
  }

  handleClickCancelEmergency(droneId) {
    this.props.handleCancelEmergency(droneId);
  }

  render() {
    const {drone} = this.props;
    const {clickELand, clickEReturn} = this.state;

    var emergencyPart = null;
    if (drone.activeMission &&  !drone.emergency) {
      if (clickELand) 
        emergencyPart = (
          <div className="col-md-12">
          <button type="button" className="btn btn-warning mr-1" disabled>E.Landing ...</button>
        </div>
        )
       else if (clickEReturn) 
       emergencyPart = (
        <div className="col-md-12">
          <button type="button" className="btn btn-warning mr-1" disabled>E.Return ...</button>
        </div>
      ) 
      else
      emergencyPart = (
        <div className="col-md-12">
          <button type="button" className="btn btn-warning mr-1" 
            onClick={() => this.handleClickStopAndLand(drone.id)}>E.Landing</button>
          <button type="button" className="btn btn-warning" 
            onClick={() => this.handleClickStopAndReturn(drone.id)}>E.Return</button>
        </div>
      )
    } else if (drone.emergency) 
      emergencyPart = (
        <div className="col-md-12">
          <button type="button" className="btn btn-warning mr-1" 
            onClick={() => this.handleClickCancelEmergency(drone.id)}>Resolve E-mode</button>
        </div>
      )
    return drone!=null ? (
        <div className="card card-row card-info">
        <div className="card-header">
          <h3 className="card-title">Drone {drone.droneName} - #{drone.serialNo}</h3>
        </div>
        <div className="card-body">
            <dl className="row">
              {emergencyPart}
              
              <dt className="col-md-2">Altitude</dt>
              <dd className="col-md-2">{drone.locationAltitude}</dd>

              <dt className="col-md-2">Velocity vertical</dt>
              <dd className="col-md-2">{drone.velocityVertical} m/s</dd>

              <dt className="col-md-2">Velocity horizontal</dt>
              <dd className="col-md-2">{drone.velocityHorizontal} m/s</dd>

              <dt className="col-md-2">Battery</dt>
              <dd className="col-md-4">{drone.batteryPercent*100}% - {drone.batteryVoltage}V</dd>

              <dt className="col-md-2">Last updated: </dt>
              <dd className="col-md-4">{drone.lastUpdatedAt}</dd>
              { drone.activeMission ? (
              <>
                <dt className="col-md-2">Status: </dt>
                <dd className="col-md-10">{drone.activeMission.status}</dd>
                <dt className="col-md-2">On mission: </dt>
                <dd className="col-md-10"><code>#{drone.activeMission.missionIdentifier}</code></dd>
                <dt className="col-md-2">Logs: </dt>
                <dd className="col-md-10" style={{backgroundColor: "#faebd7"}}>
                  <pre>
                    <code>{drone.activeMission.logs == null ? "" : drone.activeMission.logs.split("|").filter(l => l.trim() !== "").reverse().join("\n")}</code>
                  </pre>
                </dd>
              </>
              ): null}
            </dl>
        </div>
      </div>
    ) : null;
  }
}
