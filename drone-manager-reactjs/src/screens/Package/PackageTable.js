import React, { Component } from 'react'
import { Link } from 'react-router-dom';

export default class PackageTable extends Component {
  render() {
    const {missions, handleClickAssign, user} = this.props;
    return (
        <div className="card">
        <div className="card-header">
          <h3 className="card-title">List</h3>
        </div>
        <div className="card-body">
          <table className="table table-bordered">
            <thead>
              <tr>
                <th style={{ width: "10px" }}>#</th>
                <th>Id</th>
                <th>Name</th>
                <th>Size</th>
                <th>Weight</th>
                <th>Price</th>
                <th>Status</th>
                <th>Assign</th>
                <th>Create at</th>
                <th>Update at</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
                {missions.map(mission => (
                    <tr key={mission.id}>
                        <td>{mission.id}</td>
                        <td>{mission.missionIdentifier}</td>
                        <td>{mission.packageName}</td>
                        <td>{mission.size}</td>
                        <td>{mission.weight}</td>
                        <td>{mission.price}</td>
                        <td>{mission.status}</td>
                        <td>{mission.drone != null ? mission.drone.droneName : null}</td>
                        <td>{mission.createdAt}</td>
                        <td>{mission.updatedAt}</td>
                        <td>
                            <div className="btn-group-vertical">
                                <Link to={"detail/"+mission.id} className="btn btn-default">Detail</Link>
                                {
                                  mission.stared || mission.finished ? null : <Link to={"edit/"+mission.id} type="button" className="btn btn-info">Edit</Link>
                                }
                                {
                                  mission.drone==null ? <button onClick={() => handleClickAssign(mission.id)} type="button" className="btn btn-primary">Assign</button> : null
                                }
                            </div>
                        </td>
                    </tr>
                ))}
                
            </tbody>
          </table>
        </div>
        <div className="card-footer clearfix">
          {user != null && user.role=="SELLER" ? <Link to="add" className='btn btn-warning'>+ Add</Link>: null}
        </div>
      </div>
    )
  }
}
