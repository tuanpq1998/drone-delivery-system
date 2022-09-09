import React, { Component } from "react";
import { ReactBingmaps } from "react-bingmaps";
import { Link, useNavigate, useParams } from "react-router-dom";
import { getLatLngCenter } from "../../api/general";
import { getOneMission, updateMission } from "../../api/mission";
import Heading from "../components/Heading";

class PackgeEdit extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: -1,
      editting: null,
      changeSender: false,
      changeReceiver: false,
      _senderLocation : [],
      _receiverLocation : [],
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleDragSenderPushpin = this.handleDragSenderPushpin.bind(this);
    this.handleDragReceiverPushpin = this.handleDragReceiverPushpin.bind(this);
    this.handleClearClick = this.handleClearClick.bind(this);
    // this.handleClickOnMap = this.handleClickOnMap.bind(this);
  }

  handleChange(event) {
    const { editting } = this.state;
    this.setState({
      editting: {
        ...editting,
        [event.target.name]: event.target.value,
      },
    });
  }

  handleSubmit(event) {
    const {editting, id} = this.state;
    event.preventDefault();
    updateMission(id, editting).then(data => {
      if (data.status == 200) 
      this.props.navigate("../packages")
    })

  }

  componentDidMount() {
    const { id } = this.props.params;
    getOneMission(id).then((data) => {
      this.setState({
        id, 
        editting: data.data,
        _senderLocation : [data.data.senderLocationLatitude, data.data.senderLocationLongitude],
      _receiverLocation : [data.data.receiverLocationLatitude, data.data.receiverLocationLongitude],
      });
    });
  }

  generateCenter() {
    const { editting } = this.state;
    return getLatLngCenter([
      [editting.senderLocationLatitude, editting.senderLocationLongitude],
      [editting.receiverLocationLatitude, editting.receiverLocationLongitude],
    ]);
  }

  generatePolylines() {
    const { _senderLocation , _receiverLocation } = this.state;
    return [
      {
        location: [
          _senderLocation,
          _receiverLocation,
        ],
        option: { strokeColor: "red", strokeThickness: 2 },
      },
    ];
  }

  handleClickBtnChange(field, done) {
    const {editting, _senderLocation, _receiverLocation} = this.state;
    switch (field) {
      case "sender":
        if (done) {
          this.setState({
            changeSender: !done,
            changeReceiver: false,
            editting : {
              ...editting,
              senderLocationLatitude : parseFloat(_senderLocation[0].toFixed(5)),
              senderLocationLongitude : parseFloat(_senderLocation[1].toFixed(5)),
            }
          })
        }
        else this.setState({
          changeSender: !done,
          changeReceiver: false,
          _senderLocation : [editting.senderLocationLatitude, editting.senderLocationLongitude],
          _receiverLocation : [editting.receiverLocationLatitude, editting.receiverLocationLongitude],
        })
        break;

      case "receiver":
        if (done) {
          this.setState({
            changeSender: !done,
            changeReceiver: false,
            editting : {
              ...editting,
              receiverLocationLatitude : parseFloat(_receiverLocation[0].toFixed(5)),
              receiverLocationLongitude : parseFloat(_receiverLocation[1].toFixed(5)),
            }
          })
        }
        else this.setState({
          changeSender: false,
          changeReceiver: !done,
          _senderLocation : [editting.senderLocationLatitude, editting.senderLocationLongitude],
          _receiverLocation : [editting.receiverLocationLatitude, editting.receiverLocationLongitude],
        })
        break;
    }
  }

  generatePushpins() {
    const { _senderLocation , _receiverLocation, changeReceiver, changeSender } = this.state;
    const results = [];
    results.push(
      {
        location: _senderLocation,
        option: {
          title: changeSender ? `Sender \n[${_senderLocation.map(g => g.toFixed(5))}]` : "Sender",
          color: changeSender ? "green" : "blue",
          draggable: changeSender
        },
        addHandler : {
          type : "drag",
          callback: this.handleDragSenderPushpin,
        }
      },
      {
        location: _receiverLocation,
        option: {
          title: changeReceiver ? `Receiver \n[${_receiverLocation.map(g => g.toFixed(5))}]` : "Receiver",
          color: changeReceiver ? "green" : "blue",
          draggable: changeReceiver
        },
        addHandler : {
          type : "drag",
          callback: this.handleDragReceiverPushpin,
        }
      }
    );
    return results;
  }

  handleDragSenderPushpin(location) {
    const newLocation = [location.latitude, location.longitude];
    this.setState({
      _senderLocation : newLocation,
    })
  }

  handleDragReceiverPushpin(location) {
    const newLocation = [location.latitude, location.longitude];
    this.setState({
      _receiverLocation : newLocation,
    })
  }

  handleClearClick() {
    const {editting} = this.state;
    this.setState({
      changeReceiver : false,
      changeSender : false,
      _senderLocation : [editting.senderLocationLatitude, editting.senderLocationLongitude],
      _receiverLocation : [editting.receiverLocationLatitude, editting.receiverLocationLongitude],
    })
  }
 
  // handleClickOnMap(location) {
  //   const {changeSender, changeReceiver, editting } = this.state;
  //   if (changeSender) {
  //     this.setState({
  //       editting : {
  //         ...editting,
  //         senderLocationLatitude : location.latitude,
  //         senderLocationLongitude : location.longitude,
  //       }
  //     })
  //   } else if (changeReceiver) {
  //     this.setState({
  //       editting : {
  //         ...editting,
  //         receiverLocationLatitude : location.latitude,
  //         receiverLocationLongitude : location.longitude,
  //       }
  //     })
  //   }
  // }

  render() {
    const { editting, changeSender, changeReceiver } = this.state;
    const pushpins = editting ? this.generatePushpins() : [];
    const polylines = editting ? this.generatePolylines() : [];
    const center = editting ? this.generateCenter() : [13.0827, 80.2707];

    const buttons = {
      sender: (changeSender ? 
        <>
        <button
          type="button" 
          className="input-group-append btn btn-secondary"
        >
          Changing ...
        </button>
        <button
          type="button"
          onClick={() => this.handleClickBtnChange("sender", 1)}
          className="input-group-append btn btn-info"
        >
          Done
        </button>
        </>
         : 
        <button
          type="button"
          onClick={() => this.handleClickBtnChange("sender", 0)}
          className="input-group-append btn btn-warning"
        >
          Change Address
        </button>
      ),
      receiver: (changeReceiver ? 
        <>
        <button
          type="button" 
          className="input-group-append btn btn-secondary"
        >
          Changing ...
        </button>
        <button
          type="button"
          onClick={() => this.handleClickBtnChange("receiver", 1)}
          className="input-group-append btn btn-info"
        >
          Done
        </button>
        </>
         : 
        <button
          type="button"
          onClick={() => this.handleClickBtnChange("receiver", 0)}
          className="input-group-append btn btn-warning"
        >
          Change Address
        </button>
      ),
    };
    return editting ? (
      <>
        <Heading title="Edit package" />
        <div className="content">
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-12">
                <div className="card card-info">
                  <div className="card-header">
                    <h3 className="card-title">
                      Mission #${editting.missionIdentifier}
                    </h3>
                  </div>

                  <form onSubmit={this.handleSubmit}>
                    <div className="card-body row">
                      <div className="col-md-6">
                        <div className="form-group">
                          <label htmlFor="packageName">Name</label>
                          <input
                            readOnly
                            type="text"
                            className="form-control"
                            id="packageName"
                            value={editting.packageName}
                          />
                        </div>
                        <div className="row">
                          <div className="form-group col-md-4">
                            <label htmlFor="size">Size</label>
                            <input
                              
                              type="text"
                              className="form-control"
                              id="size"
                              value={editting.size}
                              name="size"
                              onChange={this.handleChange}
                            />
                          </div>
                          <div className="form-group  col-md-4">
                            <label htmlFor="size">Price</label>
                            <input
                              
                              type="text"
                              className="form-control"
                              id="price"
                              name="price"
                              value={editting.price}
                              onChange={this.handleChange}
                            />
                          </div>
                          <div className="form-group  col-md-4">
                            <label htmlFor="weight">Weight</label>
                            <input
                            
                              type="text"
                              className="form-control"
                              id="weight"
                              value={editting.weight}
                              name="weight"
                              onChange={this.handleChange}
                            />
                          </div>
                        </div>

                        <div className="form-group">
                          <label htmlFor="status">Status</label>
                          <input
                            
                            type="text"
                            className="form-control"
                            id="status"
                            value={editting.status}
                            name="status"
                            onChange={this.handleChange}
                          />
                        </div>

                        <div className="form-group">
                          <label htmlFor="status">Sender</label>
                          <div className="input-group">
                            <input
                              readOnly
                              type="text"
                              className="form-control"
                              id="sender"
                              defaultValue={`${editting.seller.fullname}(${editting.seller.username}) - ${editting.seller.telephone}`}
                            />
                            {buttons.sender}
                          </div>
                        </div>
                        <div className="form-group">
                          <textarea
                            style={{ resize: "none" }}
                            className="form-control"
                            rows={3}
                            defaultValue={`Address: ${editting.senderAddress}\nCoordinates: (${editting.senderLocationLatitude}, ${editting.senderLocationLongitude})`}
                            readOnly
                          />
                        </div>
                        <div className="form-group">
                          <label htmlFor="status">Receiver</label>
                          <div className="input-group">
                            <input
                              readOnly
                              type="text"
                              className="form-control"
                              id="receiver"
                              defaultValue={`${editting.receiverName} - ${editting.receiverTel}`}
                            />
                            {buttons.receiver}
                          </div>
                        </div>
                        <div className="form-group">
                          <textarea
                            style={{ resize: "none" }}
                            className="form-control"
                            rows={3}
                            defaultValue={`Address: ${editting.receiverAddress}\nCoordinates: (${editting.receiverLocationLatitude}, ${editting.receiverLocationLongitude})`}
                            readOnly
                          />
                        </div>

                        <div className="form-group">
                          <label>Note</label>
                          <textarea
                            name="note"
                            onChange={this.handleChange}
                            style={{ resize: "none" }}
                            className="form-control"
                            rows={6}
                            value={editting.note}
                          />
                        </div>
                      </div>

                      <div className="col-md-6">
                        <ReactBingmaps
                          // getLocation = {
                          //   {addHandler: "mousedown", callback:this.handleClickOnMap} 
                          // }
                          className="bing-map-edit"
                          bingmapKey={process.env.REACT_APP_BINGMAP_API_KEY}
                          pushPins={pushpins}
                          polylines={polylines}
                          center={center}
                          zoom={15}
                        />
                        <small className="form-text text-muted">
                          Change location by clicking buttuon, draging green point and click Done to finish.
                        </small>
                        {
                          changeReceiver || changeSender ? <button className="btn btn-secondary" onClick={this.handleClearClick} type="button">Clear</button> : ""
                        }
                      </div>
                    </div>
                    <div className="card-footer">
                      <button type="submit" className="btn btn-primary">
                        Submit
                      </button>
                      <Link className="ml-1 btn btn-link" to="../packages/">Back</Link>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    ) : (
      <>Notfound</>
    );
  }
}

export default (props) => <PackgeEdit {...props} params={useParams()} navigate={useNavigate()}/>;
