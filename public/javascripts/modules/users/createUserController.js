angular.module("users")
       .controller("createUserController", CreateUserController);

function CreateUserController($scope) {
    this.user = {};

    function createUser() {
        console.info("createUser()");
        var i = new Date().getTime();
        this.user = {
            name: "Name" + i,
            age: 25 + i,
            email: "email" + i + "@mail.com"
        };
        // redirect to the correct location
    }

}
