angular.module("simpleCrudApp", ["ngRoute", "users"])
    .controller("mainController", MainController)
    .config(function($routeProvider) {
        $routeProvider.when("/users", {
            templateUrl: "/assets/html/users/mainUsers.html",
            controller: "userController"
        });

        $routeProvider.when("/products", {
            templateUrl: "/assets/html/products/mainProducts.html",
            controller: "mainController"
        });

        $routeProvider.otherwise({
            templateUrl: "/assets/html/general.html",
            controller: "mainController"
        });
    });

function MainController($scope) {

}    