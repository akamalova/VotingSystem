Design and implement a JSON API of Voting system using Hibernate/Spring/SpringMVC without frontend.

Usage of the API is via the HTTP protocol. The GET, POST, PUT, and DELETE requests are all used. Resource representations are in JSON.

Voting system for deciding where to have lunch. 
API have 2 types of users: admin and regular users. 
Admin can input a restaurant and it's lunch menu of the day. 
The menu contains some items, just a dish name and price. 

Admin can create, update, delete restaurants, menu and dishes. 
Admin can get lists with users, votes, restaurants.
Also admin can get lists of votes and menu by date. He can get user by mail and disable users. 
Admin can get list of voted users by date and can take all votes of user.

User can vote on which restaurant he want to have lunch at. Only one vote counted per user.
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed.
User can update his profile. Also user can get restaurants with menu by date.

Vor voting user(or admin) open the page with restaurants, their menu and dishes and select the restaurant. Then he send POST request with selected restaurant Id. 

Curl commands to get data for voting and vote:

#### get All Restaurants
`curl -s http://localhost:8080/votingSystem/rest/restaurants --user user@yandex.ru:password`

#### create Vote
`curl -s -X POST -d '{"name":"OneUser", "email":"oneuser@gmail.com", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/votingSystem/rest/restaurants/vote?restaurantId=100003  --user user@yandex.ru:password`

#### get Votes by Date
`curl -s http://localhost:8080/votingSystem/rest/admin/votes/date?newDate=2015-05-30 --user admin@gmail.com:password`
