import React, { useState } from "react";
import "../css/reportissuepage.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const ReportIssuePage = () => {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    title: "",
    category: "",
    location: "",
    description: "",
  });

  const submitting = (e) => {
    e.preventDefault();
    const token=localStorage.getItem("token");

    const BASE_URL = process.env.REACT_APP_BACKEND_URL;

    axios.post(`${BASE_URL}/raiseIssue`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
    }
  })
      .then((response) => {
        alert("Successfully issue raised");

        // ✅ clear form
        setFormData({
          title: "",
          category: "",
          location: "",
          description: "",
        });

        // ✅ navigate
        navigate("/raise_issue");
      })
      .catch((error) => {
        alert("Failed to raise issue");
        console.error(error);
      });
  };

  return (
    <div className="report-page">
      <div className="report-card">

        <div className="report-header">
          <h1>Report an Issue</h1>
        </div>

        <form className="report-form" onSubmit={submitting}>

          <div className="form-group">
            <label>Issue Title</label>
            <input
              type="text"
              value={formData.title}
              onChange={(e) =>
                setFormData({ ...formData, title: e.target.value })
              }
              placeholder="Fan not working"
            />
          </div>

          <div className="form-group">
            <label>Category</label>
            <select
              value={formData.category}
              onChange={(e) =>
                setFormData({ ...formData, category: e.target.value })
              }
            >
              <option value="">Select category</option>
              <option value="electrical">Electrical</option>
              <option value="plumbing">Plumbing</option>
              <option value="furniture">Furniture</option>
              <option value="hvac">HVAC</option>
              <option value="cleaning">Cleaning</option>
              <option value="other">Other</option>
            </select>
          </div>

          <div className="form-group">
            <label>Location</label>
            <select
              value={formData.location}
              onChange={(e) =>
                setFormData({ ...formData, location: e.target.value })
              }
            >
              <option value="">Select location</option>
              <option value="room-201">Room 201</option>
              <option value="room-202">Room 202</option>
              <option value="room-203">Room 203</option>
              <option value="room-204">Room 204</option>
              <option value="room-205">Room 205</option>
              <option value="common-area">Common Area</option>
              <option value="cafeteria">Cafeteria</option>
            </select>
          </div>

          <div className="form-group">
            <label>Description</label>
            <textarea
              value={formData.description}
              onChange={(e) =>
                setFormData({ ...formData, description: e.target.value })
              }
              placeholder="Fan not working properly."
            />
          </div>

          <div className="form-buttons">
            <button type="button" className="attach-button">
              Attach Image
            </button>
            <button type="submit" className="submit-button">
              Submit Issue
            </button>
          </div>

        </form>
      </div>
    </div>
  );
};

export default ReportIssuePage;