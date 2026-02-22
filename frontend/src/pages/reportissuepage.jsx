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

  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);

  const submitting = async(e) => {
    e.preventDefault();
    const token=localStorage.getItem("token");
    const BASE_URL = process.env.REACT_APP_BACKEND_URL;

    try{

      const data=new FormData();
      data.append(
        "issue",
        new Blob([JSON.stringify(formData)], {
          type: "application/json",
        })
      );

      if (image) {
        data.append("img", image);
      }

    await axios.post(`${BASE_URL}/raiseIssue`, data, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
    
      // ✅ clear form
      setFormData({
        title: "",
        category: "",
        location: "",
        description: "",
      });
      setImage(null);
      setPreview(null);
      alert("issue raised")
      // ✅ navigate
      navigate("/raise_issue");

    
    }catch(error) {
      alert("Failed to raise issue");
      console.error(error);
    };
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

          <input
            type="file"
            id="imageInput"
            accept="image/*"
            capture="environment"
            hidden
            onChange={(e) => {
              const file = e.target.files[0];
              if (file) {
                setImage(file);
                setPreview(URL.createObjectURL(file));
              }
            }}
          />

          {preview && (
            <div className="image-preview">
              <img src={preview} alt="Preview" />
            </div>
          )}

          <div className="form-buttons">
            <button
              type="button"
              className="attach-button"
              onClick={() => document.getElementById("imageInput").click()}
            >
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