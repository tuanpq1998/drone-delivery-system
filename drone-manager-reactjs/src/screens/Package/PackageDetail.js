import React, { Component } from "react";
import { ReactBingmaps } from "react-bingmaps";
import { Link, useParams } from "react-router-dom";
import { calculateDistance, getLatLngCenter, getHomeLocation} from "../../api/general";
import { getOneMission } from "../../api/mission";
import Heading from "../components/Heading";

class PackageDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      detail: null,
      homeLocation: null,
    };
  }

  componentDidMount() {
    const { id } = this.props.params;
    getOneMission(id).then((data) => {
      getHomeLocation().then((homeLocation) => {
        this.setState({
          detail: data.data,
          homeLocation: homeLocation.data,
        });
      });
    });
  }
  generatePushpins() {
    const { detail, homeLocation } = this.state;
    const results = [];
    results.push(
      {
        location: [homeLocation.latitude, homeLocation.longitude],
        option: {
          title: "Home",
          color: "red",
        },
      },
      {
        location: [
          detail.senderLocationLatitude,
          detail.senderLocationLongitude,
        ],
        option: {
          title: "Sender",
          color: "blue",
        },
      },
      {
        location: [
          detail.receiverLocationLatitude,
          detail.receiverLocationLongitude,
        ],
        option: {
          title: "Receiver",
          color: "blue",
        },
      }
    );
    return results;
  }
  generateShipper(mission) {
    if (mission.drone == null) return "none";
    else return `Drone #${mission.drone.id} - ${mission.drone.droneName}`;
  }
  generatePolylines() {
    const { detail, homeLocation } = this.state;
    return [
      {
        location: [
          [homeLocation.latitude, homeLocation.longitude],
          [detail.senderLocationLatitude, detail.senderLocationLongitude],
        ],
        option: { strokeColor: "red", strokeThickness: 2 },
      },
      {
        location: [
          [detail.receiverLocationLatitude, detail.receiverLocationLongitude],
          [detail.senderLocationLatitude, detail.senderLocationLongitude],
        ],
        option: { strokeColor: "red", strokeThickness: 2 },
      },
      {
        location: [
          [detail.receiverLocationLatitude, detail.receiverLocationLongitude],
          [homeLocation.latitude, homeLocation.longitude],
        ],
        option: { strokeColor: "green", strokeThickness: 2 },
      },
    ];
  }

  generateCenterPoint() {
    const { detail, homeLocation } = this.state;
    return getLatLngCenter([
      [homeLocation.latitude, homeLocation.longitude],
      [detail.receiverLocationLatitude, detail.receiverLocationLongitude],
      [detail.senderLocationLatitude, detail.receiverLocationLongitude],
    ]);
  }

  render() {
    const { detail, homeLocation } = this.state;
    const pushpins = detail ? this.generatePushpins() : null;
    const polylines = detail ? this.generatePolylines() : null;
    const center = detail ? this.generateCenterPoint() : [13.0827, 80.2707];
    const distanceSR = detail
      ? calculateDistance([
          [detail.senderLocationLatitude, detail.senderLocationLongitude],
          [detail.receiverLocationLatitude, detail.receiverLocationLongitude],
        ])
      : null;
    const distance = detail
      ? calculateDistance([
          [homeLocation.latitude, homeLocation.longitude],
          [detail.senderLocationLatitude, detail.senderLocationLongitude],
          [detail.receiverLocationLatitude, detail.receiverLocationLongitude],
          [homeLocation.latitude, homeLocation.longitude],
        ])
      : null;

    const statusStr = detail
      ? (detail.finished
        ? "finished"
        : (detail.started
        ? "started"
        : (detail.drone != null
        ? "assigned"
        : "unassign")))
      : null;

    return detail ? (
      <>
        <Heading title="Package Detail" />
        <div className="content">
          <div className="container-fluid">
            <div className="row">
              <div className="col-md-12">
                <div className="card">
                  <div className="card-header">
                    <h3 className="card-title">
                      Mission <mark>#{detail.missionIdentifier}</mark>{" "}
                      <span className="badge badge-pill badge-info">
                        {statusStr}
                      </span>
                    </h3>
                  </div>
                  <div className="card-body row">
                    <dl className="row">
                      <dt className="col-sm-2">Package name: </dt>
                      <dd className="col-sm-5">{detail.packageName}</dd>
                      <dt className="col-sm-2"> Size - Weight: </dt>
                      <dd className="col-sm-3">
                        {detail.size} - {detail.weight}
                      </dd>
                      <dt className="col-sm-2">Price: </dt>
                      <dd className="col-sm-5">{detail.price}</dd>
                      <dt className="col-sm-2">Status: </dt>
                      <dd className="col-sm-3">
                        <code>{detail.status}</code>
                      </dd>

                      <dt className="col-sm-2">Create at: </dt>
                      <dd className="col-sm-5">{detail.createdAt}</dd>
                      <dt className="col-sm-2">Update at: </dt>
                      <dd className="col-sm-3">{detail.updatedAt}</dd>
                      <dt className="col-sm-2">Shipper: </dt>
                      <dd className="col-sm-5">
                        {this.generateShipper(detail)}
                      </dd>
                      <dt className="offset-md-5"> </dt>

                      <dt className="col-sm-2">Sender: </dt>
                      <dd className="col-sm-5">
                        {detail.seller.fullname} ({detail.seller.username})
                      </dd>
                      <dt className="col-sm-2">Receiver: </dt>
                      <dd className="col-sm-3">{detail.receiverName}</dd>
                      <dd className="col-sm-5 offset-sm-2">
                        {detail.seller.telephone}
                      </dd>
                      <dd className="col-sm-3 offset-sm-2">
                        {detail.receiverTel}
                      </dd>
                      <dd className="col-sm-5 offset-sm-2">
                        {detail.senderAddress}
                      </dd>
                      <dd className="col-sm-3 offset-sm-2">
                        {detail.receiverAddress}
                      </dd>
                      <dt className="col-sm-2">Distance S-R:</dt>
                      <dd className="col-sm-5">
                        {Number(distanceSR.toFixed(2))} km
                      </dd>
                      <dt className="col-sm-2">Distance (all):</dt>
                      <dd className="col-sm-3">
                        {Number(distance.toFixed(2))} km
                      </dd>
                      <dt className="col-sm-2">Send to R:</dt>
                      <dd className="col-sm-4">
                      <input type="text" readOnly className="form-control" value={`${window.location.host}/tracking/${detail.missionIdentifier}`} />
                      </dd>
                      <div className="offset-md-1"></div>
                      <dt className="col-sm-2">Password: </dt>
                      <dd className="col-sm-2">
                        <input type="text" readOnly className="form-control" value={detail.rawpassword} />
                      </dd>
                      
                      {statusStr == "finished" ? (
                        <>
                          <dt className="col-sm-2">Logs: </dt>
                          <dd className="col-sm-5">
                            <textarea
                              className="form-control col-md-10"
                              style={{ resize: "none" }}
                              rows={3}
                              readOnly
                              defaultValue= {detail.logs.split("|").filter(l => l.trim() !== "").reverse().join("\n")}
                            >
                             
                            </textarea>
                          </dd>
                        </>
                      ) : null}

                      <dt className="col-sm-2">Note: </dt>
                      <dd className="col-sm-3">
                        <textarea
                          className="form-control col-md-10"
                          style={{ resize: "none" }}
                          rows={3}
                          readOnly
                          defaultValue= {detail.note}
                        >
                          
                        </textarea>
                      </dd>
                    </dl>
                    <ReactBingmaps
                      className="bing-map-mini"
                      bingmapKey={process.env.REACT_APP_BINGMAP_API_KEY}
                      pushPins={pushpins}
                      polylines={polylines}
                      center={center}
                      zoom={15}
                    />
                  </div>
                  <div className="card-footer">
                    {statusStr == "finished" || statusStr == "started" ? null : <Link
                      to={"../packages/edit/" + detail.id}
                      className="btn btn-primary"
                    >
                      Edit
                    </Link>}
                    <Link className="ml-1 btn btn-link" to="../packages/">
                      Back
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    ) : (
      <></>
    );
  }
}

export default (props) => <PackageDetail {...props} params={useParams()} />;
