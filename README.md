# Prog20082_GroupProject

Parking App (Group #10)

Purpose of the app: Lets user park without having to go to the pay meter.

The target audience: Everyone, customers to the establishment which uses our parking system.

Important features: Lets user pick one of the parking locations using geolocation and GPS and a map. Lets user sign in to the parking spot then sign out which then calculates how much they need to pay.

Services and functionalities provided to the user: The application will automatically let the user know which parking spots are taken with the parking spot being taken out of the spinner.

Various use-cases of the app: Not needing to go to a parking meter in the cold winter.

Work Distribution

Fundamental Android Functionalities: Aydin Battal 991521740 Ask the user to input login credentials (home page). Calculate the fee. Take the user to the payment page and apply the payment. Have an option to email/text a receipt after the payment. UI Related: Home/login page Payment page

Data Persistence: Adam Czubernat 991582251 Store users info when they enter parking lot Create database that has all spots in the parking lot Assign cars entering to available spots, and update the database Remove cars from parking spots when user leaves, then update database Provide info if parking lot is full or how many and which spots are available UI Related: Provide UI that takes sure info when they enter the parking lot

Location Services: Karan Patel 991519115 I will have the feature of automatically fetch the devices location and finding the nearest parking lot as the default. User can override the location set by geolocation and input location on the map. Use being if the application has a bug or user refuses to turn on location services UI Related: Landing page when app opens for 5 seconds then switches to main screen Geo location page with minimap and markers if possible which then takes you to a screen which has the proper database loaded up to pick a spot
