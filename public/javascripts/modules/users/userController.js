angular.module("users", [])
       .controller("userController", UserController);

function UserController($scope) {
    this.users = [];
    this.users = getAllUsers();

    console.debug(this.users);

    function getAllUsers() {
        console.info("getAllUsers()");
        var users = [];

        for(var i = 1; i <= 3; i++) {
            users.push({
                name: "Name" + i,
                age: 25 + i,
                email: "email" + i + "@mail.com"
            });
        }

        return users;
    }

}