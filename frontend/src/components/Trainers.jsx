import React, { useState, useEffect } from "react";
import "../assets/styles/Trainers.css";
import { Search, Award } from "lucide-react";

const Trainers = () => {
  const [trainersData, setTrainersData] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    // Fetch trainers data from the backend
    const fetchTrainers = async () => {
      try {
        const response = await fetch("http://localhost:8080/trainers/getAll"); // Replace with your backend URL
        if (!response.ok) {
          throw new Error("Failed to fetch trainers");
        }
        const data = await response.json();
        setTrainersData(data);
      } catch (error) {
        console.error("Error fetching trainers:", error);
      }
    };

    fetchTrainers();
  }, []);

  const filteredTrainers = trainersData.filter((trainer) => {
    const fullName = `${trainer.firstName} ${trainer.lastName}`.toLowerCase();
    return fullName.includes(searchTerm.toLowerCase());
  });

  return (
    <div className="trainers-container">
      <header className="trainers-header">
        <div className="header-content">
          <h1 className="header-title">Meet Our Expert Trainers</h1>
          <div className="search-container">
            <Search size={20} className="search-icon" />
            <input
              type="text"
              placeholder="Search trainers by name..."
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
            filteredTrainers.map((trainer) => (
              <div className="trainer-card" key={trainer.id}>
                <div className="trainer-info">
                  <h2 className="trainer-name">
                    {trainer.firstName} {trainer.lastName}
                  </h2>
                  <p className="trainer-email">Email: {trainer.email}</p>
                  <p className="trainer-birthdate">
                    Birthdate: {trainer.birthdate}
                  </p>
                  <p className="trainer-username">
                    Username: {trainer.username}
                  </p>
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
    </div>
  );
};

export default Trainers;
