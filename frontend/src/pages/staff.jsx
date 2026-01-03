import React, { useState } from "react";
import "../css/staffpage.css";
import Update from "./update";
import { XCircle } from "lucide-react";
import Box from "../components/box";


const DashboardPage = () => {


    
  const [formData, setFormData] = useState({
    id: "",
    title: "",
    status: "",
    roomno: "",
   
  });

    const [updatetrue,setupdatetrue]=useState(false);
    const issues = [
    {
      id: "3000003",
      title: "Fan not working",
      status: "resolved",
      room: "Room 204",
    },
    {
      id: "3000024",
      title: "Electrical issue",
      status: "pending",
      room: "Room 204",
    },
    {
      id: "3000435",
      title: "Ceiling issue",
      status: "resolved",
      room: "Room 209",
    },
    {
      id: "3000015",
      title: "Tubelight issue",
      status: "resolved",
      room: "Room 209",
    },
  ];

  return (
     <>
        <div className={updatetrue ? "page blur" :"dashboard-page"}>
        <div className="dashboard-card">

            {/* Header */}
            <div className="dashboard-header">
            <h2 className="dashboard-title">Welcome, Electrition</h2>
            <div className="dashboard-icons">
                <div className="icon-box">☰</div>
                <div className="icon-box">✉</div>
                <div className="profile-circle">J</div>
            </div>
            </div>

            {/* Content */}
            <div className="dashboard-content">

            {/* Stats */}
            <div className="stats-containerr">
                <Box 
                    class="stat-card stat-total"
                    class1="stat-label"
                    class2="stat-value"
                    script="Total Issues"
                    number="5"              
                />
                 <Box 
                    class="stat-card stat-pending"
                    class1="stat-label"
                    class2="stat-value"
                    script="Pending Issues"
                    number="2"              
                />
                <Box 
                    class="stat-card stat-resolved"
                    class1="stat-label"
                    class2="stat-value"
                    script="Resolved Issues"
                    number="3"              
                />

               
            </div>

            {/* Recent Issues */}
            <div className="issues-section">
                <h3 className="section-title">Recent Issues</h3>

                <table className="issues-table">
                <thead>
                    <tr>
                    <th>Issue ID</th>
                    <th>Title</th>
                    <th>Status</th>
                    <th>Room No</th>
                    </tr>
                </thead>
                 <tbody>
                    {issues.map((issue) => (
                    <tr
                        key={issue.id}
                        onClick={()=>{setupdatetrue(true);
                           setFormData({
                                id: issue.id,
                                title: issue.title,
                                status: issue.status,
                                roomno: issue.room,
                            });
                         }

                        }
                        
                    >
                        <td>{issue.id}</td>
                        <td>{issue.title}</td>
                        <td>
                        <span className={`status ${issue.status}`}>
                            {issue.status}
                        </span>
                        </td>
                        <td>{issue.room}</td>
                    </tr>
                    ))}
                </tbody>
                </table>

                
                

            </div>
            </div>

        </div>
        
        </div>
     

      {updatetrue && (
        <div className="modal-overlay" >
          
            <Update 
                
                id={formData.id}
                title={formData.title}
                statuss={formData.status}
                roomno={formData.roomno}

                
            />
            <XCircle strokeWidth={1} color="black" className="crossicon" size={16} onClick={() => setupdatetrue(false)}/>
          
        </div>
      )}
    </>
  );
};

export default DashboardPage;
