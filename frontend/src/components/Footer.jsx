import React from 'react';
import '../assets/styles/Footer.css'; 

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-container">
        <div className="footer-grid">
          <div className="footer-section">
            <h3 className="footer-title">
              FitConnect
            </h3>
            <p className="footer-text">
              Connecting trainers and members for a healthier lifestyle.
            </p>
            
          </div>

          <div className="footer-section">
            <h3 className="footer-title">Quick Links</h3>
            <ul>
              <li><a href="/">Home</a></li>
              <li><a href="/about">About Us</a></li>
              <li><a href="/trainers">Our Trainers</a></li>
              <li><a href="/classes">Classes</a></li>
            </ul>
          </div>

          <div className="footer-section">
            <h3 className="footer-title">Resources</h3>
            <ul>
              <li><a href="/blog">Blog</a></li>
              <li><a href="/faq">FAQ</a></li>
              <li><a href="/privacy">Privacy Policy</a></li>
              <li><a href="/terms">Terms of Service</a></li>
            </ul>
          </div>

          <div className="footer-section">
            <h3 className="footer-title">Contact</h3>
            <address className="footer-address">
              <p>Unska 3</p>
              <p>Zagreb, ZG 10000</p>
              <p>info@fitconnect.com</p>
              <p>+385 1 6129 905</p>
            </address>
          </div>
        </div>

        <div className="footer-bottom">
          <p>&copy; {new Date().getFullYear()} FitConnect. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
