import React, { Component } from "react";
import { getAllMissions } from "../../api/mission";
import Heading from "../components/Heading";
import ModalAssign from "./ModalAssign";
import PackageTable from "./PackageTable";

export default class PackageView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      missions: [],
      assignId: null,
      sortMode: "unassign",
    };
    this.handleClickAssign = this.handleClickAssign.bind(this);
    this.handleCloseAssignModal = this.handleCloseAssignModal.bind(this);
  }

  componentDidMount() {
    getAllMissions().then((data) => {
      this.setState({
        missions: data.data,
      });
    });
  }

  handleClickAssign(id) {
    this.setState({
      assignId: id,
    });
  }

  handleCloseAssignModal() {
    this.setState({
      assignId: null,
    });
  }

  swtchSortMode(sortModeName) {
    this.setState({
      sortMode: ["unassign", "all", "finished", "started"].includes(
        sortModeName
      )
        ? sortModeName
        : "unassign",
    });
  }

  gernerateShowingMission() {
    const { missions, sortMode } = this.state;
    switch (sortMode) {
      case "all":
        return missions;
      case "finished":
        return missions.filter(m => m.finished)
      case "started":
        return missions.filter(m => m.started && !m.finished)
      case "unassign":
        return missions.filter(m => m.drone == null)
      default:
        return missions.filter(m => m.drone == null)
    }
  }

  generateBtnClassName(btnName) {
    const {sortMode } = this.state;
    if (sortMode == btnName) 
      return "btn btn-success mr-2";
    else return "btn btn-secondary mr-2";
  }

  render() {
    const { assignId, sortMode } = this.state;
    const sortedMission = this.gernerateShowingMission();
    const {user}  = this.props;
    return (
      <>
        {assignId != null && (
          <ModalAssign
            assignMissionId={assignId}
            handleCloseModal={this.handleCloseAssignModal}
          />
        )}
        <Heading title={`Packges - ${sortMode}` } />
        <div className="content">
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-12">
                <div className="card">
                  <div className="card-body">
                    <div className="input-group ">
                      <input
                        type="search"
                        className="form-control"
                        placeholder="Type here"
                      />
                      <div className="input-group-append">
                        <button
                          style={{ zIndex: 0 }}
                          type="submit"
                          className="btn btn-default"
                        >
                          <i className="fa fa-search" />
                        </button>
                      </div>
                    </div>

                    <div className="mt-3">
                      <button
                        onClick={() => this.swtchSortMode("all")}
                        className={this.generateBtnClassName("all")}
                      >
                        All
                      </button>
                      <button
                        onClick={() => this.swtchSortMode("finished")}
                        className={this.generateBtnClassName("finished")}
                      >
                        Finished
                      </button>
                      <button
                        onClick={() => this.swtchSortMode("started")}
                        className={this.generateBtnClassName("started")}
                      >
                        Started
                      </button>
                      <button
                        onClick={() => this.swtchSortMode("unassign")}
                        className={this.generateBtnClassName("unassign")}
                      >
                        Unassign
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-md-12">
                <PackageTable
                  handleClickAssign={this.handleClickAssign}
                  missions={sortedMission}
                  user={user}
                />
              </div>
            </div>
          </div>
        </div>
      </>
    );
  }
}
