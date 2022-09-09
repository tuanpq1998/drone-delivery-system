import React, { Component } from "react";
import { ReactBingmaps } from "react-bingmaps";
import { Link, useNavigate } from "react-router-dom";
import { getLocationByAddress } from "../../api/bingmap";
import { getLatLngCenter } from "../../api/general";
import { createMission } from "../../api/mission";
import Heading from "../components/Heading";

class PackageAdd extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: -1,
      creating: {
        packageName: "",
        size: "",
        price: "",
        weight: "",
        receiverName: "",
        receiverTel: "",
        receiverAddress: "",
        receiverLocationLatitude: 0,
        receiverLocationAltitude: 50,
        receiverLocationLongitude: 0,
        note: "",
      },
      _senderLocation: [],
      _senderAddress: "",
      _receiverLocation: [],
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleDragSenderPushpin = this.handleDragSenderPushpin.bind(this);
    this.handleDragReceiverPushpin = this.handleDragReceiverPushpin.bind(this);
    this.handleClearClick = this.handleClearClick.bind(this);
    this.handleShowOnMap = this.handleShowOnMap.bind(this);
    // this.handleClickOnMap = this.handleClickOnMap.bind(this);
  }

  componentDidMount() {
    const {user} = this.props;
    getLocationByAddress(user.address).then(data => {
      if (
        data &&
        data.data &&
        data.data.resourceSets &&
        data.data.resourceSets[0].resources[0].point.coordinates
      ){
        this.setState({
          _senderLocation: data.data.resourceSets[0].resources[0].point.coordinates,
          _senderAddress: user.address
        })
      }
    })
  }

  handleChange(event) {
    const { creating } = this.state;
    this.setState({
      creating: {
        ...creating,
        [event.target.name]: event.target.value,
      },
    });
  }

  handleSubmit(event) {
    const { creating, id, _senderLocation } = this.state;
    event.preventDefault();
    createMission(creating, _senderLocation).then(data => {
      if (data.status == 200) 
        this.props.navigate("../packages/detail/"+data.data.id)
    })
  }

  generateCenter() {
    const { _senderLocation, _receiverLocation } = this.state;
    return _receiverLocation == 0 ? [_senderLocation] : getLatLngCenter([
      [..._receiverLocation],
      [..._senderLocation],
    ]);
  }

  generatePolylines() {
    const { _senderLocation, _receiverLocation } = this.state;
    return [
      {
        location: [_senderLocation, _receiverLocation],
        option: { strokeColor: "red", strokeThickness: 2 },
      },
    ];
  }

  generatePushpins() {
    const {
      _receiverLocation,
      changeReceiver,
      _senderLocation,
      creating,
    } = this.state;
    const results = [];
    results.push(
      {
        location: _senderLocation,
        option: {
          title: "Sender",
          color: "blue",
          draggable: false,
        },
      },
      {
        location: [
          Number(creating.receiverLocationLatitude),
          Number(creating.receiverLocationLongitude),
        ],
        option: {
          title: changeReceiver
            ? `Receiver \n[${_receiverLocation.map((g) => g.toFixed(5))}]`
            : "Receiver",
          color: "green",
          draggable: true,
        },
        addHandler: {
          type: "drag",
          callback: this.handleDragReceiverPushpin,
        },
      }
    );
    return results;
  }

  handleDragSenderPushpin(location) {
    const newLocation = [location.latitude, location.longitude];
    this.setState({
      _senderLocation: newLocation,
    });
  }

  handleDragReceiverPushpin(location) {
    const newLocation = [location.latitude, location.longitude];
    const { creating } = this.state;
    this.setState({
      creating: {
        ...creating,
        receiverLocationLatitude: newLocation[0],
        receiverLocationLongitude: newLocation[1],
      },
    });
  }

  handleClearClick() {
    const { creating } = this.state;
    this.setState({
      changeReceiver: false,
      changeSender: false,
      _senderLocation: [
        creating.senderLocationLatitude,
        creating.senderLocationLongitude,
      ],
      _receiverLocation: [
        creating.receiverLocationLatitude,
        creating.receiverLocationLongitude,
      ],
    });
  }

  handleShowOnMap() {
    const { creating } = this.state;
    if (creating.receiverAddress != "") {
      getLocationByAddress(creating.receiverAddress).then((data) => {
        // console.log(data.data.resourceSets[0].resources[0]);
        if (
          data &&
          data.data &&
          data.data.resourceSets &&
          data.data.resourceSets[0].resources[0].point.coordinates
        )
          this.setState({
            _receiverLocation:
              data.data.resourceSets[0].resources[0].point.coordinates,
            creating: {
              ...creating,
              receiverAddress:
                data.data.resourceSets[0].resources[0].address.formattedAddress.replace(
                  `, ${data.data.resourceSets[0].resources[0].address.postalCode}`,
                  ""
                ),
              receiverLocationLatitude:
                data.data.resourceSets[0].resources[0].point.coordinates[0],
              receiverLocationLongitude:
                data.data.resourceSets[0].resources[0].point.coordinates[1],
            },
          });
      });
    }
  }

  render() {
    const { creating, changeSender, changeReceiver, _senderAddress, _senderLocation } = this.state;
    const pushpins = creating ? this.generatePushpins() : [];
    const polylines = creating ? this.generatePolylines() : [];
    const center = creating ? this.generateCenter() : [13.0827, 80.2707];

    return creating ? (
      <>
        <Heading title="Add package" />
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
                      <div className="col-md-6">
                        <div className="form-group">
                          <label htmlFor="packageName">Name</label>
                          <input
                            onChange={this.handleChange}
                            type="text"
                            className="form-control"
                            id="packageName"
                            name="packageName"
                            value={creating.packageName}
                          />
                        </div>
                        <div className="row">
                          <div className="form-group col-md-4">
                            <label htmlFor="size">Size</label>
                            <input
                              type="text"
                              className="form-control"
                              id="size"
                              value={creating.size}
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
                              value={creating.price}
                              name="price"
                              onChange={this.handleChange}
                            />
                          </div>
                          <div className="form-group  col-md-4">
                            <label htmlFor="weight">Weight</label>
                            <input
                              type="text"
                              className="form-control"
                              id="weight"
                              value={creating.weight}
                              name="weight"
                              onChange={this.handleChange}
                            />
                          </div>

                          <div className="form-group col-md-12">
                            <label htmlFor="status">Sender</label>
                            <div className="input-group">
                              <textarea
                                readOnly
                                type="text"
                                className="form-control"
                                id="sender"
                                defaultValue={`${_senderAddress}`}
                              />
                            </div>
                          </div>
                          <div className="form-group col-md-6">
                            <label htmlFor="size">Latitude</label>
                            <input
                            readOnly
                              type="text"
                              className="form-control"
                              defaultValue={_senderLocation[0]}
                            />
                          </div>
                          <div className="form-group  col-md-6">
                            <label htmlFor="size">Longitude</label>
                            <input
                            readOnly
                              type="text"
                              className="form-control"
                              defaultValue={_senderLocation[1]}
                            />
                          </div>

                          <label className="col-md-12">Receiver</label>

                          <div className="form-group col-md-6">
                            <label>Name: </label>
                            <div className="input-group">
                              <input
                                type="text"
                                className="form-control"
                                id="receiverName"
                                value={creating.receiverName}
                                name="receiverName"
                                onChange={this.handleChange}
                              />
                            </div>
                          </div>
                          <div className="form-group col-md-6">
                            <label>Telephone: </label>
                            <div className="input-group">
                              <input
                                type="text"
                                className="form-control"
                                id="receiverTel"
                                value={creating.receiverTel}
                                name="receiverTel"
                                onChange={this.handleChange}
                              />
                            </div>
                          </div>

                          <div className="form-group col-md-12">
                            <label>Address: </label>
                            <div className="input-group">
                              <input
                                type="text"
                                className="form-control"
                                id="receiverAddress"
                                value={creating.receiverAddress}
                                name="receiverAddress"
                                onChange={this.handleChange}
                              />
                              <button
                                type="button"
                                className="input-group-append btn btn-info"
                                onClick={this.handleShowOnMap}
                              >
                                Show on map
                              </button>
                            </div>
                          </div>

                          <div className="form-group col-md-4">
                            <label htmlFor="size">Latitude</label>
                            <input
                              type="text"
                              className="form-control"
                              id="receiverLocationLatitude"
                              value={creating.receiverLocationLatitude}
                              name="receiverLocationLatitude"
                              onChange={this.handleChange}
                            />
                          </div>
                          <div className="form-group  col-md-4">
                            <label htmlFor="size">Longitude</label>
                            <input
                              type="text"
                              className="form-control"
                              id="receiverLocationLongitude"
                              value={creating.receiverLocationLongitude}
                              name="receiverLocationLongitude"
                              onChange={this.handleChange}
                            />
                          </div>
                          <div className="form-group  col-md-4">
                            <label htmlFor="weight">Altitude</label>
                            <div className="input-group">
                              <input
                                type="text"
                                className="form-control"
                                id="receiverLocationAltitude"
                                value={creating.receiverLocationAltitude}
                                name="receiverLocationAltitude"
                                onChange={this.handleChange}
                              />
                            </div>
                          </div>
                        </div>
                        <div className="form-group">
                          <label>Note</label>
                          <textarea
                            name="note"
                            onChange={this.handleChange}
                            style={{ resize: "none" }}
                            className="form-control"
                            rows={6}
                            value={creating.note}
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
                          // polylines={polylines}
                          center={center}
                          zoom={15}
                        />
                      </div>
                    </div>
                    <div className="card-footer">
                      <button type="submit" className="btn btn-primary">
                        Submit
                      </button>
                      <Link className="ml-1 btn btn-link" to="../packages/">
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
    ) : (
      <>Notfound</>
    );
  }
}

export default (props) => <PackageAdd {...props} navigate={useNavigate()}/>;
