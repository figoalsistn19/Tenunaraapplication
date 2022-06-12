Tenunara Endpoint
===========

**Framework :** Node.js

**Dependencies :**
- Cors
- Express
- Firebase-admin
- Firebase-tools
- Multer
- Nodemon
- Body-parser
- @google-cloud/storage
- path
- axios
- util
- form-data

**Uri** : (https://tenunara-351304.et.r.appspot.com/)

## List of Endpoint :
- **GET /toko** => Return all toko
- **GET /kursus** => Return all kursus
- **GET /tenun?id=** => Return specific tenun from id
- **GET /profile** => Now client do it directly to the database
- **POST /reg** => Now client do it directly to the database
- **POST /login** => Now client do it directly to the database
- **POST /logout** => Now client do it directly to the database
- **PUT /user** => Now client do it directly to the database
- **GET /allHistory** => Return all history data as scan
- **GET /history?id=** => Return user scan history
- **GET /topWeekly** => Return top 10 last week by most like from user
- **POST /history** => Send scanning request, return scanning result then save as history
- **DELETE /history** => Delete user history
- **PUT /history** => Update history share status true/false
- **POST /like** => Adding like to scan history
- **DELETE /like** => Canceling like from scan history

## All API is public except this, we need user is authenticated :
- **GET/POST/PUT/DELETE /history**
- **GET/POST/PUT/DELETE /user** 
- **GET/POST/PUT/DELETE /like**


