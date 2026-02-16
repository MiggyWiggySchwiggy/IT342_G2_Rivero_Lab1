import React, { useState } from 'react';
import axios from 'axios';
import './App.css';
import Dashboard from './Dashboard'; // IMPORT THE NEW COMPONENT

function App() {
  const [isLogin, setIsLogin] = useState(true); 
  const [user, setUser] = useState(null); 
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    passwordHash: ''
  });
  const [message, setMessage] = useState('');

  // IMPORTANT: Allow cookies for session management
  axios.defaults.withCredentials = true;

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    
    const url = isLogin 
      ? 'http://localhost:8080/api/auth/login' 
      : 'http://localhost:8080/api/auth/register';

    try {
      const response = await axios.post(url, formData);
      
      if (isLogin) {
        setUser(response.data); // Save the user data to state
        setMessage("Login Successful!");
      } else {
        setMessage("Registration Successful! Please Login.");
        setIsLogin(true);
      }
    } catch (error) {
      setMessage("Error: " + (error.response?.data || "Connection failed"));
    }
  };

  const handleLogout = async () => {
    try {
        await axios.post('http://localhost:8080/api/auth/logout');
        setUser(null); // Clear user state to return to login screen
        setMessage("Logged out successfully.");
    } catch (error) {
        console.error("Logout failed", error);
    }
  };

  // --- RENDER DASHBOARD IF LOGGED IN ---
  if (user) {
    return <Dashboard user={user} onLogout={handleLogout} />;
  }

  // --- RENDER LOGIN / REGISTER FORM ---
  return (
    <div className="App" style={{ padding: '50px', fontFamily: 'Arial' }}>
      <h1>{isLogin ? 'Login' : 'Register'}</h1>
      
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', maxWidth: '300px', margin: 'auto' }}>
        
        {!isLogin && (
          <>
            <input name="firstName" placeholder="First Name" onChange={handleChange} required style={inputStyle} />
            <input name="lastName" placeholder="Last Name" onChange={handleChange} required style={inputStyle} />
            <input name="email" placeholder="Email" type="email" onChange={handleChange} required style={inputStyle} />
          </>
        )}

        <input name="username" placeholder="Username" onChange={handleChange} required style={inputStyle} />
        <input name="passwordHash" placeholder="Password" type="password" onChange={handleChange} required style={inputStyle} />

        <button type="submit" style={{ ...btnStyle, background: 'blue' }}>
          {isLogin ? 'Login' : 'Register'}
        </button>
      </form>

      {message && <p style={{ color: message.includes('Error') ? 'red' : 'green', marginTop: '10px' }}>{message}</p>}

      <p onClick={() => setIsLogin(!isLogin)} style={{ cursor: 'pointer', color: 'blue', textDecoration: 'underline', marginTop: '20px' }}>
        {isLogin ? "Need an account? Register here." : "Already have an account? Login here."}
      </p>
    </div>
  );
}

const inputStyle = { marginBottom: '10px', padding: '10px', fontSize: '16px' };
const btnStyle = { padding: '10px', color: 'white', border: 'none', cursor: 'pointer', background: 'green', fontSize: '16px' };

export default App;