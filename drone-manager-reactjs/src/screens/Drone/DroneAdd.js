import React, { Component } from "react";
import { Link, useNavigate } from "react-router-dom";
import { createDrone } from "../../api/drone";
import Heading from "../components/Heading";

class DroneAdd extends Component {
  constructor(props) {
    super(props);

    this.state = {
      droneName: "",
      modelName: "",
      serialNo: "",
      maxHeight: 10,
      maxWeightPackageDelivery: 10,
      maxSpeed: 10,
      connectType : "udp",
      connectIp: "localhost",
      connectPort: "",
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleSelectChange = this.handleSelectChange.bind(this);
  }

  handleInputChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  handleSelectChange(event) {
    this.setState({
      connectType : event.target.value
    })
  }

  handleSubmit(event) {
    event.preventDefault();
    const drone = this.state;
    createDrone(drone).then(data => {
        if (data.data.id != null) {
            this.props.navigate("../drones")
        }
    })
  }

  render() {
    const {
      droneName,
      modelName,
      serialNo,
      maxHeight,
      maxWeightPackageDelivery,
      maxSpeed,
      connectType,
      connectIp,
      connectPort,
    } = this.state;
    return (
      <>
        <Heading title="Add drone" />
        <div className="content">
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-12">
                <div className="card card-info">
                  <div className="card-header">
                    <h3 className="card-title">Enter infomation</h3>
                  </div>

                  <form onSubmit={this.handleSubmit}>
                    <div className="card-body row">
                      <div className="col-md-12">
                        <div className="row">
                          <div className="form-group col-md-4">
                            <label htmlFor="droneName">Drone name: </label>
                            <input
                              onChange={this.handleInputChange}
                              value={droneName}
                              type="text"
                              className="form-control"
                              id="droneName"
                              name="droneName"
                            />
                          </div>
                          <div className="form-group col-md-4">
                            <label htmlFor="modelName">Model name: </label>
                            <input
                              onChange={this.handleInputChange}
                              value={modelName}
                              type="text"
                              className="form-control"
                              id="modelName"
                              name="modelName"
                            />
                          </div>
                          <div className="form-group col-md-4">
                            <label htmlFor="serialNo">Serial: </label>
                            <input
                              onChange={this.handleInputChange}
                              value={serialNo}
                              type="text"
                              className="form-control"
                              id="serialNo"
                              name="serialNo"
                            />
                          </div>
                        </div>

                        <div className="row">
                          <div className="form-group col-md-4">
                            <label htmlFor="maxHeight">Max height: </label>
                            <input
                              onChange={this.handleInputChange}
                              value={maxHeight}
                              type="number"
                              className="form-control"
                              id="maxHeight"
                              name="maxHeight"
                            />
                          </div>
                          <div className="form-group  col-md-4">
                            <label htmlFor="maxWeightPackageDelivery">
                              Max weight:
                            </label>
                            <input
                              onChange={this.handleInputChange}
                              value={maxWeightPackageDelivery}
                              type="number"
                              className="form-control"
                              id="maxWeightPackageDelivery"
                              name="maxWeightPackageDelivery"
                            />
                          </div>
                          <div className="form-group  col-md-4">
                            <label htmlFor="maxSpeed">Max speed: </label>
                            <input
                              onChange={this.handleInputChange}
                              value={maxSpeed}
                              type="number"
                              className="form-control"
                              id="maxSpeed"
                              name="maxSpeed"
                            />
                          </div>
                        </div>
                        <div className="row">
                        <div className="form-group col-md-4">
                            <label htmlFor="connectType">Type: </label>
                            <select onChange={this.handleSelectChange} 
                              value={connectType} 
                              className="form-control" 
                              id="connectType" 
                              name="connectType"> 
                              <option value="tcp">TCP</option>
                              <option value="udp">UDP</option>
                              <option value="serial">Serial</option>
                            </select>
                          </div>

                          <div className="form-group col-md-4">
                            <label htmlFor="connectIp">IP: </label>
                            <input
                              onChange={this.handleInputChange}
                              value={connectIp}
                              type="text"
                              className="form-control"
                              id="connectIp"
                              name="connectIp"
                            />
                          </div>
                          <div className="form-group  col-md-4">
                            <label htmlFor="connectPort">Port: </label>
                            <input
                              onChange={this.handleInputChange}
                              value={connectPort}
                              type="text"
                              className="form-control"
                              id="connectPort"
                              name="connectPort"
                            />
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="card-footer">
                      <button type="submit" className="btn btn-primary">
                        Submit
                      </button>
                      {/* <input type="reset" value="Reset" className="ml-1 btn btn-secondary" /> */}
                      <Link className="ml-1 btn btn-link" to="../drones/">
                        Back
                      </Link>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}

export default (props) => <DroneAdd {...props} navigate={useNavigate()}/>;
