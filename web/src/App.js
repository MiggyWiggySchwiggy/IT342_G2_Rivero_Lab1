import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [isLogin, setIsLogin] = useState(true); // Toggle between Login and Register
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    passwordHash: ''
  });
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    
    // Backend URL (Matches your Spring Boot Controller)
    const url = isLogin 
      ? 'http://localhost:8080/api/auth/login' 
      : 'http://localhost:8080/api/auth/register';

    try {
      const response = await axios.post(url, formData);
      setMessage(isLogin ? "Login Successful!" : "Registration Successful! Please Login.");
      console.log(response.data);
    } catch (error) {
      setMessage("Error: " + (error.response?.data || "Connection failed"));
    }
  };

  return (
    <div className="App" style={{ padding: '50px', fontFamily: 'Arial' }}>
      <h1>{isLogin ? 'Login' : 'Register'}</h1>
      
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', maxWidth: '300px', margin: 'auto' }}>
        
        {!isLogin && (
          <>
            <input name="firstName" placeholder="First Name" onChange={handleChange} required style={{ marginBottom: '10px', padding: '8px' }} />
            <input name="lastName" placeholder="Last Name" onChange={handleChange} required style={{ marginBottom: '10px', padding: '8px' }} />
            <input name="email" placeholder="Email" type="email" onChange={handleChange} required style={{ marginBottom: '10px', padding: '8px' }} />
          </>
        )}

        <input name="username" placeholder="Username" onChange={handleChange} required style={{ marginBottom: '10px', padding: '8px' }} />
        <input name="passwordHash" placeholder="Password" type="password" onChange={handleChange} required style={{ marginBottom: '10px', padding: '8px' }} />

        <button type="submit" style={{ padding: '10px', background: 'blue', color: 'white', border: 'none', cursor: 'pointer' }}>
          {isLogin ? 'Login' : 'Register'}
        </button>
      </form>

      {message && <p style={{ color: message.includes('Error') ? 'red' : 'green' }}>{message}</p>}

      <p onClick={() => setIsLogin(!isLogin)} style={{ cursor: 'pointer', color: 'blue', textDecoration: 'underline' }}>
        {isLogin ? "Need an account? Register here." : "Already have an account? Login here."}
      </p>
    </div>
  );
}

export default App;