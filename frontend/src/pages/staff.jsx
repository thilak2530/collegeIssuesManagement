import React, { useState ,useEffect, useCallback} from "react";
import "../css/staffpage.css";
import Update from "./update";
import { XCircle } from "lucide-react";
import Box from "../components/box";
import axios from "axios";



const DashboardPage = () => {
  
  const token = localStorage.getItem("token");
  const BASE_URL = process.env.REACT_APP_BACKEND_URL;
  const [issues,setIssues]=useState([]);
  const [boxs,setBoxs]=useState([]);
  const refIds=["resolved","pending"];
  const [selectedStaff,setSelectedStaff]=useState("");
  const [updatetrue,setupdatetrue]=useState(false);

 const handleAssign = () => {
      if (!selectedStaff) {
        alert("Please select status");
        return;
      }
     
      axios.patch(
        `${BASE_URL}/status/update`,
         {
          id: formData.userid,
          status: selectedStaff
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        }
      )
      .then(() => {
        alert("status changed successfully");
        setupdatetrue(false)
        refresh();
         
      })
      .catch(err => {
        console.error(err);
        alert("status changed failed");
      });
    };

    const [formData, setFormData] = useState({
      id: "",
      title: "",
      status: "",
      roomno: "",
      userid:""
    
    });

    const refresh=useCallback(()=>{
        axios.get(`${BASE_URL}/staffrecords`,{
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          }
          
        })
        .then((res) => {setIssues(res.data)})
        .catch(console.error);

        axios.get(`${BASE_URL}/staffdetailboxs`,
        {
         
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        })
        .then((res) => {setBoxs(res.data);})
        .catch(console.error);

    },[BASE_URL,token,])

  useEffect(()=>{

    refresh();

   

  },[refresh]);




    


    
    
  return (
     <>
        <div className={updatetrue ? "page blur" :"dashboard-page"}>
        <div className="dashboard-card">

            {/* Header */}
            <div className="dashboard-header">

            <h2 className="dashboard-title">Welcome</h2>


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
                    number={boxs.totalissues}              
                />
                 <Box 
                    class="stat-card stat-pending"
                    class1="stat-label"
                    class2="stat-value"
                    script="Pending Issues"
                    number={boxs.totalpending}               
                />
                <Box 
                    class="stat-card stat-resolved"
                    class1="stat-label"
                    class2="stat-value"
                    script="Resolved Issues"
                    number={boxs.totalresolved}              
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
                                userid:issue.id,
                                id: issue.refId,
                                title: issue.title,
                                status: issue.status,
                                roomno: issue.roomno,
                            });
                         }

                        }
                        
                    >
                        <td>{issue.refId}</td>
                        <td>{issue.title}</td>
                        <td>
                        <span className={`status ${issue.status}`}>
                            {issue.status}
                        </span>
                        </td>
                        <td>{issue.roomno}</td>
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
                
                refId={formData.id}
                refIds={refIds}
                title={formData.title}
                statuss={formData.status}
                roomno={formData.roomno}
                text="status"
                setSelectedStaff={setSelectedStaff}
                selectedStaff={selectedStaff}
                onrequest={()=>{handleAssign()}}



                
            />
            <XCircle strokeWidth={1} color="black" className="crossicon" size={16} onClick={() => setupdatetrue(false)}/>
          
        </div>
      )}
    </>
  );
};

export default DashboardPage;
