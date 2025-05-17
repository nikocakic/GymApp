import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../assets/styles/SignUp.css';
import { Mail, Lock, User, Calendar, CheckCircle } from 'lucide-react';

const SignUp = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    birthdate: '',
    agreeTerms: false
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value
    });
  };

  const navigate = useNavigate();
  const handleGiveUp = () => {
    const confirmed = window.confirm("Are you sure you want to give up?");
    if (confirmed) {
      navigate('/home');
    }
  };

  const validateForm = () => {
    const newErrors = {};
    
    if (!formData.firstName.trim()) {
      newErrors.firstName = 'First name is required';
    }
    
    if (!formData.lastName.trim()) {
      newErrors.lastName = 'Last name is required';
    }
    
    if (!formData.email.trim()) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Email is invalid';
    }
    
    if (!formData.password) {
      newErrors.password = 'Password is required';
    } else if (formData.password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters';
    }
    
    if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = 'Passwords do not match';
    }
    
    if (!formData.birthdate) {
      newErrors.birthdate = 'Birthdate is required';
    }
    
    if (!formData.agreeTerms) {
      newErrors.agreeTerms = 'You must agree to the terms and conditions';
    }
    
    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validateForm();
    
    if (Object.keys(validationErrors).length === 0) {
      console.log('Form submitted:', formData);
      alert('Sign up successful! Redirecting to dashboard...');
    } else {
      setErrors(validationErrors);
    }
  };

  return (
    <div className="signup-container">
      <main className="signup-content">
        <section className="signup-section">
          <div className="signup-card">
            <h1 className="signup-title">Create Your Account</h1>
            <p className="signup-subtitle">Join FitConnect and start your fitness journey today!</p>
            
            <form className="signup-form" onSubmit={handleSubmit}>
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="firstName">First Name</label>
                  <div className="input-group">
                    <User className="input-icon" size={20} />
                    <input
                      type="text"
                      id="firstName"
                      name="firstName"
                      value={formData.firstName}
                      onChange={handleChange}
                      className={`form-control ${errors.firstName ? 'input-error' : ''}`}
                      placeholder="Enter your first name"
                    />
                  </div>
                  {errors.firstName && <span className="error-message">{errors.firstName}</span>}
                </div>
                
                <div className="form-group">
                  <label htmlFor="lastName">Last Name</label>
                  <div className="input-group">
                    <User className="input-icon" size={20} />
                    <input
                      type="text"
                      id="lastName"
                      name="lastName"
                      value={formData.lastName}
                      onChange={handleChange}
                      className={`form-control ${errors.lastName ? 'input-error' : ''}`}
                      placeholder="Enter your last name"
                    />
                  </div>
                  {errors.lastName && <span className="error-message">{errors.lastName}</span>}
                </div>
              </div>
              
              <div className="form-group">
                <label htmlFor="email">Email Address</label>
                <div className="input-group">
                  <Mail className="input-icon" size={20} />
                  <input
                    type="email"
                    id="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    className={`form-control ${errors.email ? 'input-error' : ''}`}
                    placeholder="your.email@example.com"
                  />
                </div>
                {errors.email && <span className="error-message">{errors.email}</span>}
              </div>
              
              <div className="form-group">
                <label htmlFor="password">Password</label>
                <div className="input-group">
                  <Lock className="input-icon" size={20} />
                  <input
                    type="password"
                    id="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    className={`form-control ${errors.password ? 'input-error' : ''}`}
                    placeholder="Create a secure password"
                  />
                </div>
                {errors.password && <span className="error-message">{errors.password}</span>}
              </div>
              
              <div className="form-group">
                <label htmlFor="confirmPassword">Confirm Password</label>
                <div className="input-group">
                  <Lock className="input-icon" size={20} />
                  <input
                    type="password"
                    id="confirmPassword"
                    name="confirmPassword"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    className={`form-control ${errors.confirmPassword ? 'input-error' : ''}`}
                    placeholder="Confirm your password"
                  />
                </div>
                {errors.confirmPassword && <span className="error-message">{errors.confirmPassword}</span>}
              </div>
              
              <div className="form-group">
                <label htmlFor="birthdate">Birth Date</label>
                <div className="input-group">
                  <Calendar className="input-icon" size={20} />
                  <input
                    type="date"
                    id="birthdate"
                    name="birthdate"
                    value={formData.birthdate}
                    onChange={handleChange}
                    className={`form-control ${errors.birthdate ? 'input-error' : ''}`}
                  />
                </div>
                {errors.birthdate && <span className="error-message">{errors.birthdate}</span>}
              </div>
              
              <div className="form-group checkbox-group">
                <input
                  type="checkbox"
                  id="agreeTerms"
                  name="agreeTerms"
                  checked={formData.agreeTerms}
                  onChange={handleChange}
                  className={errors.agreeTerms ? 'checkbox-error' : ''}
                />
                <label htmlFor="agreeTerms" className="checkbox-label">
                  I agree to the <a href="/terms" className="terms-link">Terms of Service</a> and <a href="/privacy" className="terms-link">Privacy Policy</a>
                </label>
                {errors.agreeTerms && <span className="error-message">{errors.agreeTerms}</span>}
              </div>
              
              <button type="submit" className="signup-button">
                Create Account
              </button>
            </form>

            <button className="giveup-button" onClick={handleGiveUp}>
                Give up
            </button>
            
            <div className="login-link-container">
              Already have an account? <a href="/login" className="login-link">Log in</a>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
};

export default SignUp;