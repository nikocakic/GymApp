import { useState } from 'react';
import React from 'react';
import '../assets/styles/Header.css';
import { Menu, X, User, LogOut, Calendar, Dumbbell, ChevronDown } from 'lucide-react';

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isProfileOpen, setIsProfileOpen] = useState(false);

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
            <a href="/classes">Classes</a>
            <a href="/membership">Membership</a>
            <a href="/contact">Contact</a>
          </nav>

          <div className="user-actions">
            <div className="profile-dropdown">
              <button onClick={() => setIsProfileOpen(!isProfileOpen)} className="profile-button">
                <User size={18} />
                <span>Account</span>
                <ChevronDown size={16} />
              </button>
              {isProfileOpen && (
                <div className="dropdown-menu">
                  <a href="/profile"><User size={16} /> Profile</a>
                  <a href="/schedule"><Calendar size={16} /> My Schedule</a>
                  <a href="/workouts"><Dumbbell size={16} /> Workouts</a>
                  <a href="/logout" className="logout"><LogOut size={16} /> Logout</a>
                </div>
              )}
            </div>
          </div>

          <button className="menu-toggle" onClick={() => setIsMenuOpen(!isMenuOpen)}>
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
            <a href="/profile"><User size={16} /> Profile</a>
            <a href="/schedule"><Calendar size={16} /> My Schedule</a>
            <a href="/logout" className="logout"><LogOut size={16} /> Logout</a>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;
