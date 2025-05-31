import React, { useState, useEffect } from "react";
import "../assets/styles/AddSessions.css";
import { Calendar, Clock, Users, Info, CheckCircle } from "lucide-react";

const AddSessions = () => {
  const weekdays = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];
  const [trainerLoggedIn, setTrainerLoggedIn] = useState(true); //for now this simulates if im logged in as trainer or gym attendant

  const timeSlots = [
    "07:00 - 09:00",
    "09:00 - 11:00",
    "11:00 - 13:00",
    "13:00 - 15:00",
    "15:00 - 17:00",
    "17:00 - 19:00",
    "19:00 - 21:00",
    "21:00 - 23:00",
  ];

  const [selectedDay, setSelectedDay] = useState("");
  const [selectedTime, setSelectedTime] = useState("");
  const [sessionTitle, setSessionTitle] = useState("");
  const [sessionDescription, setSessionDescription] = useState("");
  const [maxAttendants, setMaxAttendants] = useState(10);
  const [scheduleUpdated, setScheduleUpdated] = useState(false);

  const [scheduledSessions, setScheduledSessions] = useState([]);

  useEffect(() => {
    if (scheduleUpdated) {
      const timer = setTimeout(() => {
        setScheduleUpdated(false);
      }, 3000);
      return () => clearTimeout(timer);
    }
  }, [scheduleUpdated]);

  const handleSubmit = (e) => {
    e.preventDefault();

    const isSlotTaken = scheduledSessions.some(
      (session) => session.day === selectedDay && session.time === selectedTime
    );

    if (isSlotTaken) {
      alert(
        "This time slot is already scheduled. Please choose another time or day."
      );
      return;
    }

    const newSession = {
      id: Date.now(),
      day: selectedDay,
      time: selectedTime,
      title: sessionTitle,
      description: sessionDescription,
      maxAttendants: maxAttendants,
      currentAttendants: 0,
    };

    setScheduledSessions([...scheduledSessions, newSession]);

    setSessionTitle("");
    setSessionDescription("");
    setMaxAttendants(10);

    setScheduleUpdated(true);
  };

  const handleDeleteSession = (sessionId) => {
    setScheduledSessions(
      scheduledSessions.filter((session) => session.id !== sessionId)
    );
  };

  const getCurrentWeekDates = () => {
    const today = new Date();
    const currentDay = today.getDay();
    const monday = new Date(today);

    monday.setDate(today.getDate() - (currentDay === 0 ? 6 : currentDay - 1));

    return weekdays.map((day, index) => {
      const date = new Date(monday);
      date.setDate(monday.getDate() + index);
      return {
        day,
        date: `${date.getMonth() + 1}/${date.getDate()}/${date.getFullYear()}`,
      };
    });
  };

  const weekDates = getCurrentWeekDates();

  return (
    <div className="add-sessions-container">
      <header className="sessions-header">
        <div className="header-content">
          <h1 className="header-title">Schedule Training Sessions</h1>
          <p className="header-subtitle">
            Add 2-hour training sessions to your weekly schedule
          </p>
        </div>
      </header>

      <main className="sessions-main">
        <div className="schedule-container">
          <div className="week-overview">
            <h2 className="section-title">
              <Calendar size={20} className="section-icon" />
              Current Week Schedule
            </h2>
            <div className="dates-display">
              {weekDates.map(({ day, date }) => (
                <div className="date-item" key={day}>
                  <span className="date-day">{day}</span>
                  <span className="date-date">{date}</span>
                </div>
              ))}
            </div>

            <div className="schedule-grid">
              <div className="time-column">
                <div className="grid-header">Time</div>
                {timeSlots.map((time) => (
                  <div className="time-slot" key={time}>
                    {time}
                  </div>
                ))}
              </div>

              {weekdays.map((day) => (
                <div className="day-column" key={day}>
                  <div className="grid-header">{day}</div>
                  {timeSlots.map((time) => {
                    const session = scheduledSessions.find(
                      (s) => s.day === day && s.time === time
                    );

                    return (
                      <div
                        className={`grid-cell ${session ? "scheduled" : ""}`}
                        key={`${day}-${time}`}
                      >
                        {session && trainerLoggedIn && (
                          <div className="session-info">
                            <div className="session-title">{session.title}</div>
                            <div className="session-attendants">
                              <Users size={14} />
                              <span>
                                {session.currentAttendants}/
                                {session.maxAttendants}
                              </span>
                            </div>
                            <button
                              className="delete-button"
                              onClick={() => handleDeleteSession(session.id)}
                            >
                              &times;
                            </button>
                          </div>
                        )}

                        {session && !trainerLoggedIn && (
                          <div className="session-info">
                            <div className="session-title">{session.title}</div>
                            <div className="session-attendants">
                              <Users size={14} />
                              <span>
                                {session.currentAttendants}/
                                {session.maxAttendants}
                              </span>
                            </div>
                            <button
                              className="join-button"
                              onClick={() => {
                                if (
                                  session.currentAttendants <
                                  session.maxAttendants
                                ) {
                                  const updatedSessions = scheduledSessions.map(
                                    (s) =>
                                      s.id === session.id
                                        ? {
                                            ...s,
                                            currentAttendants:
                                              s.currentAttendants + 1,
                                          }
                                        : s
                                  );
                                  setScheduledSessions(updatedSessions);
                                  alert(
                                    `You have joined the session: ${session.title}`
                                  );
                                } else {
                                  alert("This session is full.");
                                }
                              }}
                            >
                              Join
                            </button>
                          </div>
                        )}
                      </div>
                    );
                  })}
                </div>
              ))}
            </div>
          </div>

          {trainerLoggedIn && (
            <div className="add-session-form">
              <h2 className="section-title">
                <Clock size={20} className="section-icon" />
                Add New Session
              </h2>

              {scheduleUpdated && (
                <div className="success-message">
                  <CheckCircle size={16} />
                  Session successfully added to your schedule!
                </div>
              )}

              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="sessionTitle">Session Title*</label>
                  <input
                    type="text"
                    id="sessionTitle"
                    value={sessionTitle}
                    onChange={(e) => setSessionTitle(e.target.value)}
                    placeholder="e.g., HIIT Workout, Yoga Flow, Strength Training"
                    required
                  />
                </div>

                <div className="form-row">
                  <div className="form-group">
                    <label htmlFor="sessionDay">Day*</label>
                    <select
                      id="sessionDay"
                      value={selectedDay}
                      onChange={(e) => setSelectedDay(e.target.value)}
                      required
                    >
                      <option value="">Select a day</option>
                      {weekdays.map((day) => (
                        <option key={day} value={day}>
                          {day}
                        </option>
                      ))}
                    </select>
                  </div>

                  <div className="form-group">
                    <label htmlFor="sessionTime">Time Slot*</label>
                    <select
                      id="sessionTime"
                      value={selectedTime}
                      onChange={(e) => setSelectedTime(e.target.value)}
                      required
                    >
                      <option value="">Select a time</option>
                      {timeSlots.map((time) => (
                        <option key={time} value={time}>
                          {time}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>

                <div className="form-group">
                  <label htmlFor="maxAttendants">Max Attendants*</label>
                  <input
                    type="number"
                    id="maxAttendants"
                    min="1"
                    max="50"
                    value={maxAttendants}
                    onChange={(e) =>
                      setMaxAttendants(parseInt(e.target.value, 10))
                    }
                    required
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="sessionDescription">Description</label>
                  <textarea
                    id="sessionDescription"
                    value={sessionDescription}
                    onChange={(e) => setSessionDescription(e.target.value)}
                    placeholder="Describe what clients can expect in this session..."
                    rows="4"
                  />
                </div>

                <div className="form-actions">
                  <button type="submit" className="submit-button">
                    Add Session
                  </button>
                </div>
              </form>
            </div>
          )}
        </div>
      </main>

      <section className="sessions-tips">
        <div className="tips-container">
          <h2 className="tips-title">
            <Info size={20} className="tips-icon" />
            Tips for Effective Session Planning
          </h2>
          <ul className="tips-list">
            <li>
              Schedule sessions during peak hours when clients are most likely
              to attend
            </li>
            <li>
              Allow yourself at least 30 minutes between sessions for
              preparation
            </li>
            <li>
              Provide detailed descriptions to help clients choose the right
              session
            </li>
            <li>
              Consider your energy levels when scheduling multiple sessions in
              one day
            </li>
            <li>
              Review your schedule weekly to make adjustments based on
              attendance patterns
            </li>
          </ul>
        </div>
      </section>
    </div>
  );
};

export default AddSessions;
