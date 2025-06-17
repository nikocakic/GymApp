import React, { useState } from "react";
import "../assets/styles/Login.css";
import { Mail, Lock, AlertCircle } from "lucide-react";
import Cookies from "js-cookie";
import { jwtDecode } from "jwt-decode";
// Import useNavigate
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    rememberMe: false,
  });

  const [errors, setErrors] = useState({});
  const [loginError, setLoginError] = useState("");

  // Initialize navigate
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });

    // Clear specific field error when user starts typing
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: "",
      });
    }

    // Clear general login error when user makes any change
    if (loginError) {
      setLoginError("");
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.email.trim()) {
      newErrors.email = "Email is required";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "Email is invalid";
    }

    if (!formData.password) {
      newErrors.password = "Password is required";
    }

    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validateForm();

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    const userDetail = { email: formData.email, password: formData.password };
    console.log(userDetail);
    const LOCAL_URL = "http://localhost:8080";
    fetch(LOCAL_URL + "/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userDetail),
    })
      .then((res) => {
        console.log(res);
        if (!res.ok) {
          throw new Error(`HTTP error! Status: ${res.status}`);
        }
        return res.headers.get("Authorization");
      })
      .then((data) => {
        data = data.substring(7); // Remove "BEARER " from start
        const decoded = jwtDecode(data);
        console.log("Decoded JWT:");
        console.log(decoded);
        const en = decoded.enabled;
        console.log(en);
        console.log("Decoded token:");
        console.log(decoded);
        if (!en) {
          alert(
            "Administrator nije prihvatio vašu registraciju, molim vas pričekajte"
          );
        } else {
          Cookies.set("token", data, {
            expires: 60 * 60 * 1000,
            secure: false,
          });
          console.log(data);
          console.log("User logged in and role added!");
          navigate("/");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Invalid login. Please register.");
      });
  };

  return (
    <div className="login-container">
      <main className="login-content">
        <section className="login-section">
          <div className="login-card">
            <h1 className="login-title">Welcome Back</h1>
            <p className="login-subtitle">
              Log in to continue your fitness journey with FitConnect
            </p>

            {loginError && (
              <div className="login-error-alert">
                <AlertCircle size={20} />
                <span>{loginError}</span>
              </div>
            )}

            <form className="login-form" onSubmit={handleSubmit}>
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
                    className={`form-control ${
                      errors.email ? "input-error" : ""
                    }`}
                    placeholder="your.email@example.com"
                  />
                </div>
                {errors.email && (
                  <span className="error-message">{errors.email}</span>
                )}
              </div>

              <div className="form-group">
                <div className="password-label-row">
                  <label htmlFor="password">Password</label>
                  <a href="/forgot-password" className="forgot-password-link">
                    Forgot Password?
                  </a>
                </div>
                <div className="input-group">
                  <Lock className="input-icon" size={20} />
                  <input
                    type="password"
                    id="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    className={`form-control ${
                      errors.password ? "input-error" : ""
                    }`}
                    placeholder="Enter your password"
                  />
                </div>
                {errors.password && (
                  <span className="error-message">{errors.password}</span>
                )}
              </div>

              <div className="form-group checkbox-group">
                <input
                  type="checkbox"
                  id="rememberMe"
                  name="rememberMe"
                  checked={formData.rememberMe}
                  onChange={handleChange}
                />
                <label htmlFor="rememberMe" className="checkbox-label">
                  Remember me on this device
                </label>
              </div>

              <button type="submit" className="login-button">
                Sign In
              </button>
            </form>

            <div className="divider">
              <span>or</span>
            </div>

            <div className="social-login-options">
              <button className="social-login-button google-button">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="18"
                  height="18"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <path d="M20.283 10.356h-8.327v3.451h4.792c-.446 2.193-2.313 3.453-4.792 3.453a5.27 5.27 0 0 1-5.279-5.28 5.27 5.27 0 0 1 5.279-5.279c1.259 0 2.397.447 3.29 1.178l2.6-2.599c-1.584-1.381-3.615-2.233-5.89-2.233a8.908 8.908 0 0 0-8.934 8.934 8.907 8.907 0 0 0 8.934 8.934c4.467 0 8.529-3.249 8.529-8.934 0-.528-.081-1.097-.202-1.625z"></path>
                </svg>
                Continue with Google
              </button>

              <button className="social-login-button facebook-button">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="18"
                  height="18"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"></path>
                </svg>
                Continue with Facebook
              </button>
            </div>

            <div className="signup-link-container">
              Don't have an account?{" "}
              <a href="/signup" className="signup-link">
                Sign up
              </a>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
};

export default Login;
