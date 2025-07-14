import { useState, useEffect } from "react";
import React from "react";
import { Link, redirect, useNavigate } from "react-router-dom";
import "../assets/styles/Header.css";
import Cookies from "js-cookie";
import {
  Menu,
  X,
  User,
  LogOut,
  Calendar,
  Dumbbell,
  ChevronDown,
} from "lucide-react";
import { set } from "react-hook-form";

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isProfileOpen, setIsProfileOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [role, setRole] = useState(false);
  const [admin, setAdmin] = useState(false);
  const [roleName, setRoleName] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const token = Cookies.get("token");
    let user = JSON.parse(localStorage.getItem("user-info"));
    if (typeof token === "undefined") {
      setRole(false);
    } else {
      setRole(true);
    }

    const fetchUserData = async () => {
      const LOCAL_URL = "http://localhost:8080";

      await fetch(LOCAL_URL + "/header/info", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      })
        .then(async (res) => {
          console.log(res);
          const data = await res.json();
          var role = data.role;
          setIsLoggedIn(true);
          if (role === "ADMIN") {
            setAdmin(true);
            setRoleName("ADMIN");
          } else if (role === "TRAINER") {
            setAdmin(false);
            setRoleName("TRAINER");
          } else if (role === "GYM_ATTENDANT") {
            setAdmin(false);
            setRoleName("GYM_ATTENDANT");
          }
        })
        .catch((err) => {
          console.error(err);
        });
    };
    if (role) {
      fetchUserData();
      console.log("fetcha san user data");
    }
  }, [role]);

  const handleLogout = () => {
    console.log("Logout clicked");
    Cookies.remove("token");
    setIsLoggedIn(false);
    navigate("/");
    window.location.reload();
  };

  return (
    <header className="header">
      <div className="header-container">
        <div className="header-top">
          <div className="logo">
            <Dumbbell className="logo-icon" />
            <span className="logo-text">FitConnect</span>
          </div>

          <nav className="nav-desktop">
            <a href="/">Home</a>
            <a href="/trainers">Trainers</a>
            {roleName === "GYM_ATTENDANT" && <a href="/classes">Classes</a>}
            {roleName === "ADMIN" && (
              <a href="/admin/dashboard">Admin Dashboard</a>
            )}
            <a href="/membership">Membership</a>
            {roleName === "TRAINER" && <a href="/addSessions">Add sessions</a>}
          </nav>

          <div className="user-actions">
            {isLoggedIn ? (
              <div className="profile-dropdown">
                <button
                  onClick={() => setIsProfileOpen(!isProfileOpen)}
                  className="profile-button"
                >
                  <User size={18} />
                  <span>Account</span>
                  <ChevronDown size={16} />
                </button>
                {isProfileOpen && (
                  <div className="dropdown-menu">
                    <a href="/profile">
                      <User size={16} /> Profile
                    </a>
                    {roleName !== "ADMIN" && (
                      <>
                        <a href="/schedule">
                          <Calendar size={16} /> My Schedule
                        </a>
                        <a href="/workouts">
                          <Dumbbell size={16} /> Workouts
                        </a>
                      </>
                    )}
                    <div className="logout" onClick={handleLogout}>
                      <LogOut size={16} /> Logout
                    </div>
                  </div>
                )}
              </div>
            ) : (
              <div className="auth-buttons">
                <a href="/login" className="btn-signin">
                  Login
                </a>
                <a href="/signup" className="btn-register">
                  Register
                </a>
              </div>
            )}
          </div>

          <button
            className="menu-toggle"
            onClick={() => setIsMenuOpen(!isMenuOpen)}
          >
            {isMenuOpen ? <X size={24} /> : <Menu size={24} />}
          </button>
        </div>

        {isMenuOpen && (
          <div className="nav-mobile">
            <a href="/">Home</a>
            <a href="/trainers">Trainers</a>
            <a href="/classes">Classes</a>
            <a href="/membership">Membership</a>
            <a href="/contact">Contact</a>
            <div className="nav-divider" />
            {isLoggedIn ? (
              <>
                <a href="/profile">
                  <User size={16} /> Profile
                </a>
                {roleName !== "ADMIN" && (
                  <>
                    <a href="/schedule">
                      <Calendar size={16} /> My Schedule
                    </a>
                    <a href="/workouts">
                      <Dumbbell size={16} /> Workouts
                    </a>
                  </>
                )}
                <a href="/logout" className="logout">
                  <LogOut size={16} /> Logout
                </a>
              </>
            ) : (
              <>
                <a href="/login">Login In</a>
                <a href="/signup">Register</a>
              </>
            )}
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;
