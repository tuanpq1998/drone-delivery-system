import React, { Component } from 'react'

export default class DroneInfoGeneral extends Component {
  render() {
    const {drones, isWsConnect} = this.props;
    return (
        <div className="card card-row card-info">
        <div className="card-header">
          <h3 className="card-title">Info</h3>
        </div>
        <div className="card-body">
            <dl className="row">
              <dd className="col-md-2">Connect: </dd>
              <dd className="col-md-10">{isWsConnect ? "Y" : "N"}</dd>
              <dd className="col-md-2">Total: </dd>
              <dd className="col-md-10">{drones.length}</dd>
              <dd className="col-md-2">Online: </dd>
              <dd className="col-md-10">{drones.filter(d => d.serverActive).length}</dd>
              <dd className="col-md-2">Offline: </dd>
              <dd className="col-md-10">{drones.filter(d => !d.serverActive).length}</dd>
            </dl>
        </div>
      </div>
    )
  }
}
