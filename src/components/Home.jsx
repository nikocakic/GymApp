import React from 'react';
import '../assets/styles/Home.css';
import { Users, Calendar, Dumbbell } from 'lucide-react';

const Home = () => {
  return (
    <div className="home-container">

      <main className="main-content">
        <section className="hero-section">
          <div className="hero-container">
            <h1 className="hero-title">Transform Your Fitness Journey</h1>
            <p className="hero-subtitle">Connect with expert trainers, track your progress, and achieve your fitness goals with FitConnect.</p>
            <div className="cta-buttons">
              <a href="/signup" className="cta-button cta-button-primary">Join Now</a>
              <a href="/trainers" className="cta-button cta-button-secondary">Find a Trainer</a>
            </div>
          </div>
        </section>

        <section className="features-section">
          <div className="features-container">
            <h2 className="features-title">Why Choose FitConnect?</h2>
            <div className="features-grid">
              <div className="feature-card">
                <div className="feature-icon">
                  <Users className="icon" />
                </div>
                <h3 className="feature-title">Expert Trainers</h3>
                <p className="feature-description">Connect with certified fitness professionals who specialize in various training methods.</p>
              </div>

              <div className="feature-card">
                <div className="feature-icon">
                  <Calendar className="icon" />
                </div>
                <h3 className="feature-title">Flexible Scheduling</h3>
                <p className="feature-description">Book sessions that fit your schedule and receive reminders before your workout.</p>
              </div>

              <div className="feature-card">
                <div className="feature-icon">
                  <Dumbbell className="icon" />
                </div>
                <h3 className="feature-title">Progress Tracking</h3>
                <p className="feature-description">Monitor your fitness journey with detailed workout logs and achievement tracking.</p>
              </div>
            </div>
          </div>
        </section>

        <section className="cta-section">
          <div className="cta-container">
            <h2 className="cta-title">Ready to Start Your Fitness Journey?</h2>
            <p className="cta-subtitle">Join FitConnect today and take the first step toward achieving your fitness goals.</p>
            <a href="/signup" className="cta-button cta-button-primary">Sign Up Now</a>
          </div>
        </section>
      </main>

    </div>
  );
};

export default Home;
