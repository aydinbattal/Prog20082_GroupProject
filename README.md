# SheriBook

Learning Commons Room Booking App (Group #10)

# Purpose of the app:
Lets the user book a learning commons room using a wireless device without going to the booking centre.

# The target audience:
Students of Sheridan College.

# Important features:
Lets the user pick one of the campuses using geolocation and GPS and a map.
Lets the user sign in to the learning commons room then sign out.

# Services and functionalities provided to the user:
The application will automatically let the user know which rooms are booked.
The application will show the nearest campus by default.

# Various use-cases of the app:
Not needing to spend extra time going to a booking centre when there's limited time.
Knowing if there’s any available rooms during desired time slot.

# Work Distribution

# Fundamental Android Functionalities: Aydin Battal 991521740
Ask the user to input login information.
Book a room after receiving booking information like the campus, room number, time slots etc.
Have an option to email/text a receipt after booking.
Have an option to sign out of the account.
Have an option to delete the account.
Have the ability to edit profile information.
Have the ability to contact developers to get support.

# UI Related:
Login page
Home/Profile page
Booking page
Receipts page
Support page

# Data Persistence: Adam Czubernat 991582251
Store users info after they sign up.
Create database that stores users information when they create account(firstName,lastname, email, password, studentID) and related functions when using the database
Create database for booking(room number, campus name, student id, amount of students, duration, time slot) and related functions when using database
Data validation for encrypting password and validating email when creating account

# UI Related:
Sign up/create account page

# Location Services: Karan Patel 991519115
User will select which campus to book their room at through the map.
Will show how far away from each campus?

# UI Related:
Map with markers and selecting a location will load the respective booking page for that campus
Shows distance away?
