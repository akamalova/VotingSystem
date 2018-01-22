Design and implement a JSON API using Hibernate/Spring/SpringMVC without frontend.

Voting system for deciding where to have lunch. 
API have 2 types of users: admin and regular users. 
Admin can input a restaurant and it's lunch menu of the day. 
The menu contains some items, just a dish name and price. 

Admin can create, update, delete menu of the restaurant. 
Admin can get lists with users, votes, restaurants, menu of the actual restaurant and dishes of actual menu.
Also admin can get lists of users, votes, menu by date. He can get user by mail, disable users. 
Admin can get list of voted users by date and can take all votes of user.

User can vote on which restaurant he want to have lunch at. Only one vote counted per user.
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed.
User can create, update and delete his votes. Also user can get menu by date.
