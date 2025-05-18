import React, { useState } from 'react';
import '../assets/styles/Trainers.css';
import { Search, Award, Clock } from 'lucide-react';

// Import all trainer images
import SarahJohnson from '../assets/pictures/SarahJohnson.png';
// Add more imports as you add more local images, e.g.:
// import MichaelChen from '../assets/pictures/MichaelChen.png';

const imageMap = {
    'SarahJohnson.png': SarahJohnson,
    // Add your other imports here as you add them, e.g.:
    // 'MichaelChen.png': MichaelChen,
  };

const trainersData = [
  {
    id: 1,
    firstName: 'Sarah',
    lastName: 'Johnson',
    image: 'SarahJohnson.png',
    description: 'Specializing in strength training and HIIT workouts. Sarah creates challenging programs that deliver results while keeping workouts fun and engaging.',
    yearsExperience: 8
  },
  {
    id: 2,
    firstName: 'Michael',
    lastName: 'Chen',
    image: 'SarahJohnson.png',
    description: 'Certified yoga instructor and nutrition coach. Michael focuses on holistic wellness, combining mindful movement with proper nutrition guidance.',
    yearsExperience: 5
  },
  {
    id: 3,
    firstName: 'Elena',
    lastName: 'Rodriguez',
    image: 'SarahJohnson.png',
    description: 'Former professional athlete specializing in sports-specific training and rehabilitation. Elena helps clients recover from injuries and reach peak performance.',
    yearsExperience: 12
  },
  {
    id: 4,
    firstName: 'David',
    lastName: 'Patel',
    image: 'SarahJohnson.png',
    description: 'Functional fitness expert who designs comprehensive training programs. David emphasizes proper form and sustainable fitness practices.',
    yearsExperience: 7
  },
  {
    id: 5,
    firstName: 'Jasmine',
    lastName: 'Williams',
    image: 'SarahJohnson.png',
    description: 'Specializing in bodyweight training and calisthenics. Jasmine helps clients build strength and mobility without the need for complex equipment.',
    yearsExperience: 6
  },
  {
    id: 6,
    firstName: 'Marcus',
    lastName: 'Thompson',
    image: 'SarahJohnson.png',
    description: 'Expert in weight management and metabolic conditioning. Marcus develops personalized plans to help clients achieve sustainable weight loss goals.',
    yearsExperience: 9
  }
];

// Map image filenames to imported modules


const Trainers = () => {
  const [searchTerm, setSearchTerm] = useState('');

  const filteredTrainers = trainersData.filter(trainer => {
    const fullName = `${trainer.firstName} ${trainer.lastName}`.toLowerCase();
    return fullName.includes(searchTerm.toLowerCase()) ||
      trainer.description.toLowerCase().includes(searchTerm.toLowerCase());
  });

  return (
    <div className="trainers-container">
      <header className="trainers-header">
        <div className="header-content">
          <h1 className="header-title">Meet Our Expert Trainers</h1>
          <p className="header-subtitle">
            Connect with fitness professionals who will guide and support you on your fitness journey
          </p>
          <div className="search-container">
            <Search size={20} className="search-icon" />
            <input
              type="text"
              placeholder="Search trainers by name or specialty..."
              className="search-input"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
        </div>
      </header>

      <main className="trainers-main">
        <div className="trainers-grid">
          {filteredTrainers.length > 0 ? (
            filteredTrainers.map(trainer => (
              <div className="trainer-card" key={trainer.id}>
                <div className="trainer-image-container">
                  <img
                    src={imageMap[trainer.image]}
                    alt={`${trainer.firstName} ${trainer.lastName}`}
                    className="trainer-image"
                  />
                </div>
                <div className="trainer-info">
                  <h2 className="trainer-name">{trainer.firstName} {trainer.lastName}</h2>
                  <div className="trainer-experience">
                    <Clock size={16} className="experience-icon" />
                    <span>{trainer.yearsExperience} {trainer.yearsExperience === 1 ? 'year' : 'years'} experience</span>
                  </div>
                  <p className="trainer-description">{trainer.description}</p>
                  <a href={`/trainers/${trainer.id}`} className="trainer-cta">
                    View Profile
                  </a>
                </div>
              </div>
            ))
          ) : (
            <div className="no-results">
              <Award size={48} />
              <h3>No trainers found</h3>
              <p>Try adjusting your search criteria</p>
            </div>
          )}
        </div>
      </main>

      <section className="trainers-cta">
        <div className="cta-container">
          <h2 className="cta-title">Ready to Start Working with a Trainer?</h2>
          <p className="cta-subtitle">Create your account to connect with any of our expert trainers</p>
          <div className="cta-buttons">
            <a href="/signup" className="cta-button cta-button-primary">Sign Up Now</a>
            <a href="/how-it-works" className="cta-button cta-button-secondary">Learn How It Works</a>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Trainers;
