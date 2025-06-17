import React, { useState, useEffect } from "react";
import "../assets/styles/AddSessions.css";
import { Calendar, Clock, Users, Info, CheckCircle } from "lucide-react";
import Cookies from "js-cookie";

const AddSessions = () => {
  const weekdays = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];
  const [trainerLoggedIn, setTrainerLoggedIn] = useState(false); //for now this simulates if im logged in as trainer or gym attendant

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
  const [userId, setUserId] = useState(-1);
  const [scheduleUpdated, setScheduleUpdated] = useState(false);
  const [noAttendants, setNoAttendants] = useState(0); // this number does not represent the number of attendants in the session, but rather to trigger useEffect
  const [scheduledSessions, setScheduledSessions] = useState([]);
  const [sessionAttendants, setSessionAttendants] = useState([]); // Add this state

  const getCurrentWeekDates = () => {
    const today = new Date();
    const currentDay = today.getDay();
    const monday = new Date(today);

    monday.setDate(today.getDate() - (currentDay === 0 ? 6 : currentDay - 1));

    return weekdays.map((day, index) => {
      const date = new Date(monday);
      date.setDate(monday.getDate() + index);

      // Format the date as yyyy-MM-dd
      const formattedDate = date.toISOString().split("T")[0];

      return {
        day,
        date: formattedDate,
      };
    });
  };

  const weekDates = getCurrentWeekDates();

  useEffect(() => {
    const fetchSessions = async () => {
      try {
        // Get the start and end dates of the current week
        const startDate = weekDates[0]?.date; // Monday
        const endDate = weekDates[weekDates.length - 1]?.date; // Friday

        // Send the start and end dates as query parameters
        const response = await fetch(
          `http://localhost:8080/sessions/week?startDate=${startDate}&endDate=${endDate}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (response.ok) {
          const sessions = await response.json();
          console.log("Fetched sessions:", sessions);
          setScheduledSessions(sessions); // Update the state with fetched sessions
        } else {
          console.error("Failed to fetch sessions for the week.");
        }
      } catch (error) {
        console.error("Error fetching sessions:", error);
      }
    };

    fetchSessions();

    const fetchUserData = async () => {
      const LOCAL_URL = "http://localhost:8080";
      const token = Cookies.get("token");
      await fetch(LOCAL_URL + "/header/info", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      })
        .then(async (res) => {
          const data = await res.json();
          var role = data.role;
          setUserId(data.id);
          if (role === "TRAINER") {
            setTrainerLoggedIn(true);
          }
          fetchSessionAttendants(data.id); // Pass the fetched ID directly
        })
        .catch((err) => {
          console.error(err);
        });
      console.log("User ID:", userId);
      fetchSessionAttendants(userId);
      if (scheduleUpdated) {
        const timer = setTimeout(() => {
          setScheduleUpdated(false);
        }, 3000);

        return () => clearTimeout(timer); // Cleanup the timer
      }
    };

    fetchUserData();
  }, [noAttendants]); //[weekDates, scheduleUpdated]); // Dependencies: `weekDates` and `scheduleUpdated`

  const fetchSessionAttendants = async (id) => {
    console.log("Fetching session attendants for user ID:", id);
    const LOCAL_URL = "http://localhost:8080";
    const token = Cookies.get("token");
    await fetch(LOCAL_URL + `/sessions/getSessionAttendants?id=${id}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then(async (res) => {
        const data = await res.json();
        console.log("Fetched session attendants:", data);
        setSessionAttendants(data); // Store the fetched attendants in state
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const handleSubmit = async (e) => {
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

    // Extract the start time from the time slot and format it as HH:mm:ss
    const timeSlot = selectedTime.split(" - ")[0] + ":00";

    // Get the current week's date for the selected day
    const selectedDate = weekDates.find(
      (date) => date.day === selectedDay
    )?.date;

    const newSession = {
      dayInWeek: selectedDay,
      timeSlot: timeSlot, // Now in HH:mm:ss format
      title: sessionTitle,
      description: sessionDescription,
      maxAttendants: maxAttendants,
      currentAttendants: 0,
      date: selectedDate,
    };

    try {
      const response = await fetch("http://localhost:8080/sessions/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newSession),
      });

      if (response.ok) {
        const savedSession = await response.json();

        // Add the saved session to the local state
        setScheduledSessions([...scheduledSessions, savedSession]);

        // Reset form fields
        setSessionTitle("");
        setSessionDescription("");
        setMaxAttendants(10);
        setSelectedDay("");
        setSelectedTime("");

        setScheduleUpdated(true);
      } else {
        alert("Failed to add session. Please try again.");
      }
    } catch (error) {
      console.error("Error adding session:", error);
      alert("An error occurred while adding the session.");
    }
  };

  const handleDeleteSession = async (id, date, timeSlot) => {
    try {
      // Format the date and timeSlot to match the backend's expected format
      const formattedDate = new Date(date).toISOString().split("T")[0]; // yyyy-MM-dd
      const formattedTimeSlot = timeSlot; // Already in HH:mm:ss format

      // Send DELETE request to the backend with id, date, and timeSlot as query parameters
      const response = await fetch(
        `http://localhost:8080/sessions/delete?id=${id}&date=${formattedDate}&timeSlot=${formattedTimeSlot}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        // Remove the session from the local state
        setScheduledSessions(
          scheduledSessions.filter(
            (session) =>
              !(
                session.id === id &&
                session.date === date &&
                session.timeSlot === timeSlot
              )
          )
        );
        alert("Session deleted successfully.");
      } else {
        alert("Failed to delete session. Please try again.");
      }
    } catch (error) {
      console.error("Error deleting session:", error);
      alert("An error occurred while deleting the session.");
    }
  };

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
                    // Match the fetched session data with the current day and time
                    const session = scheduledSessions.find(
                      (s) =>
                        s.dayInWeek === day &&
                        s.timeSlot === time.split(" - ")[0] + ":00"
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
                              onClick={() =>
                                handleDeleteSession(
                                  session.id,
                                  session.date,
                                  session.timeSlot
                                )
                              }
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
                            {sessionAttendants.some(
                              (attendant) =>
                                attendant.sessionId === session.id &&
                                attendant.userId === userId
                            ) ? (
                              <button
                                className="join-button green-color"
                                disabled
                              >
                                Joined
                              </button>
                            ) : (
                              <button
                                className="join-button blue-color"
                                onClick={async () => {
                                  if (
                                    session.currentAttendants <
                                    session.maxAttendants
                                  ) {
                                    try {
                                      const token = Cookies.get("token");
                                      const response = await fetch(
                                        `http://localhost:8080/sessions/join`,
                                        {
                                          method: "POST",
                                          headers: {
                                            "Content-Type": "application/json",
                                            Authorization: `Bearer ${token}`,
                                          },
                                          body: JSON.stringify({
                                            sessionId: session.id,
                                            userId: userId,
                                          }),
                                        }
                                      );

                                      if (response.ok) {
                                        const updatedSession =
                                          await response.json();

                                        const updatedSessions =
                                          scheduledSessions.map((s) =>
                                            s.id === updatedSession.id
                                              ? updatedSession
                                              : s
                                          );
                                        setScheduledSessions(updatedSessions);

                                        alert(
                                          `You have successfully joined the session: ${session.title}`
                                        );
                                        setNoAttendants(noAttendants + 1);
                                      } else {
                                        alert(
                                          "Failed to join the session. Please try again."
                                        );
                                      }
                                    } catch (error) {
                                      console.error(
                                        "Error joining session:",
                                        error
                                      );
                                      alert(
                                        "An error occurred while joining the session."
                                      );
                                    }
                                  } else {
                                    alert("This session is full.");
                                  }
                                }}
                              >
                                Join
                              </button>
                            )}
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
