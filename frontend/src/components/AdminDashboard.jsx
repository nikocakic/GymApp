import React, { useState, useEffect } from "react";
import "../assets/styles/AdminDashboard.css";
import { Mail, CheckCircle, XCircle, Clock, AlertCircle } from "lucide-react";
import Cookies from "js-cookie";

const AdminDashboard = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchTrainerRequests();
  }, []);

  const fetchTrainerRequests = async () => {
    const token = Cookies.get("token");
    try {
      const response = await fetch("http://localhost:8080/admin/dashboard", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        const data = await response.json();
        setRequests(data);
      }
    } catch (error) {
      console.error("Error fetching trainer requests:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleRequestAction = async (requestId, action) => {
    const token = Cookies.get("token");
    try {
      const response = await fetch(
        `http://localhost:8080/admin/${action}/${requestId}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.ok) {
        fetchTrainerRequests();
      }
    } catch (error) {
      console.error(`Error ${action}ing request:`, error);
    }
  };

  return (
    <div className="admin-container">
      <header className="admin-header">
        <h1 className="admin-title">Trainer Registration Requests</h1>
        <p className="admin-subtitle">
          Review and manage trainer registration requests
        </p>
      </header>

      <div className="requests-grid">
        {loading ? (
          <div className="no-requests">
            <Clock size={48} />
            <h3>Loading requests...</h3>
          </div>
        ) : requests.length > 0 ? (
          requests.map((request) => (
            <div key={request.id} className="request-card">
              <div className="request-info">
                <div className="status-badge status-pending">
                  <Clock size={16} />
                  <span>Pending Review</span>
                </div>
                <h2 className="trainer-name">
                  {request.firstName} {request.lastName}
                </h2>
                <div className="trainer-email">
                  <Mail size={16} />
                  <span>{request.email}</span>
                </div>
                <div className="request-actions">
                  <button
                    className="action-button accept-button"
                    onClick={() => handleRequestAction(request.id, "accept")}
                  >
                    <CheckCircle size={18} />
                    Accept
                  </button>
                  <button
                    className="action-button decline-button"
                    onClick={() => handleRequestAction(request.id, "decline")}
                  >
                    <XCircle size={18} />
                    Decline
                  </button>
                </div>
              </div>
            </div>
          ))
        ) : (
          <div className="no-requests">
            <AlertCircle size={48} />
            <h3>No Pending Requests</h3>
            <p>
              There are currently no trainer registration requests to review
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminDashboard;
