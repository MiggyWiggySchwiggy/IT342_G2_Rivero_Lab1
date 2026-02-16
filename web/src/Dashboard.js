import React from 'react';
import axios from 'axios';

const Dashboard = ({ user, onLogout }) => {

  // Feature: Fetch Profile (Test the protected endpoint)
  const fetchProfile = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/user/me');
      alert("Protected Data Retrieved:\n" + JSON.stringify(response.data, null, 2));
    } catch (error) {
      alert("Failed to fetch profile. Session might be expired.");
    }
  };

  return (
    <div style={{ padding: '50px', fontFamily: 'Arial', textAlign: 'center' }}>
      <div style={{ border: '1px solid #ccc', borderRadius: '10px', padding: '20px', maxWidth: '400px', margin: 'auto', boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }}>
        <h1 style={{ color: '#333' }}>Welcome, {user.firstName}!</h1>
        <p style={{ color: '#666' }}>You have securely logged in.</p>
        
        <div style={{ textAlign: 'left', margin: '20px 0', padding: '10px', background: '#f9f9f9', borderRadius: '5px' }}>
          <p><strong>User ID:</strong> {user.userID}</p>
          <p><strong>Username:</strong> {user.username}</p>
          <p><strong>Email:</strong> {user.email}</p>
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
          <button onClick={fetchProfile} style={btnStyle}>
            Test Protected API (/me)
          </button>
          
          <button onClick={onLogout} style={{ ...btnStyle, background: '#d9534f' }}>
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};

// Simple reusable button style
const btnStyle = {
  padding: '10px 15px',
  color: 'white',
  border: 'none',
  borderRadius: '5px',
  cursor: 'pointer',
  background: '#0275d8',
  fontSize: '16px'
};

export default Dashboard;