import React, { Component } from "react";
import Modal from "react-modal";
import { useNavigate } from "react-router-dom";
import { getAssignable } from "../../api/drone";
import { assignMission, startMission } from "../../api/mission";
import DroneList from "../Drone/DroneList";

class ModalAssign extends Component {
  constructor(props) {
    super(props);
    this.state = {
      drones: null,
      assignMissionId: null,
      selectedDroneId : null,
      form : {
        speedMs: 10.0,
        holdingTime: 60.0,
        flyingAltitude: 100,
        senderAltitude: 50,
        receiverAltitude: 50,
      }
    };
    this.handleSelectDrone = this.handleSelectDrone.bind(this);
    this.handleAssignBtnClick = this.handleAssignBtnClick.bind(this);
    this.handleAssignAndStartBtnClick = this.handleAssignAndStartBtnClick.bind(this);
    this.handleOnChangeInput = this.handleOnChangeInput.bind(this);
  }

  componentDidMount() {
    const { assignMissionId } = this.props;
    getAssignable().then((data) => {
      this.setState({
        drones: data.data,
        assignMissionId,
      });
    });
  }

  handleOnChangeInput(event) {
    const {form} = this.state;
    this.setState({
      form : {
        ...form,
        [event.target.name] : Number(event.target.value),
      }
    })
  }

  handleSelectDrone(droneId) {
    this.setState({
      selectedDroneId : droneId,
    })
  }

  handleAssignBtnClick() {
    const {assignMissionId, selectedDroneId, form} = this.state;
    const missionObj = {
      id : assignMissionId,
      ...form
    }
    assignMission(missionObj, selectedDroneId).then(data => {
      // console.log(data.data);
      this.props.handleCloseModal();
      this.props.navigate("../packages");
    })
  }

  handleAssignAndStartBtnClick() {
    const {assignMissionId, selectedDroneId, form} = this.state;
    const _form = {
      speedMs: form.speedMs,
      holdingTime: form.holdingTime,
      flyingAltitude: form.flyingAltitude,
      senderLocationAltitude: form.senderAltitude,
      receiverLocationAltitude: form.receiverAltitude,
    };
    const missionObj = {
      id : assignMissionId,
      ..._form
    }
    assignMission(missionObj, selectedDroneId).then(data => {
      // console.log(data.data);
      if (data.data.activeMission != null) {
        startMission(selectedDroneId).then(data => {
          this.props.handleCloseModal();
          this.props.navigate("../packages");
        })
      }
    })
  }

  render() {
    const { drones, selectedDroneId , form} = this.state;
    const { handleCloseModal, assignMissionId } = this.props;
    return (
      <Modal
        style={{
          content: {
            position: "fixed",
            paddingTop: "100px",
            top: 0,
            left: 0,
            zIndex: 1050,
            width: "100%",
            height: "100%",
            overflow: "hidden",
            outline: 0,
            backgroundColor: "rgba(255, 255, 255, 0)",
          },
          overlay: {
            zIndex: 2000,
          },
        }}
        isOpen={assignMissionId != null}
      >
        <div className="modal-dialog modal-lg">
          <div className="modal-content">
            <div className="modal-header">
              <h4 className="modal-title">
                Assign Mission #{assignMissionId}{" "}
              </h4>
              <button
                type="button"
                className="close"
                data-dismiss="modal"
                aria-label="Close"
                onClick={handleCloseModal}
              >
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div className="modal-body row">
              {drones ? (
                <>
                  <div className="col-md-12">
                    <div className="form-group">
                      <label>Select one: </label>
                      <DroneList drones={drones} assignMode={true} selectedDroneId={selectedDroneId} handleSelectDrone={this.handleSelectDrone}/>
                    </div>
                  </div>
                  <div className="col-md-4">
                    <div className="form-group">
                      <label>Sender altitude </label>
                      <input defaultValue={form.senderAltitude} step="0.1" type="number" className="form-control" name="senderAltitude" onChange={this.handleOnChangeInput} />
                    </div>
                  </div>
                  <div className="col-md-4">
                    <div className="form-group">
                      <label>Receiver altitude </label>
                      <input defaultValue={form.receiverAltitude} step="0.1" type="number" className="form-control" name="receiverAltitude" onChange={this.handleOnChangeInput} />
                    </div>
                  </div>
                  <div className="col-md-4">
                    <div className="form-group">
                      <label>Flying altitude: </label>
                      <input defaultValue={form.flyingAltitude} step="0.1" type="number" className="form-control" name="flyingAltitude" onChange={this.handleOnChangeInput} />
                    </div>
                  </div>
                  <div className="col-md-6">
                    <div className="form-group">
                      <label>Holding second: </label>
                      <input defaultValue={form.holdingTime} step="0.1" type="number" className="form-control" name="holdingTime" onChange={this.handleOnChangeInput} />
                    </div>
                  </div>
                  <div className="col-md-6">
                    <div className="form-group">
                      <label>Speed: </label>
                      <input defaultValue={form.speedMs} step="0.1" type="number" className="form-control" name="speedMs" onChange={this.handleOnChangeInput} />
                    </div>
                  </div>
                </>
              ) : (
                "Loading ..."
              )}
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-default"
                data-dismiss="modal"
                onClick={handleCloseModal}
              >
                Close
              </button>
              <button onClick={this.handleAssignBtnClick} type="button" className="btn btn-secondary">
              Assign
            </button>
              <button onClick={this.handleAssignAndStartBtnClick} type="button" className="btn btn-primary">
              Assign & Start
            </button>
            </div>
          </div>
        </div>
      </Modal>
    );
  }
}

export default (props) => <ModalAssign {...props} navigate={useNavigate()}/>;