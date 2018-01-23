### Test RestController (application deployed in application context `votingSystem`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/votingSystem//rest/admin/users --user admin@gmail.com:password`

#### get Users 100001
`curl -s http://localhost:8080/votingSystem/rest/admin/users/100001 --user admin@gmail.com:password`

#### create User
`curl -s -X POST -d '{"name":"OneUser", "email":"oneuser@gmail.com", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/votingSystem/rest/admin/users --user admin@gmail.com:password`

#### update User
`curl -s -X POST -d '{ "id":100001,"name":"UpdatedUser","email":"updated@gmail.com","password":"password"}}' -H 'Content-Type: application/json' http://localhost:8080/votingSystem/rest/admin/users --user admin@gmail.com:password`

#### get All Restaurants
`curl -s http://localhost:8080/votingSystem/rest/restaurants --user user@yandex.ru:password`

#### create Vote
`curl -s -X POST -d '{"name":"OneUser", "email":"oneuser@gmail.com", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/votingSystem/rest/restaurants/vote?restaurantId=100003  --user user@yandex.ru:password`

#### create Vote by Admin
`curl -s -X POST -d '{"name":"OneUser", "email":"oneuser@gmail.com", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/votingSystem/rest/restaurants/vote?restaurantId=100003  --user admin@gmail.com:password`

#### get Votes by Date
`curl -s http://localhost:8080/votingSystem/rest/admin/votes/date?newDate=2015-05-30 --user admin@gmail.com:password`

#### delete Menu
`curl -s -X DELETE http://localhost:8080/votingSystem//rest/restaurants/100003/menu/100008 --user admin@gmail.com:password`



